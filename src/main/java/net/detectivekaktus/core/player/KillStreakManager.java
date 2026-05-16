package net.detectivekaktus.core.player;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.core.sound.ScheduledGlobalSound;
import net.detectivekaktus.core.sound.SoundScheduler;
import net.detectivekaktus.sound.announce.DotcAnnounceSounds;

public class KillStreakManager {
    private final ServerPlayer player;

    private final int TICKS_UNTIL_LOSING_SHORT_STREAK = 15 * 20;
    private long lastKillTimestamp = 0L;
    private int shortStreakKillCount = 0;

    public KillStreakManager(ServerPlayer player) {
        this.player = player;
    }

    public void onPlayerKilled() {
        var flags = updateFlags();
        var killCount = flags.getKillCount();

        var totalKills = player.getStats().getValue(Stats.CUSTOM, Stats.PLAYER_KILLS);
        if (totalKills == 1 && killCount == 1) {
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

        shortStreakKillCount++;
        var thisKillTimestamp = player.level().getGameTime();
        if (thisKillTimestamp - lastKillTimestamp > TICKS_UNTIL_LOSING_SHORT_STREAK) {
            shortStreakKillCount = 1;
            lastKillTimestamp = thisKillTimestamp;
            return;
        }
        lastKillTimestamp = thisKillTimestamp;

        switch (shortStreakKillCount) {
            case 2: {
                announceToAllPlayersWithDelayedSound(
                        "player.defense-of-the-craft.double_kill",
                        DotcAnnounceSounds.DEFAULT_DOUBLE_KILL
                );
                break;
            }
            case 3: {
                announceToAllPlayersWithDelayedSound(
                        "player.defense-of-the-craft.triple_kill",
                        DotcAnnounceSounds.DEFAULT_TRIPLE_KILL
                );
                break;
            }
            case 4: {
                announceToAllPlayersWithDelayedSound(
                        "player.defense-of-the-craft.ultra_kill",
                        DotcAnnounceSounds.DEFAULT_ULTRA_KILL
                );
                break;
            }
            default: {
                if (shortStreakKillCount >= 5)
                    announceToAllPlayersWithDelayedSound(
                            "player.defense-of-the-craft.rampage_kill",
                            0xFFFF0000,
                            DotcAnnounceSounds.DEFAULT_RAMPAGE
                    );
                break;
            }
        }
    }

    private PlayerFlags.FlagsData updateFlags() {
        var flags = PlayerFlags.get(player);
        var killCount = flags.getKillCount();
        flags.setKillCount(++killCount);
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

    private void announceToAllPlayersWithDelayedSound(String key, SoundEvent sound) {
        player.server.getPlayerList().broadcastSystemMessage(Component.translatable(key, player.getDisplayName()), false);
        SoundScheduler.INSTANCE.addGlobalSound(new ScheduledGlobalSound(
                player.server.getPlayerList(),
                sound,
                SoundSource.PLAYERS,
                2 * 20
        ));
    }

    private void announceToAllPlayersWithDelayedSound(String key, int color, SoundEvent sound) {
        player.server.getPlayerList().broadcastSystemMessage(Component.translatable(key, player.getDisplayName()).withColor(color), false);
        SoundScheduler.INSTANCE.addGlobalSound(new ScheduledGlobalSound(
                player.server.getPlayerList(),
                sound,
                SoundSource.PLAYERS,
                2 * 20
        ));
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
}
