package net.detectivekaktus.sound.announce;

import net.minecraft.sounds.SoundEvent;

import net.detectivekaktus.sound.DotcSounds;

public class DotcAnnounceSounds {
    public static final SoundEvent DEFAULT_FIRST_BLOOD = DotcSounds.register("announce_default_kill_first");
    public static final SoundEvent DEFAULT_DOUBLE_KILL = DotcSounds.register("announce_default_kill_double");
    public static final SoundEvent DEFAULT_TRIPLE_KILL = DotcSounds.register("announce_default_kill_triple");
    public static final SoundEvent DEFAULT_ULTRA_KILL = DotcSounds.register("announce_default_kill_ultra");
    public static final SoundEvent DEFAULT_RAMPAGE = DotcSounds.register("announce_default_kill_rampage");

    public static final SoundEvent DEFAULT_KILLING_SPREE = DotcSounds.register("announce_default_streak_spree");
    public static final SoundEvent DEFAULT_DOMINATING = DotcSounds.register("announce_default_streak_dominate");
    public static final SoundEvent DEFAULT_MEGA_KILL = DotcSounds.register("announce_default_streak_mega");
    public static final SoundEvent DEFAULT_UNSTOPPABLE = DotcSounds.register("announce_default_streak_unstop");
    public static final SoundEvent DEFAULT_WICKED_SICK = DotcSounds.register("announce_default_streak_wicked");
    public static final SoundEvent DEFAULT_MONSTER_KILL = DotcSounds.register("announce_default_streak_monster");
    public static final SoundEvent DEFAULT_GODLIKE = DotcSounds.register("announce_default_streak_godlike");
    public static final SoundEvent DEFAULT_HOLY_SHIT = DotcSounds.register("announce_default_streak_holy");

    public static void initialize() { }
}
