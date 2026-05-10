package net.detectivekaktus.client.mixin.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.detectivekaktus.DotcConfig;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(AbstractWidget.class)
public class DotcAbstractWidgetMixin {
    @ModifyArg(
            method = "playDownSound",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V"
            ),
            index = 0
    )
    public SoundInstance changeDownSound(SoundInstance original) {
        if (!DotcConfig.HANDLER.instance().useValveUiSounds)
            return original;
        return SimpleSoundInstance.forUI(DotcGuiSounds.UI_BUTTON_PRESS, 1.0f);
    }
}
