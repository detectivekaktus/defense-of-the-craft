package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class SpiritVessel extends UrnOfShadows {
    public SpiritVessel(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        if (target == null) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 8 * 20, 2));
            return;
        }

        target.addEffect(new MobEffectInstance(DotcEffects.SPIRIT_SOUL_RELEASE, 8 * 20));
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.SPIRIT_VESSEL;
    }

    @Override
    public int getCooldownInTicks() {
        return 25 * 20;
    }
}
