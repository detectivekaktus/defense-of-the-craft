package net.detectivekaktus.event.player;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.detectivekaktus.core.player.KillStreakManager;

public class DotcPlayerEvents {
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var players = server.getPlayerList().getPlayers();
            for (var player : players) {
                ManaRegenEvent.tick(player);
                HpRegenEvent.tick(player);

                // Util tick is a tick value in range [0; 100].
                // It's used in the subsequent events to determine per-player
                // tick events, such as radiance damage, to prevent them being
                // invoked for all players at the same time
                UtilTickEvent.tick(player);

                GemOfTrueSightEvent.tick(player);
                RadianceEvent.tick(player);

                KillStreakManager.tickKillStreak(player);
            }
        });
        ServerPlayerEvents.LEAVE.register(LeaveServerEvent::execute);
    }
}
