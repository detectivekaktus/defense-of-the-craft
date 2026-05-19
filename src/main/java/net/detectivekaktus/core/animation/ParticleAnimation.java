package net.detectivekaktus.core.animation;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

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

    protected void drawSemiCircle(double radius, double step, TrigConsumer consumer) {
        drawOnTrigCircumference(radius, 0, Math.PI, step, consumer);
    }

    protected void drawSemiCircle(double radius, double step, boolean firstHalf, TrigConsumer consumer) {
        if (firstHalf) {
            drawOnTrigCircumference(radius, 0, Math.PI, step, consumer);
            return;
        }

        drawOnTrigCircumference(radius, step, Math.PI, 2 * Math.PI, consumer);
    }

    protected void drawCircle(double radius, double step, TrigConsumer consumer) {
        drawOnTrigCircumference(radius, step, 0, 2 * Math.PI, consumer);
    }

    protected void drawOnTrigCircumference(double radius, double step, double angle, double limit, TrigConsumer consumer) {
        while (angle < limit) {
            var cos = radius * Math.cos(angle);
            var sin = radius * Math.sin(angle);
            consumer.accept(cos ,sin);
            angle += step;
        }
    }

    @FunctionalInterface
    protected interface TrigConsumer {
        void accept(double cos, double sin);
    }

    public boolean isFinished() {
        return finished;
    }

    protected int getRandomOrientation() {
        return level.getRandom().nextDouble() < 0.5 ? 1 : -1;
    }

    public abstract void playAnimation();
}
