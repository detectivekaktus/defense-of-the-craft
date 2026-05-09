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

        private boolean setOrFallback(AttachmentType<Boolean> key, boolean value, boolean fallback) {
            var res = target.setAttached(key, value);
            return res == null ? fallback : res;
        }
    }
}