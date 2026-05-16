package net.detectivekaktus.attach;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import net.minecraft.resources.ResourceLocation;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.core.player.ShadowWalkingSource;

@SuppressWarnings("UnstableApiUsage")
public class PlayerFlags {
    public static final AttachmentType<Boolean> SHADOW_WALKING = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "shadow_walking"),
            booleanBuilder -> booleanBuilder.initializer(() -> false).persistent(Codec.BOOL)
    );
    public static final AttachmentType<Integer> SHADOW_WALKING_SOURCE = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "shadow_walking_source"),
            integerBuilder -> integerBuilder.initializer(() -> ShadowWalkingSource.NONE.id).persistent(Codec.INT)
    );

    public static final AttachmentType<Integer> KILL_COUNT = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "kill_count"),
            integerBuilder -> integerBuilder.initializer(() -> 0).persistent(Codec.INT)
    );
    public static final AttachmentType<Integer> KILL_STREAK_TICK = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "kill_streak_tick"),
            integerBuilder -> integerBuilder.initializer(() -> 0).persistent(Codec.INT)
    );

    public static final AttachmentType<Integer> UTIL_TICK = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "util_tick"),
            integerBuilder -> integerBuilder.initializer(() -> 0).persistent(Codec.INT)
    );

    public static void initialize() { }

    public static PlayerFlags.FlagsData get(AttachmentTarget target) {
        return new FlagsData(target);
    }

    public record FlagsData(AttachmentTarget target) {
        public boolean isShadowWalking() {
            return target.getAttachedOrCreate(SHADOW_WALKING);
        }

        public boolean setShadowWalking(boolean val) {
            var current = isShadowWalking();
            return setOrFallback(SHADOW_WALKING, val, current);
        }

        public ShadowWalkingSource getShadowWalkingSource() {
            var id = target.getAttachedOrCreate(SHADOW_WALKING_SOURCE);
            return ShadowWalkingSource.fromId(id);
        }

        public ShadowWalkingSource setShadowWalkingSource(ShadowWalkingSource val) {
            var current = getShadowWalkingSource();
            var old = target.setAttached(SHADOW_WALKING_SOURCE, val.id);
            return old == null ? current : ShadowWalkingSource.fromId(old);
        }

        public int getKillCount() {
            return target.getAttachedOrCreate(KILL_COUNT);
        }

        public int setKillCount(int val) {
            var current = getKillCount();
            return setOrFallback(KILL_COUNT, Math.max(val, 0), current);
        }

        public int getKillStreakTick() {
            return target.getAttachedOrCreate(KILL_STREAK_TICK);
        }

        public int setKillStreakTick(int val) {
            var current = getKillStreakTick();
            return setOrFallback(KILL_STREAK_TICK, Math.max(val, 0), current);
        }

        public int getUtilTick() {
            return target.getAttachedOrCreate(UTIL_TICK);
        }

        public int setUtilTick(int val) {
            var current = getUtilTick();
            return setOrFallback(UTIL_TICK, Math.max(val, 0), current);
        }

        private boolean setOrFallback(AttachmentType<Boolean> key, boolean value, boolean fallback) {
            var res = target.setAttached(key, value);
            return res == null ? fallback : res;
        }

        private int setOrFallback(AttachmentType<Integer> key, int value, int fallback) {
            var res = target.setAttached(key, value);
            return res == null ? fallback : res;
        }
    }
}