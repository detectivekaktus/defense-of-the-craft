package net.detectivekaktus.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class BlinkDagger extends DotcAbilitySwordItem {
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
        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                getSoundBeforeBlink(),
                SoundSource.PLAYERS
        );
        var pos = getTeleportPosition(player);
        player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
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

        if (currentBlock.is(Blocks.AIR) && pos.getY() > level.getMinBuildHeight()) {
            while (currentBlock.is(Blocks.AIR)) {
                currentPos = currentPos.below(1);
                currentBlock = level.getBlockState(currentPos);
            }

            if (!currentBlock.is(Blocks.AIR))
                currentPos = currentPos.above(1);
        } else if (pos.getY() <= level.getMinBuildHeight()) {
            // By doing this you should hopefully skip all the void air blocks and
            // get to the bedrock level. Also, if someone actually breaks bedrock it
            // still goes up
            while (currentBlock.is(Blocks.AIR)) {
                currentPos = currentPos.above(1);
                currentBlock = level.getBlockState(currentPos);
            }

            // By doing this you skip all blocks above the void. I didn't want to
            // specify concrete blocks because players can place whatever they need
            // and break bedrock, so it just looks for non-air blocks.
            while (!currentBlock.is(Blocks.AIR)) {
                currentPos = currentPos.above(1);
                currentBlock = level.getBlockState(currentPos);
            }
        }

        var playerPos = player.blockPosition();
        if (
                Math.abs(playerPos.getX() - currentPos.getX()) > BLINK_RADIUS
                        || Math.abs(playerPos.getY() - currentPos.getY()) > BLINK_RADIUS
                        || Math.abs(playerPos.getZ() - currentPos.getZ()) > BLINK_RADIUS
        )
            return playerPos;

        return currentPos;
    }

    private BlockPos ensureNotStuck(Player player, BlockPos pos, Level level) {
        var block = level.getBlockState(pos);

        if (block.is(Blocks.AIR))
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
                if (block.is(Blocks.AIR) && level.isInWorldBounds(currentPos)) {
                    var distance = player.blockPosition().distSqr(new Vec3i(currentPos.getX(), currentPos.getY(), currentPos.getZ()));
                    if (distance > bestDistance) {
                        bestDistance = distance;
                        bestPos = currentPos;
                    }
                    break;
                }
            }
        }

        return bestPos == null ? pos : bestPos;
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
