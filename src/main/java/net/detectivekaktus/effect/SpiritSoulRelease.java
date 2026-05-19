package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.detectivekaktus.damage.DotcDamageTypes;

public class SpiritSoulRelease extends MobEffect {
    protected SpiritSoulRelease() {
        super(MobEffectCategory.HARMFUL, 0xFF658207);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        var maxHpAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHpAttribute == null)
            return false;

        var damage = (float) (0.05f * maxHpAttribute.getValue());
        livingEntity.hurt(livingEntity.damageSources().source(DotcDamageTypes.MAGICAL), damage);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCounter, int amplifier) {
        var interval = 25 >> amplifier;
        return interval == 0 || tickCounter % interval == 0;
    }
}
