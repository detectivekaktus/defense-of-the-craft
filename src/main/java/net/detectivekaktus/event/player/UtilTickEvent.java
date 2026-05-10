package net.detectivekaktus.event.player;

import net.minecraft.server.level.ServerPlayer;

import net.detectivekaktus.attach.PlayerFlags;

public class UtilTickEvent {
    public static void tick(ServerPlayer player) {
        var flags = PlayerFlags.get(player);
        var utilTick = flags.getUtilTick();

        if (utilTick >= 100) {
            flags.setUtilTick(0);
            return;
        }
        flags.setUtilTick(++utilTick);
    }
}
