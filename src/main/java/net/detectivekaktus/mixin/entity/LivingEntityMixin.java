package net.detectivekaktus.mixin.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.core.player.ShadowWalkingSource;
import net.detectivekaktus.core.util.CombatManagerHolder;
import net.detectivekaktus.effect.DotcEffects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private boolean isNotMixinTarget(LivingEntity entity) {
        return entity.level().isClientSide || !(entity instanceof ServerPlayer);
    }

    @Inject(
            method = "knockback",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cancelKnockbackIfEvaded(double d, double e, double f, CallbackInfo callbackInfo) {
        var entity = (LivingEntity) (Object) this;
        if (isNotMixinTarget(entity))
            return;

        var player = (CombatManagerHolder) entity;
        if (!player.getCombatManager().hasEvaded())
            return;

        player.getCombatManager().setEvaded(false);
        callbackInfo.cancel();
    }

    @Inject(
            method = "travel",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void blockMovement(Vec3 vector, CallbackInfo callbackInfo) {
        var entity = (LivingEntity) (Object) this;
        if (!entity.hasEffect(DotcEffects.STUN))
            return;

        if (entity.fallDistance != 0 || !entity.onGround())
            return;

        entity.setDeltaMovement(0, 0, 0);
        callbackInfo.cancel();
    }

    @ModifyExpressionValue(
            method = "tickEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;tick(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/Runnable;)Z"
            )
    )
    private boolean resetInvisibilityFlagsOnPlayers(boolean original, @Local MobEffectInstance instance) {
        var entity = (LivingEntity) (Object) this;
        if (entity.level().isClientSide)
            return original;

        if (original)
            return true;

        if (!instance.is(MobEffects.INVISIBILITY) || !(entity instanceof Player player))
            return false;

        var flags = PlayerFlags.get(player);
        flags.setShadowWalking(false);
        flags.setShadowWalkingSource(ShadowWalkingSource.NONE);
        return false;
    }
}
