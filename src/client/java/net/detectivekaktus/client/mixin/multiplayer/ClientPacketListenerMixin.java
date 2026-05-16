package net.detectivekaktus.client.mixin.multiplayer;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.DotcConfig;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(
            method = "handleSoundEvent",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void skipStreakAnnouncements(ClientboundSoundPacket clientboundSoundPacket, CallbackInfo callbackInfo) {
        var sound = clientboundSoundPacket.getSound();
        var optional = sound.unwrapKey();
        if (optional.isEmpty())
            return;

        var location = optional.get().location();
        if (!location.getNamespace().equals(DefenseOfTheCraft.MOD_ID))
            return;

        if (!location.getPath().startsWith("announce_"))
            return;

        if (!DotcConfig.HANDLER.instance().enableStreakAnnouncement)
            callbackInfo.cancel();
    }
}
