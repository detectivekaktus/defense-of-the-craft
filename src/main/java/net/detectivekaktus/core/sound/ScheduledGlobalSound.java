package net.detectivekaktus.core.sound;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class ScheduledGlobalSound implements ServerTickEvents.EndTick {
    private final SoundEvent sound;
    private final SoundSource source;
    private final PlayerList playerList;

    private long ticksUntilSound;
    private boolean finished = false;

    public ScheduledGlobalSound(PlayerList list, SoundEvent sound, SoundSource source, long ticksUntilSound) {
        this.playerList = list;
        this.sound = sound;
        this.source = source;
        this.ticksUntilSound = ticksUntilSound;
    }

    public void setTimer(long ticksUntilSound) {
        this.ticksUntilSound = ticksUntilSound;
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        if (--ticksUntilSound == 0L) {
            var players = playerList.getPlayers();
            for (var player : players)
                player.playNotifySound(sound, source, 1.0f, 1.0f);
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
