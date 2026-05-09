package net.detectivekaktus.core.animation;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

public class DustOfAppearanceAnimation extends ParticleAnimation {
    private static final double DUST_PARTICLE_MAX_HORIZONTAL_SPREAD = 5;
    private static final double DUST_PARTICLE_MAX_VERTICAL_SPREAD = 1.5;
    private static final int PARTICLES_PER_BLOCK = 50;
    private static final int PARTICLES_PER_INVOCATION = 5;

    private final int distanceFromPlayer;
    private final boolean xAxis;

    public DustOfAppearanceAnimation(ServerLevel level, double x, double y, double z, int distanceFromPlayer, boolean xAxis, long ticksUntilAnimation, ParticleOptions particle) {
        super(level, x, y, z, ticksUntilAnimation, particle);
        this.distanceFromPlayer = distanceFromPlayer;
        this.xAxis = xAxis;
    }

    @Override
    public void playAnimation() {
        var random = level.getRandom();
        var deltaY = getDeltaYBasedOnDistanceFromPlayer(distanceFromPlayer);
        for (var particleCount = 0; particleCount != PARTICLES_PER_BLOCK; particleCount++) {
            var orientation = getRandomOrientation(random);
            var particleX = orientation * random.nextDouble() * DUST_PARTICLE_MAX_HORIZONTAL_SPREAD;

            orientation = getRandomOrientation(random);
            var particleZ = orientation * random.nextDouble() * DUST_PARTICLE_MAX_HORIZONTAL_SPREAD;

            orientation = getRandomOrientation(random);
            var particleY = deltaY + orientation * random.nextDouble() * DUST_PARTICLE_MAX_VERTICAL_SPREAD;

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

    private int getRandomOrientation(RandomSource randomSource) {
        return randomSource.nextDouble() < 0.5 ? 1 : -1;
    }

    private double getDeltaYBasedOnDistanceFromPlayer(double distance) {
        distance = Math.abs(distance);
        return -0.005 * distance * distance + 0.3 * distance + 0.2;
    }
}
