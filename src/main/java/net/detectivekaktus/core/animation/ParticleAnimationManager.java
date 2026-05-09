package net.detectivekaktus.core.animation;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class ParticleAnimationManager implements ServerTickEvents.EndTick{
    public static final ParticleAnimationManager INSTANCE = new ParticleAnimationManager();
    private final List<ParticleAnimation> animations = new ArrayList<>();

    public void addAnimation(ParticleAnimation animation) {
        animations.add(animation);
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        var iterator = animations.iterator();
        while (iterator.hasNext()) {
            var animation = iterator.next();
            animation.onEndTick(server);
            if (animation.isFinished())
                iterator.remove();
        }
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
    }
}
