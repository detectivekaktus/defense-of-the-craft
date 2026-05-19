package net.detectivekaktus.core.animation;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;

public class DustOfAppearanceAnimation extends ParticleAnimation {
    private static final double MAX_HORIZONTAL_SPREAD = 5;
    private static final double MAX_VERTICAL_SPREAD = 1.5;
    private static final int PARTICLES_PER_BLOCK = 50;
    private static final int PARTICLES_PER_INVOCATION = 2;

    private final int distanceFromPlayer;
    private final boolean xAxis;

    public DustOfAppearanceAnimation(ServerLevel level, double x, double y, double z, long ticksUntilAnimation, ParticleOptions particle, int distanceFromPlayer, boolean xAxis) {
        super(level, x, y, z, ticksUntilAnimation, particle);
        this.distanceFromPlayer = distanceFromPlayer;
        this.xAxis = xAxis;
    }

    @Override
    public void playAnimation() {
        var random = level.getRandom();
        var deltaY = getDeltaYBasedOnDistanceFromPlayer(distanceFromPlayer);
        for (var particleCount = 0; particleCount != PARTICLES_PER_BLOCK; particleCount++) {
            var orientation = getRandomOrientation();
            var particleX = orientation * random.nextDouble() * MAX_HORIZONTAL_SPREAD;

            orientation = getRandomOrientation();
            var particleZ = orientation * random.nextDouble() * MAX_HORIZONTAL_SPREAD;

            orientation = getRandomOrientation();
            var particleY = deltaY + orientation * random.nextDouble() * MAX_VERTICAL_SPREAD;

            if (xAxis)
                level.sendParticles(
                        particle,
                        x + distanceFromPlayer + particleX, y + particleY, z + particleZ,
                        PARTICLES_PER_INVOCATION,
                        0, 0, 0,
                        0.1 // speed
                );
            else
                level.sendParticles(
                        particle,
                        x + particleX, y + particleY, z + distanceFromPlayer + particleZ,
                        PARTICLES_PER_INVOCATION,
                        0, 0, 0,
                        0.1
                );
        }
    }

    private double getDeltaYBasedOnDistanceFromPlayer(double distance) {
        distance = Math.abs(distance);
        return -0.005 * distance * distance + 0.3 * distance + 0.2;
    }
}
