package net.detectivekaktus.mixin.player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.detectivekaktus.core.player.KillStreakManager;
import net.detectivekaktus.core.player.StatManager;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Unique
    private final ContainerListener dotcInventoryListener = new ContainerListener() {
        @Override
        public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {
            statManager.updateStats();
        }

        @Override
        public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int j) { }
    };

    @Inject(at = @At("HEAD"), method = "initMenu")
    public void initMenu(AbstractContainerMenu abstractContainerMenu, CallbackInfo callbackInfo) {
        abstractContainerMenu.addSlotListener(dotcInventoryListener);
    }

    @Unique
    @Final
    private StatManager statManager;

    @Unique
    @Final
    private KillStreakManager killStreakManager;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void addStatManager(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile, ClientInformation clientInformation, CallbackInfo callbackInfo) {
        var player = (ServerPlayer) (Object) this;
        this.statManager = new StatManager(player);
        this.killStreakManager = new KillStreakManager(player);
    }

    @Inject(
            method = "awardKillScore",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void awardKillStreak(Entity entity, int i, DamageSource damageSource, CallbackInfo callbackInfo) {
        killStreakManager.onPlayerKilled();
    }
}
