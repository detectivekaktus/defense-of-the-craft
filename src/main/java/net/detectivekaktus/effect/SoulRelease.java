package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import net.detectivekaktus.damage.DotcDamageTypes;

public class SoulRelease extends MobEffect {
    protected SoulRelease() {
        super(MobEffectCategory.HARMFUL, 0xFF70AA10);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(livingEntity.damageSources().source(DotcDamageTypes.MAGICAL), 3.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCounter, int amplifier) {
        var interval = 25 >> amplifier;
        return interval == 0 || tickCounter % interval == 0;
    }
}
