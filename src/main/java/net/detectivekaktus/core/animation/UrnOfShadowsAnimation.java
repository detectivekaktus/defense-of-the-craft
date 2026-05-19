package net.detectivekaktus.core.animation;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;

public class UrnOfShadowsAnimation extends ParticleAnimation {
    protected final double radius;

    public UrnOfShadowsAnimation(ServerLevel level, double x, double y, double z, long ticksUntilAnimation, ParticleOptions particle, double radius) {
        super(level, x, y, z, ticksUntilAnimation, particle);
        this.radius = radius;
    }

    @Override
    public void playAnimation() {
        drawCircle(radius, Math.PI / 15, ((cos, sin) -> level.sendParticles(
                particle,
                x + cos, y, z + sin,
                1,
                0, 0, 0,
                1
        )));
    }
}
