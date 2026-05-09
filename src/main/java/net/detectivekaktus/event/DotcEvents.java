package net.detectivekaktus.event;

import net.detectivekaktus.core.animation.ParticleAnimationManager;
import net.detectivekaktus.event.player.DotcPlayerEvents;

public class DotcEvents {
    public static void initialize() {
        DotcPlayerEvents.initialize();
        ParticleAnimationManager.register();
    }
}
