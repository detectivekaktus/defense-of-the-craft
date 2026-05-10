package net.detectivekaktus.client.mixin;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(Screenshot.class)
public class DotcScreenshotMixin {
    @Inject(
            method = "takeScreenshot",
            at = @At("HEAD")
    )
    private static void takeScreenshot(CallbackInfoReturnable<NativeImage> callbackInfo) {
        if (!DotcConfig.HANDLER.instance().addScreenshotSound)
            return;

        var client = Minecraft.getInstance();
        client.getSoundManager().play(SimpleSoundInstance.forUI(DotcGuiSounds.UI_STEAM_CAMERA, 1.0f));
    }
}
