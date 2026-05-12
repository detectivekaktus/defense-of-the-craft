package net.detectivekaktus.core.animation;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;

public class BlinkDaggerAnimation extends ParticleAnimation {
    private static final double MAX_HORIZONTAL_SPREAD = 0.5;
    private static final double MAX_VERTICAL_SPREAD = 0.25;

    public BlinkDaggerAnimation(ServerLevel level, double x, double y, double z, long ticksUntilAnimation, ParticleOptions particle) {
        super(level, x, y, z, ticksUntilAnimation, particle);
    }

    @Override
    public void playAnimation() {
        var random = level.getRandom();
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        for (var direction : directions) {
            for (var i = 0; i < 3; i++) {
                var orientation = getRandomOrientation();
                var particleX = direction[0] + orientation * MAX_HORIZONTAL_SPREAD * random.nextDouble();
                var particleY = orientation * MAX_VERTICAL_SPREAD * random.nextDouble();
                var particleZ = direction[1] + orientation * MAX_HORIZONTAL_SPREAD * random.nextDouble();

                level.sendParticles(
                        particle,
                        x + particleX, y + particleY, z + particleZ,
                        5,
                        0, 0, 0,
                        0
                );
            }
        }
    }
}
