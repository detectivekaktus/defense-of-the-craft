package net.detectivekaktus.client.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Screenshot;grab(Ljava/io/File;Lcom/mojang/blaze3d/pipeline/RenderTarget;Ljava/util/function/Consumer;)V"
            )
    )
    private void playScreenshotSound(CallbackInfo callbackInfo) {
        if (!DotcConfig.HANDLER.instance().addScreenshotSound)
            return;

        var client = Minecraft.getInstance();
        client.getSoundManager().play(SimpleSoundInstance.forUI(DotcGuiSounds.UI_STEAM_CAMERA, 1.0f));
    }
}
