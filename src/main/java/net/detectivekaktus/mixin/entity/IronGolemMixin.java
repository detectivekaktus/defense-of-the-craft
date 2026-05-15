package net.detectivekaktus.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.detectivekaktus.effect.DotcEffects;

@Mixin(IronGolem.class)
public class IronGolemMixin {
    @Inject(
            method = "doHurtTarget",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cancelAttack(Entity entity, CallbackInfoReturnable<Boolean> callbackInfo) {
        var golem = (IronGolem) (Object) this;
        if (!golem.hasEffect(DotcEffects.STUN) && !golem.hasEffect(DotcEffects.DISARM))
            return;

        callbackInfo.setReturnValue(false);
    }
}
