package net.detectivekaktus.core.animation;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;

public class AeonDiskAnimation extends ParticleAnimation {
    public AeonDiskAnimation(ServerLevel level, double x, double y, double z, long ticksUntilAnimation, ParticleOptions particle) {
        super(level, x, y, z, ticksUntilAnimation, particle);
    }

    @Override
    public void playAnimation() {
        var radius = 1.5;
        var step = Math.PI / 15;

        drawCircle(radius, step, ((cos, sin) -> level.sendParticles(
                particle,
                x + cos, y, z + sin,
                3,
                0, 0, 0,
                0
        )));
        drawCircle(radius, step, ((cos, sin) -> level.sendParticles(
                particle,
                x + cos, y + sin, z,
                3,
                0, 0, 0,
                0
        )));
        drawCircle(radius, step, ((cos, sin) -> level.sendParticles(
                particle,
                x, y + cos, z + sin,
                3,
                0, 0, 0,
                0
        )));
    }
}
