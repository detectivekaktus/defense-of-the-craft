package net.detectivekaktus.effect;

import net.detectivekaktus.core.animation.ParticleAnimationManager;
import net.detectivekaktus.core.animation.UrnOfShadowsAnimation;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import org.joml.Vector3f;

import net.detectivekaktus.damage.DotcDamageTypes;

public class SpiritSoulRelease extends MobEffect {
    private static final ParticleOptions particle = new DustParticleOptions(
            new Vector3f(0.529f, 0.058f, 0.019f),
            1.0f
    );

    protected SpiritSoulRelease() {
        super(MobEffectCategory.HARMFUL, 0xFF870F05);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level().isClientSide)
            return true;

        var maxHpAttribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (maxHpAttribute == null)
            return false;

        var damage = (float) (0.075f * maxHpAttribute.getValue());
        livingEntity.hurt(livingEntity.damageSources().source(DotcDamageTypes.MAGICAL), damage);

        ParticleAnimationManager.INSTANCE.addAnimation(new UrnOfShadowsAnimation(
                (ServerLevel) livingEntity.level(),
                livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(),
                2,
                particle,
                1
        ));
        ParticleAnimationManager.INSTANCE.addAnimation(new UrnOfShadowsAnimation(
                (ServerLevel) livingEntity.level(),
                livingEntity.getX(), livingEntity.getY() + 1.1, livingEntity.getZ(),
                2,
                particle,
                1.5
        ));
        ParticleAnimationManager.INSTANCE.addAnimation(new UrnOfShadowsAnimation(
                (ServerLevel) livingEntity.level(),
                livingEntity.getX(), livingEntity.getY() + 2, livingEntity.getZ(),
                2,
                particle,
                1
        ));

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCounter, int amplifier) {
        var interval = 25 >> amplifier;
        return interval == 0 || tickCounter % interval == 0;
    }
}
