package net.detectivekaktus.core.item;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;

public interface ParticleAnimated {
    void playAnimation(ServerLevel level, double x, double y, double z);
    ParticleOptions getAnimationParticle();
}
