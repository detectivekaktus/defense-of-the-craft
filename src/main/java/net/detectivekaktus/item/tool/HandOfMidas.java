package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.attach.PlayerRandom;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.core.item.DotcItemCooldowns;
import net.detectivekaktus.item.DotcAbilityItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;
import net.detectivekaktus.tag.DotcEntityTypeTags;

import java.util.LinkedHashMap;

public class HandOfMidas extends DotcAbilityItem {
    private final LinkedHashMap<Item, Integer> WEIGHTS = new LinkedHashMap<>();
    private final int TOTAL_WEIGHT;

    private final int DIAMOND_PITY = -1;

    private final int PITY_COUNTER_CAP = 12;
    private final int PITY_INITIAL_BONUS = 4;

    private final float PITY_NO_INCREASE = 1.0f;
    private final float PITY_SOFT_INCREASE = 1.1f;
    private final float PITY_HARD_INCREASE = 1.2f;

    public HandOfMidas(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
        WEIGHTS.put(Items.COAL, 800);
        WEIGHTS.put(Items.IRON_INGOT, 600);
        WEIGHTS.put(Items.GOLD_INGOT, 600);
        WEIGHTS.put(Items.DIAMOND, 125);
        WEIGHTS.put(Items.NETHERITE_INGOT, 5);
        TOTAL_WEIGHT = WEIGHTS.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return interactWithItem(player, livingEntity, itemStack).getResult();
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        if (!stack.has(DotcComponents.USE_COUNTER_COMPONENT)) {
            DefenseOfTheCraft.LOGGER.error("Hand of Midas doesn't have use counter component. No drops will be given");
            return;
        }
        var useCounter = stack.get(DotcComponents.USE_COUNTER_COMPONENT);
        DefenseOfTheCraft.LOGGER.info("{}", stack.has(DotcComponents.USE_COUNTER_COMPONENT));

        var random = PlayerRandom.get(player);
        var pityCounter = random.getPityCounter();

        var randomWeight = getRandomWeight(player.getRandom(), useCounter, pityCounter);
        var droppedItem = getDroppedItem(randomWeight);

        if (droppedItem == Items.DIAMOND || droppedItem == Items.NETHERITE_INGOT)
            random.setPityCounter(0);
        else
            random.setPityCounter(++pityCounter);
        stack.set(DotcComponents.USE_COUNTER_COMPONENT, ++useCounter);

        var level = player.level();
        var itemEntity = new ItemEntity(
                level,
                target.getX(), target.getY(), target.getZ(),
                new ItemStack(droppedItem)
        );

        target.discard();
        level.addFreshEntity(itemEntity);

        if (droppedItem == Items.NETHERITE_INGOT)
            level.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                    SoundSource.PLAYERS
            );
    }

    private Item getDroppedItem(int randomWeight) {
        if (randomWeight == DIAMOND_PITY)
            return Items.DIAMOND;

        var cumulative = 0;
        for (var entry : WEIGHTS.entrySet()) {
            var item = entry.getKey();
            var weight = entry.getValue();

            cumulative += weight;
            if (randomWeight <= cumulative)
                return item;
        }

        return Items.NETHERITE_INGOT;
    }

    private int getRandomWeight(RandomSource randomSource, int useCounter, int pityCounter) {
        if (useCounter <= PITY_INITIAL_BONUS)
            return getInitialBonusWeight(randomSource, pityCounter);

        if (pityCounter >= PITY_COUNTER_CAP)
            return DIAMOND_PITY;

        var rand = randomSource.nextIntBetweenInclusive(1, TOTAL_WEIGHT);
        var increase = getPityIncrease(pityCounter);
        return (int) (rand * increase);
    }

    private int getInitialBonusWeight(RandomSource randomSource, int pityCounter) {
        var skipped = (int) (WEIGHTS.get(Items.COAL) * 0.875f);
        var rand = randomSource.nextIntBetweenInclusive(skipped, TOTAL_WEIGHT);
        var increase = getPityIncrease(pityCounter);
        return (int) (rand * increase);
    }

    private float getPityIncrease(int pityCounter) {
        if (pityCounter <= 4)
            return PITY_NO_INCREASE;
        else if (pityCounter <= 8)
            return PITY_SOFT_INCREASE;
        else
            return PITY_HARD_INCREASE;
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return DotcEntityTypeTags.HAND_OF_MIDAS_INVULNERABLE;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.HAND_OF_MIDAS;
    }

    @Override
    public float getManaCost() {
        return 25.0f;
    }

    @Override
    public int getCooldownInTicks() {
//        return DotcItemCooldowns.HAND_OF_MIDAS_COOLDOWN;
        return 0;
    }
}
