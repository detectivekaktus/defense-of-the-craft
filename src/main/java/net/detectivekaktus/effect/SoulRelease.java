package net.detectivekaktus.effect;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import org.joml.Vector3f;

import net.detectivekaktus.core.animation.ParticleAnimationManager;
import net.detectivekaktus.core.animation.UrnOfShadowsAnimation;
import net.detectivekaktus.damage.DotcDamageTypes;

public class SoulRelease extends MobEffect {
    private static final ParticleOptions particle = new DustParticleOptions(
            new Vector3f(0.768f, 0.152f, 0.105f),
            1.0f
    );

    protected SoulRelease() {
        super(MobEffectCategory.HARMFUL, 0xFFC4271B);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level().isClientSide)
            return true;

        livingEntity.hurt(livingEntity.damageSources().source(DotcDamageTypes.MAGICAL), 3.0f);

        ParticleAnimationManager.INSTANCE.addAnimation(new UrnOfShadowsAnimation(
                (ServerLevel) livingEntity.level(),
                livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(),
                2,
                particle,
                1
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
