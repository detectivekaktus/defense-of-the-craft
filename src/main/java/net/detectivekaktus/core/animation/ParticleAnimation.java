package net.detectivekaktus.core.animation;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

public abstract class ParticleAnimation implements ServerTickEvents.EndTick {
    private long ticksUntilAnimation;
    private boolean finished;

    protected final ServerLevel level;
    protected final double x;
    protected final double y;
    protected final double z;
    protected final ParticleOptions particle;

    public ParticleAnimation(ServerLevel level, double x, double y, double z, long ticksUntilAnimation, ParticleOptions particle) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ticksUntilAnimation = ticksUntilAnimation;
        this.particle = particle;
        this.finished = false;
    }

    public void setTimer(long ticksUntilAnimation) {
        this.ticksUntilAnimation = ticksUntilAnimation;
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        if (--ticksUntilAnimation == 0L) {
            playAnimation();
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    protected int getRandomOrientation() {
        return level.getRandom().nextDouble() < 0.5 ? 1 : -1;
    }

    public abstract void playAnimation();
}
