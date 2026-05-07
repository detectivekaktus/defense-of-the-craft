package net.detectivekaktus.client.mixin.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @WrapWithCondition(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Entity;FFFFFF)V"
            )
    )
    private boolean shouldRenderLayers(
            RenderLayer renderLayer,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            Entity entity,
            float f,
            float g,
            float h,
            float j,
            float k,
            float l
    ) {
        var target = (LivingEntity) entity;
        if (!(target instanceof Player))
            return true;
        return !target.hasEffect(MobEffects.INVISIBILITY);
    }
}
