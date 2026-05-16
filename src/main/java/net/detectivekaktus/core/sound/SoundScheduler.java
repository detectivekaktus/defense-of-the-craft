package net.detectivekaktus.core.sound;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class SoundScheduler implements ServerTickEvents.EndTick {
    public static final SoundScheduler INSTANCE = new SoundScheduler();
    private final List<ScheduledGlobalSound> sounds = new ArrayList<>();

    public void addGlobalSound(ScheduledGlobalSound sound) {
        sounds.add(sound);
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        var iterator = sounds.iterator();
        if (iterator.hasNext()) {
            var sound = iterator.next();
            sound.onEndTick(server);
            if (sound.isFinished())
                iterator.remove();
        }
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
    }
}
