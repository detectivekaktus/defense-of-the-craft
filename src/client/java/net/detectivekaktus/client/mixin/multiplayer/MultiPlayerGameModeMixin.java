package net.detectivekaktus.client.mixin.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import org.apache.commons.lang3.mutable.MutableObject;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.detectivekaktus.client.core.UiSoundManager;
import net.detectivekaktus.effect.DotcEffects;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            method = {"startDestroyBlock", "continueDestroyBlock"},
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cancelDestroyBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo) {
        var player = minecraft.player;
        if (player == null)
            return;

        if (!player.hasEffect(DotcEffects.STUN) && !player.hasEffect(DotcEffects.DISARM))
            return;

        callbackInfo.setReturnValue(false);
    }

    @ModifyExpressionValue(
            // After some WILD research the `MultiPlayerGameMode.useItem()` method
            // uses a lambda under the hood and its name in bytecode is the
            // one you see specified in the mixin.
            method = "method_41929",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;isOnCooldown(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean playCooldownSoundOnUseItem(
            boolean isOnCooldown,
            InteractionHand interactionHand,
            Player player,
            MutableObject object
    ) {
        return UiSoundManager.playCooldownSound(isOnCooldown, player);
    }

    @ModifyExpressionValue(
            method = "performUseItemOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;isOnCooldown(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean playCooldownSoundOnUseItemOn(boolean isOnCooldown, LocalPlayer player) {
        return UiSoundManager.playCooldownSound(isOnCooldown, player);
    }
}
