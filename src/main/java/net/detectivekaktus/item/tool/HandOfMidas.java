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
import net.detectivekaktus.item.DotcAbilityItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;
import net.detectivekaktus.tag.DotcEntityTypeTags;

import java.time.Instant;
import java.util.LinkedHashMap;

public class HandOfMidas extends DotcAbilityItem {
    private final LinkedHashMap<Item, Integer> WEIGHTS;
    private final int TOTAL_WEIGHT;

    private final int DIAMOND_PITY = -1;

    private final int PITY_COUNTER_CAP = 12;
    private final int PITY_TIMESTAMP_CAP = 60 * 60 * 4;
    private final int COMEBACK_BONUS_INTERVAL = 60 * 60 * 24 * 2;
    // maybe you'll hit the pity counter cap when your bonus expires
    private final int PITY_INITIAL_BONUS = 11;
    private final int PITY_COMEBACK_BONUS = 4;

    public HandOfMidas(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
        WEIGHTS = new LinkedHashMap<>();
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

        var now = Instant.now().getEpochSecond();
        var lastRollTimestamp = stack.set(DotcComponents.LAST_TIME_USED_COMPONENT, now);
        if (lastRollTimestamp == null)
            lastRollTimestamp = now;

        var random = PlayerRandom.get(player);
        var lastLogoutTimestamp = random.getLastLogoutTimestamp();
        if (now - lastLogoutTimestamp >= COMEBACK_BONUS_INTERVAL)
            random.setComebackBoosterCounter(PITY_COMEBACK_BONUS);
        var comebackBonus = random.getComebackBoosterCounter();
        var pityCounter = random.getPityCounter();

        var randomWeight = getRandomWeight(
                player.getRandom(),
                useCounter,
                pityCounter,
                lastRollTimestamp,
                comebackBonus
        );
        var droppedItem = getDroppedItem(randomWeight);

        random.setPityCounter(droppedItem == Items.DIAMOND || droppedItem == Items.NETHERITE_INGOT ? 0 : ++pityCounter);
        random.setComebackBoosterCounter(comebackBonus == 0 ? 0 : --comebackBonus);
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

    private int getRandomWeight(
            RandomSource randomSource,
            int useCounter,
            int pityCounter,
            long lastRollTimestamp,
            int comebackBonus
    ) {
        if (comebackBonus != 0)
            return getComebackBonusWeight(randomSource);

        if (useCounter <= PITY_INITIAL_BONUS)
            return getInitialBonusWeight(randomSource);

        if (pityCounter >= PITY_COUNTER_CAP)
            return DIAMOND_PITY;

        if (Instant.now().getEpochSecond() - lastRollTimestamp >= PITY_TIMESTAMP_CAP)
            return DIAMOND_PITY;

        return randomSource.nextIntBetweenInclusive(1, TOTAL_WEIGHT);
    }

    private int getComebackBonusWeight(RandomSource randomSource) {
        var skipped = WEIGHTS.get(Items.COAL) + WEIGHTS.get(Items.IRON_INGOT) + 1;
        return randomSource.nextIntBetweenInclusive(skipped, TOTAL_WEIGHT);
    }

    private int getInitialBonusWeight(RandomSource randomSource) {
        var skipped = (int) (WEIGHTS.get(Items.COAL) * 0.875f);
        return randomSource.nextIntBetweenInclusive(skipped, TOTAL_WEIGHT);
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
        return (60 * 5) * 20;
    }
}
