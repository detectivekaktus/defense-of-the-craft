package net.detectivekaktus.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;

import net.minecraft.network.chat.Component;

import net.detectivekaktus.DotcConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.defense-of-the-craft.title"))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Component.translatable("category.defense-of-the-craft.ui.title"))
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("group.defense-of-the-craft.sound.title"))
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("option.defense-of-the-craft.change_button_sounds"))
                                                                .description(OptionDescription.of(Component.translatable("option.defense-of-the-craft.change_button_sounds.description")))
                                                                .binding(
                                                                        DotcConfig.HANDLER.defaults().changeButtonSounds,
                                                                        () -> DotcConfig.HANDLER.instance().changeButtonSounds,
                                                                        val -> DotcConfig.HANDLER.instance().changeButtonSounds = val
                                                                )
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("option.defense-of-the-craft.add_screenshot_sound"))
                                                                .description(OptionDescription.of(Component.translatable("option.defense-of-the-craft.add_screenshot_sound.description")))
                                                                .binding(
                                                                        DotcConfig.HANDLER.defaults().addScreenshotSound,
                                                                        () -> DotcConfig.HANDLER.instance().addScreenshotSound,
                                                                        val -> DotcConfig.HANDLER.instance().addScreenshotSound = val
                                                                )
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("option.defense-of-the-craft.add_whispering_sound"))
                                                                .description(OptionDescription.of(Component.translatable("option.defense-of-the-craft.add_whispering_sound.description")))
                                                                .binding(
                                                                        DotcConfig.HANDLER.defaults().addWhisperingSound,
                                                                        () -> DotcConfig.HANDLER.instance().addWhisperingSound,
                                                                        val -> DotcConfig.HANDLER.instance().addWhisperingSound = val
                                                                )
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("option.defense-of-the-craft.enable_streak_announcement"))
                                                                .description(OptionDescription.of(Component.translatable("option.defense-of-the-craft.enable_streak_announcement.description")))
                                                                .binding(
                                                                        DotcConfig.HANDLER.defaults().enableStreakAnnouncement,
                                                                        () -> DotcConfig.HANDLER.instance().enableStreakAnnouncement,
                                                                        val -> DotcConfig.HANDLER.instance().enableStreakAnnouncement = val
                                                                )
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .build()
                                )
                                .group(
                                        OptionGroup.createBuilder()
                                                .name(Component.translatable("group.defense-of-the-craft.hud.title"))
                                                .option(
                                                        Option.<Boolean>createBuilder()
                                                                .name(Component.translatable("option.defense-of-the-craft.show_status_bar"))
                                                                .description(OptionDescription.of(Component.translatable("option.defense-of-the-craft.show_status_bar.description")))
                                                                .binding(
                                                                        DotcConfig.HANDLER.defaults().showStatusBar,
                                                                        () -> DotcConfig.HANDLER.instance().showStatusBar,
                                                                        val -> DotcConfig.HANDLER.instance().showStatusBar = val
                                                                )
                                                                .controller(TickBoxControllerBuilder::create)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .save(() -> DotcConfig.HANDLER.save())
                .build()
                .generateScreen(parent);
    }
}
