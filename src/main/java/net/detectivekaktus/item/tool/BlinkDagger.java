package net.detectivekaktus.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import org.joml.Vector3f;

import net.detectivekaktus.core.animation.BlinkDaggerAnimation;
import net.detectivekaktus.core.animation.ParticleAnimationManager;
import net.detectivekaktus.core.item.ParticleAnimated;
import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class BlinkDagger extends DotcAbilitySwordItem implements ParticleAnimated {
    private final int BLINK_RADIUS = 12;

    public BlinkDagger(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        return interactWithItem(player, null, stack);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        var level = player.level();
        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                getSoundBeforeBlink(),
                SoundSource.PLAYERS
        );

        playAnimation((ServerLevel) level, player.getX(), player.getY(), player.getZ());

        var pos = getTeleportPosition(player);
        player.teleportTo(pos.getX(), pos.getY(), pos.getZ());

        playAnimation((ServerLevel) level, player.getX(), player.getY(), player.getZ());
    }

    private BlockPos getTeleportPosition(Player player) {
        var level = player.level();
        var vector = player.getViewVector(1.0f).scale(BLINK_RADIUS);
        var pos = new BlockPos((int) (player.getX() + vector.x), (int) (player.getY() + vector.y), (int) (player.getZ() + vector.z));
        pos = ensureNotStuck(player, pos, level);
        pos = ensureOnGround(player, pos, level);
        return pos;
    }

    private BlockPos ensureOnGround(Player player, BlockPos pos, Level level) {
        var currentBlock = level.getBlockState(pos);
        var currentPos = pos;

        if (currentBlock.is(BlockTags.AIR) && currentPos.getY() > level.getMinBuildHeight()) {
            var prevPos = currentPos;
            for (int i = 0; i < BLINK_RADIUS; i++) {
                currentPos = currentPos.below(1);
                currentBlock = level.getBlockState(currentPos);

                if (!currentBlock.is(BlockTags.AIR))
                    return prevPos;

                prevPos = currentPos;
            }
        }
        else if (currentPos.getY() <= level.getMinBuildHeight()) {
            var hasEncounteredSolidBlock = false;
            for (var i = 0; i < BLINK_RADIUS; i++) {
                currentPos = currentPos.above(1);
                currentBlock = level.getBlockState(currentPos);
                var blockAbove = level.getBlockState(currentPos.above(1));

                if (hasEncounteredSolidBlock
                        // This would mean that there's sufficient space for player to stay
                        && (currentBlock.is(BlockTags.AIR) && blockAbove.is(BlockTags.AIR)))
                    return currentPos;

                if (!currentBlock.is(BlockTags.AIR))
                    hasEncounteredSolidBlock = true;
            }
        }

        return player.blockPosition();
    }

    private BlockPos ensureNotStuck(Player player, BlockPos pos, Level level) {
        var block = level.getBlockState(pos);

        if (block.is(BlockTags.AIR))
            return pos;

        BlockPos bestPos = null;
        var bestDistance = Double.MIN_VALUE;
        int[][] directions = {
                { 1, 0 },
                { -1, 0 },
                { 0, 1 },
                { 0, -1 }
        };

        for (var direction : directions) {
            for (var i = 1; i < BLINK_RADIUS + 1; i++) {
                var currentPos = pos.offset(direction[0] * i, 0, direction[1] * i);
                block = level.getBlockState(currentPos);
                if (block.is(BlockTags.AIR) && level.isInWorldBounds(currentPos)) {
                    var distance = player.blockPosition().distSqr(new Vec3i(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
                    if ((distance <= BLINK_RADIUS * BLINK_RADIUS) && distance > bestDistance) {
                        bestDistance = distance;
                        bestPos = currentPos;
                    }
                    break;
                }
            }
        }

        return bestPos == null ? player.blockPosition() : bestPos;
    }

    @Override
    public void playAnimation(ServerLevel level, double x, double y, double z) {
        y += 1.25;

        var particle = getAnimationParticle();
        ParticleAnimationManager.INSTANCE.addAnimation(new BlinkDaggerAnimation(
                level,
                x, y, z,
                5,
                particle
        ));
    }

    @Override
    public ParticleOptions getAnimationParticle() {
        return new DustParticleOptions(new Vector3f(0.043f, 0.282f, 0.909f), 0.521f);
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.BLINK_DAGGER_TARGET;
    }

    protected SoundEvent getSoundBeforeBlink() {
        return DotcItemSounds.BLINK_DAGGER_SOURCE;
    }

    @Override
    public float getManaCost() {
        return 30.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 15 * 20;
    }
}
