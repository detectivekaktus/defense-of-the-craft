package net.detectivekaktus.attach;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import net.minecraft.resources.ResourceLocation;

import net.detectivekaktus.DefenseOfTheCraft;

import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public class PlayerRandom {
    public static final AttachmentType<Integer> PITY_COUNTER = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "pity_counter"),
            integerBuilder -> integerBuilder.initializer(() -> 0).persistent(Codec.INT)
    );
    public static final AttachmentType<Integer> COMEBACK_BOOST_COUNTER = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "comeback_boost_counter"),
            integerBuilder -> integerBuilder.initializer(() -> 0).persistent(Codec.INT)
    );
    // meant to store last player's logout timestamp in seconds since UNIX epoch in UTC
    public static final AttachmentType<Long> LOGOUT_TIMESTAMP = AttachmentRegistry.create(
            ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "logout_timestamp"),
            longBuilder -> longBuilder.persistent(Codec.LONG)
    );

    public static void initialize() { }

    public record RandomData(AttachmentTarget target) {
        public int getPityCounter() {
            return target.getAttachedOrCreate(PITY_COUNTER);
        }

        public int setPityCounter(int val) {
            var current = getPityCounter();
            return setOrFallback(PITY_COUNTER, Math.max(val, 0), current);
        }

        public int getComebackBoosterCounter() {
            return target.getAttachedOrCreate(COMEBACK_BOOST_COUNTER);
        }

        public int setComebackBoosterCounter(int val) {
            var current = getComebackBoosterCounter();
            return setOrFallback(COMEBACK_BOOST_COUNTER, Math.max(val, 0), current);
        }

        public long getLastLogoutTimestamp() {
            return target.getAttachedOrCreate(LOGOUT_TIMESTAMP);
        }

        public long setLastLogoutTimestamp(long val) {
            var current = getComebackBoosterCounter();
            return setOrFallback(LOGOUT_TIMESTAMP, val, current);
        }

        private int modifyOrFallback(AttachmentType<Integer> key, UnaryOperator<Integer> f, int fallback) {
            var res = target.modifyAttached(key, f);
            return res == null ? fallback : res;
        }

        private int setOrFallback(AttachmentType<Integer> key, int value, int fallback) {
            var res = target.setAttached(key, value);
            return res == null ? fallback : res;
        }

        private long modifyOrFallback(AttachmentType<Long> key, UnaryOperator<Long> f, long fallback) {
            var res = target.modifyAttached(key, f);
            return res == null ? fallback : res;
        }

        private long setOrFallback(AttachmentType<Long> key, long value, long fallback) {
            var res = target.setAttached(key, value);
            return res == null ? fallback : res;
        }
    }
}
