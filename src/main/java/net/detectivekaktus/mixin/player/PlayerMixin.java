package net.detectivekaktus.mixin.player;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.detectivekaktus.core.player.CombatManager;
import net.detectivekaktus.core.util.CombatManagerHolder;
import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

@Mixin(Player.class)
public class PlayerMixin implements CombatManagerHolder {
    @Unique
    @Final
    public CombatManager combatManager;

    @Override
    public CombatManager getCombatManager() {
        return combatManager;
    }

    @Unique
    private boolean isNotMixinTarget(Player player) {
        return player.level().isClientSide || !(player instanceof ServerPlayer);
    }

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void addCombatManager(Level level, BlockPos blockPos, float f, GameProfile gameProfile, CallbackInfo callbackInfo) {
        var player = (Player) (Object) this;
        if (isNotMixinTarget(player))
            return;

        this.combatManager = new CombatManager(player);
    }

    @ModifyVariable(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getWeaponItem()Lnet/minecraft/world/item/ItemStack;"
            ),
            ordinal = 0
    )
    private float preDamageCalculatedHook(float original, Entity entity) {
        var player = (Player) (Object) this;
        if (isNotMixinTarget(player))
            return original;

        var damage = combatManager.crit(original);
        damage += combatManager.addShadowWalkingDamage();
        return damage;
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private void preTargetHurtHook(Entity entity, CallbackInfo callbackInfo) {
        var player = (Player) (Object) this;
        if (isNotMixinTarget(player))
            return;

        combatManager.calculateProcs(entity);
    }

    @ModifyExpressionValue(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean postTargetHurtHook(boolean hurt, Entity entity, @Local(ordinal = 3) float damage) {
        if (!hurt)
            return false;

        var player = (Player) (Object) this;
        if (isNotMixinTarget(player))
            return hurt;

        combatManager.proc(entity);
        combatManager.lifesteal(damage);
        return true;
    }

    @ModifyVariable(
            method = "actuallyHurt",
            at = @At(value = "HEAD"),
            ordinal = 0
    )
    private float applyDamageModifiers(float original, DamageSource damageSource) {
        var player = (Player) (Object) this;
        var entity = damageSource.getEntity();
        if (isNotMixinTarget(player))
            return original;

        var damage = original;
        if (entity instanceof Player attacker)
            damage += combatManager.manaBurn(attacker, player);
        damage = combatManager.reduceDamage(damage, damageSource);

        return damage;
    }

    @Inject(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"
            ),
            cancellable = true
    )
    private void preDamageAbsorbHook(DamageSource damageSource, float damage, CallbackInfo callbackInfo) {
        var player = (Player) (Object) this;
        if (isNotMixinTarget(player))
            return;

        if (player.hasEffect(DotcEffects.COMBO_BREAKER))
            callbackInfo.cancel();

        if (combatManager.evade(damageSource))
            callbackInfo.cancel();

        if (combatManager.popAeonDisk(damage, damageSource))
            callbackInfo.cancel();

        combatManager.addCooldownOnBlinks(damage);
    }

    @Inject(
            method = "interactOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;interactLivingEntity(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"
            ),
            cancellable = true
    )
    private void preInteractLivingEntityHook(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> callbackInfo) {
        var player = (Player) (Object) this;

        var stack = player.getItemInHand(interactionHand);
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.level().playLocalSound(
                    player,
                    DotcGuiSounds.UI_COOLDOWN,
                    SoundSource.PLAYERS,
                    1.0f, 1.0f
            );
            callbackInfo.setReturnValue(InteractionResult.FAIL);
        }
    }

    @Inject(
            method = "attack",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cancelAttack(Entity entity, CallbackInfo callbackInfo) {
        var player = (Player) (Object) (this);
        var shouldSkip = !player.hasEffect(DotcEffects.STUN)
                && !player.hasEffect(DotcEffects.DISARM)
                && !player.hasEffect(DotcEffects.COMBO_BREAKER);
        if (shouldSkip)
            return;

        if (player.level().isClientSide)
            player.playSound(DotcGuiSounds.UI_DEBUFF);

        callbackInfo.cancel();
    }
}
