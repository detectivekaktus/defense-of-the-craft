package net.detectivekaktus.client.mixin.multiplayer.chat;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
    @Inject(
            method = "handlePlayerChatMessage",
            at = @At(
                    value = "HEAD"
            )
    )
    private void playReceiveMessageSound(PlayerChatMessage playerChatMessage, GameProfile gameProfile, ChatType.Bound bound, CallbackInfo callbackInfo) {
        if (!DotcConfig.HANDLER.instance().addWhisperingSound)
            return;

        if (!bound.chatType().is(ChatType.MSG_COMMAND_INCOMING))
            return;

        var client = Minecraft.getInstance();
        client.getSoundManager().play(SimpleSoundInstance.forUI(DotcGuiSounds.UI_RECEIVED_MESSAGE, 1.0f));
    }
}
