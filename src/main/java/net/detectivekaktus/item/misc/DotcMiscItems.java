package net.detectivekaktus.item.misc;

import net.minecraft.world.item.Item;

import net.detectivekaktus.item.DotcItems;
import net.detectivekaktus.sound.music.DotcMusicSounds;

public class DotcMiscItems {
    public static final Item RADIANT_THEME_MUSIC_DISC = DotcItems.register(
            new Item(
                    new Item.Properties()
                            .jukeboxPlayable(DotcMusicSounds.RADIANT_THEME_KEY)
                            .stacksTo(1)
            ),
            "radiant_theme_music_disc"
    );

    public static void initialize() { }
}
