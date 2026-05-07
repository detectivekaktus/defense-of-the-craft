package net.detectivekaktus.event.player;

import net.minecraft.server.level.ServerPlayer;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.attach.PlayerRandom;

import java.time.Instant;

public class LeaveServerEvent {
    public static void execute(ServerPlayer player) {
        DefenseOfTheCraft.LOGGER.info("Saving {} logout time", player.getDisplayName().getString());
        var random = PlayerRandom.get(player);
        random.setLastLogoutTimestamp(Instant.now().getEpochSecond());
    }
}
