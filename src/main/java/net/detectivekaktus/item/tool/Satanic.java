package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import net.detectivekaktus.core.player.CombatManager;
import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class Satanic extends DotcAbilitySwordItem {
    public Satanic(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        return interactWithItem(player, null, stack);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        CombatManager.dispelNonBeneficialEffects(player);
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.SATANIC;
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 30 * 20;
    }
}
