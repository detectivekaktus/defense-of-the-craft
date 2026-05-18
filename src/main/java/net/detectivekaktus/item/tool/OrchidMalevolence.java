package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class OrchidMalevolence extends DotcAbilitySwordItem {
    public OrchidMalevolence(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return interactWithItem(player, livingEntity, itemStack).getResult();
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        target.addEffect(new MobEffectInstance(DotcEffects.SILENCE, 5 * 20));
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.ORCHID_MALEVOLENCE;
    }

    @Override
    public float getManaCost() {
        return 40.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 20 * 20;
    }
}
