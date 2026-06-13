package net.detectivekaktus.sound.music;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.sound.DotcSounds;

public class DotcMusicSounds {
    public static final SoundEvent RADIANT_THEME = DotcSounds.register("music_radiant_theme");
    public static final ResourceKey<JukeboxSong> RADIANT_THEME_KEY = ResourceKey.create(
            Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "music_radiant_theme")
    );

    public static final SoundEvent DIRE_THEME = DotcSounds.register("music_dire_theme");
    public static final ResourceKey<JukeboxSong> DIRE_THEME_KEY = ResourceKey.create(
            Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "music_dire_theme")
    );

    public static void initialize() { }
}
