package net.detectivekaktus.item.consumable;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.joml.Vector3f;

import net.detectivekaktus.core.item.ParticleAnimated;
import net.detectivekaktus.item.DotcItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class DustOfAppearance extends DotcItem implements ParticleAnimated {
    private final int DUST_RANGE = 12;
    private final int DUST_PARTICLE_MAX_SPREAD = 6;
    private final int PARTICLES_PER_BLOCK = 15;

    public DustOfAppearance(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        if (level.isClientSide)
            return InteractionResultHolder.pass(stack);

        playAnimation((ServerLevel) level, player.getX(), player.getY(), player.getZ());
        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                DotcItemSounds.DUST_OF_APPEARANCE,
                SoundSource.PLAYERS
        );

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void playAnimation(ServerLevel level, double x, double y, double z) {
        var random = level.getRandom();
        y += 0.5;

        var particle = new DustParticleOptions(
                new Vector3f(0.705f, 0.733f, 0.909f),
                1.0f
        );

        renderStraightLineWithParticles(
                level,
                StraightLineDrawAxis.X_AXIS,
                x, y, z,
                1, 1,
                random,
                particle
        );
        renderStraightLineWithParticles(
                level,
                StraightLineDrawAxis.X_AXIS,
                x, y, z,
                -1, -1,
                random,
                particle
        );
        renderStraightLineWithParticles(
                level,
                StraightLineDrawAxis.Z_AXIS,
                x, y, z,
                1, 1,
                random,
                particle
        );
        renderStraightLineWithParticles(
                level,
                StraightLineDrawAxis.Z_AXIS,
                x, y, z,
                -1, -1,
                random,
                particle
        );
    }

    private void renderStraightLineWithParticles(
            ServerLevel level,
            StraightLineDrawAxis axis,
            double x, double y, double z,
            int start, int delta,
            RandomSource random,
            DustParticleOptions particle
    ) {
        for (var distance = start; Math.abs(distance) != DUST_RANGE - 1; distance += delta) {
            var particleY = getYDeltaBasedOnDistanceFromPlayer(distance);
            for (var particleCount = 0; particleCount != PARTICLES_PER_BLOCK; particleCount++) {
                var orientation = getRandomOrientation(random);
                var particleX = orientation * random.nextDouble() * DUST_PARTICLE_MAX_SPREAD;

                orientation = getRandomOrientation(random);
                var particleZ = orientation * random.nextDouble() * DUST_PARTICLE_MAX_SPREAD;

                if (axis == StraightLineDrawAxis.X_AXIS)
                    level.sendParticles(
                            particle,
                            x + distance + particleX, y + particleY, z + particleZ,
                            3, // number of particles
                            0, 0, 0,
                            0 // speed
                    );
                else
                    level.sendParticles(
                            particle,
                            x + particleX, y + particleY, z + distance + particleZ,
                            3,
                            0, 0, 0,
                            0
                    );
            }
        }
    }

    private int getRandomOrientation(RandomSource randomSource) {
        return randomSource.nextDouble() < 0.5 ? 1 : -1;
    }

    private double getYDeltaBasedOnDistanceFromPlayer(double distance) {
        distance = Math.abs(distance);
        var sinArg = -0.005 * distance * distance + 0.3 * distance + 0.2;
        return 2 * Math.sin(0.5 * sinArg);
    }

    private enum StraightLineDrawAxis {
        X_AXIS,
        Z_AXIS
    }
}
