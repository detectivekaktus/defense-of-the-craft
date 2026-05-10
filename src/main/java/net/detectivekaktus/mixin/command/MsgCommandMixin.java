package net.detectivekaktus.mixin.command;

import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(MsgCommand.class)
public class MsgCommandMixin {
    @Inject(
            method = "sendMessage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/commands/CommandSourceStack;sendChatMessage(Lnet/minecraft/network/chat/OutgoingChatMessage;ZLnet/minecraft/network/chat/ChatType$Bound;)V"
            )
    )
    private static void playReceiveMessageSound(CallbackInfo callbackInfo, @Local ServerPlayer player) {
        if (!DotcConfig.HANDLER.instance().addWhisperingSound)
            return;

        player.playNotifySound(
                DotcGuiSounds.UI_RECEIVED_MESSAGE,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
    }
}
