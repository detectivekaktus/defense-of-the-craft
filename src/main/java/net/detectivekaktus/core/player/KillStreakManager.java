package net.detectivekaktus.core.player;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.sound.announce.DotcAnnounceSounds;

public class KillStreakManager {
    private final ServerPlayer player;

    private final int TICKS_UNTIL_LOSING_STREAK = 10 * 20;

    public KillStreakManager(ServerPlayer player) {
        this.player = player;
    }

    public void onPlayerKilled() {
        var flags = updateFlags();
        var killCount = flags.getKillCount();
        var killTick = flags.getKillStreakTick();

        var totalKills = player.getStats().getValue(Stats.CUSTOM, Stats.PLAYER_KILLS);
        if (totalKills == 1 && killTick == 1) {
            announceToAllPlayers(
                    "player.defense-of-the-craft.first_blood",
                    DotcAnnounceSounds.DEFAULT_FIRST_BLOOD
            );
            return;
        }

        switch (killCount) {
            case 3: {
                announceToAllPlayers(
                        "player.defense-of-the-craft.spree",
                        0xFF00FF41,
                        DotcAnnounceSounds.DEFAULT_KILLING_SPREE
                );
                break;
            }
            case 4: {
                announceToAllPlayers("player.defense-of-the-craft.dominating",
                        0xFF5F00BD,
                        DotcAnnounceSounds.DEFAULT_DOMINATING
                );
                break;
            }
            case 5: {
                announceToAllPlayers("player.defense-of-the-craft.mega_kill",
                        0xFFFF0081,
                        DotcAnnounceSounds.DEFAULT_MEGA_KILL
                );
                break;
            }
            case 6: {
                announceToAllPlayers(
                        "player.defense-of-the-craft.unstoppable",
                        0xFFFF8100,
                        DotcAnnounceSounds.DEFAULT_UNSTOPPABLE
                );
                break;
            }
            case 7: {
                announceToAllPlayers(
                        "player.defense-of-the-craft.wicked_sick",
                        0xFF818100,
                        DotcAnnounceSounds.DEFAULT_WICKED_SICK
                );
                break;
            }
            case 8: {
                announceToAllPlayers(
                        "player.defense-of-the-craft.monster_kill",
                        0xFFFF81FF,
                        DotcAnnounceSounds.DEFAULT_MONSTER_KILL
                );
                break;
            }
            case 9: {
                announceToAllPlayers(
                        "player.defense-of-the-craft.godlike",
                        0xFF0000,
                        DotcAnnounceSounds.DEFAULT_GODLIKE
                );
                break;
            }
            default: {
                if (killCount >= 10)
                    announceToAllPlayers(
                            "player.defense-of-the-craft.holy_shit",
                            0xFFFF8100,
                            DotcAnnounceSounds.DEFAULT_HOLY_SHIT
                    );
                break;
            }
        }
    }

    private PlayerFlags.FlagsData updateFlags() {
        var flags = PlayerFlags.get(player);
        var killCount = flags.getKillCount();
        flags.setKillCount(++killCount);
        flags.setKillStreakTick(TICKS_UNTIL_LOSING_STREAK);
        return flags;
    }

    private void announceToAllPlayers(String key, SoundEvent sound) {
        player.server.getPlayerList().broadcastSystemMessage(Component.translatable(key, player.getDisplayName()), false);
        playSoundToAllPlayers(sound);
    }

    private void announceToAllPlayers(String key, int color, SoundEvent sound) {
        player.server.getPlayerList().broadcastSystemMessage(Component.translatable(key, player.getDisplayName()).withColor(color), false);
        playSoundToAllPlayers(sound);
    }

    private void playSoundToAllPlayers(SoundEvent sound) {
        var players = player.server.getPlayerList().getPlayers();
        for (var serverPlayer : players)
            serverPlayer.playNotifySound(
                    sound,
                    SoundSource.PLAYERS,
                    1.0f, 1.0f
            );
    }

    public static void tickKillStreak(ServerPlayer player) {
        var flags = PlayerFlags.get(player);
        var killTick = flags.getKillStreakTick();

        if (killTick == 0)
            return;

        flags.setKillStreakTick(--killTick);
    }
}
