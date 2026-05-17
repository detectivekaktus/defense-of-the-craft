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

        drawCircle(radius, ((cos, sin) -> {
            level.sendParticles(
                    particle,
                    x + cos, y, z + sin,
                    3,
                    0, 0, 0,
                    0
            );
        }));
        drawCircle(radius, ((cos, sin) -> {
            level.sendParticles(
                    particle,
                    x + cos, y + sin, z,
                    3,
                    0, 0, 0,
                    0
            );
        }));
        drawCircle(radius, ((cos, sin) -> {
            level.sendParticles(
                    particle,
                    x, y + cos, z + sin,
                    3,
                    0, 0, 0,
                    0
            );
        }));
    }

    private void drawCircle(double radius, TrigConsumer consumer) {
        var step = Math.PI / 15;
        for (double angle = 0; angle < Math.PI * 2; angle += step) {
            var cos = radius * Math.cos(angle);
            var sin = radius * Math.sin(angle);
            consumer.accept(cos ,sin);
        }
    }

    @FunctionalInterface
    interface TrigConsumer {
        void accept(double cos, double sin);
    }
}
