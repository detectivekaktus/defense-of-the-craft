package net.detectivekaktus.item.tool;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;

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
                DotcItemSounds.BLINK_DAGGER_SOURCE,
                SoundSource.PLAYERS
        );
        var pos = getTeleportPosition(player);
        player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
    }

    private BlockPos getTeleportPosition(Player player) {
        var vector = player.getViewVector(1.0f);
        return new BlockPos((int) (player.getX() + vector.x), (int) (player.getY() + vector.y), (int) (player.getZ() + vector.z));
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.BLINK_DAGGER_TARGET;
    }

    @Override
    public float getManaCost() {
        return 30.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 0;
//        return 15 * 20;
    }
}
