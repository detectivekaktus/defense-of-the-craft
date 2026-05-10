package net.detectivekaktus.client.mixin.gui;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @ModifyVariable(
            method = "play",
            at = @At(value = "HEAD")
    )
    private SoundInstance changeUiButtonClick(SoundInstance original) {
        if (!DotcConfig.HANDLER.instance().changeButtonSounds)
            return original;

        var originalLocation = original.getLocation();
        if (originalLocation.equals(ResourceLocation.withDefaultNamespace("ui.button.click")))
            return SimpleSoundInstance.forUI(DotcGuiSounds.UI_BUTTON_PRESS, 1.0f);
        return original;
    }
}
