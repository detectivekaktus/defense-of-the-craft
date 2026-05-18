package net.detectivekaktus.mixin.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.detectivekaktus.core.item.ItemStackHelper;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    private boolean isNotMixinTarget(Player player) {
        return player.level().isClientSide || !(player instanceof ServerPlayer);
    }

    @Inject(
            method = "use",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void useHead(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callbackInfo) {
        var stack = player.getItemInHand(hand);
        if (ItemStackHelper.cancelInteractionIfDisabled(player))
            callbackInfo.setReturnValue(InteractionResultHolder.fail(stack));

        if (isNotMixinTarget(player))
            return;

        ItemStackHelper.revealInvisibility(player);
    }

    @Inject(
            method = "useOn",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void useOnHead(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> callbackInfo) {
        var player = useOnContext.getPlayer();
        if (player == null)
            return;

        if (ItemStackHelper.cancelInteractionIfDisabled(player))
            callbackInfo.setReturnValue(InteractionResult.FAIL);

        if (isNotMixinTarget(player))
            return;

        ItemStackHelper.revealInvisibility(player);
    }

    @Inject(
            method = "interactLivingEntity",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void interactLivingEntityHead(Player player, LivingEntity livingEntity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callbackInfo) {
        if (ItemStackHelper.cancelInteractionIfDisabled(player))
            callbackInfo.setReturnValue(InteractionResult.FAIL);

        if (isNotMixinTarget(player))
            return;

        ItemStackHelper.revealInvisibility(player);
    }
}
