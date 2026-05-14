package net.detectivekaktus.client.mixin.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.detectivekaktus.client.core.DotcItemModels;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.item.primitive.DotcPrimitives;
import net.detectivekaktus.item.tool.DotcTools;
import net.detectivekaktus.item.tool.Radiance;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private BakedModel addDotcItemModelsInGetModel(
            BakedModel original,
            ItemStack stack,
            Level level,
            LivingEntity livingEntity,
            int i
    ) {
        if (stack.is(DotcTools.MONKEY_KING_BAR))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.MONKEY_KING_BAR_IN_HAND);
        else if (stack.is(DotcTools.CRYSTALYS))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.CRYSTALYS_IN_HAND);
        else if (stack.is(DotcTools.DAEDALUS))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.DAEDALUS_IN_HAND);
        else if (stack.is(DotcTools.BUTTERFLY))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.BUTTERFLY_IN_HAND);
        else if (stack.is(DotcPrimitives.DIVINE_RAPIER))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.DIVINE_RAPIER_IN_HAND);
        else if (stack.is(DotcTools.DESOLATOR))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.DESOLATOR_IN_HAND);
        else if (stack.is(DotcTools.HEAVENS_HALBERD))
            return itemModelShaper.getModelManager().getModel(DotcItemModels.HEAVENS_HALBERD_IN_HAND);
        else if (stack.is(DotcTools.RADIANCE)) {
            if (!stack.has(DotcComponents.USE_MODE_COMPONENT))
                return original;
            var mode = stack.get(DotcComponents.USE_MODE_COMPONENT);
            if (mode == Radiance.Mode.DISABLED.id)
                return itemModelShaper.getModelManager().getModel(DotcItemModels.RADIANCE);
            else if (mode == Radiance.Mode.PVE.id)
                return itemModelShaper.getModelManager().getModel(DotcItemModels.RADIANCE_PVE);
            else
                return itemModelShaper.getModelManager().getModel(DotcItemModels.RADIANCE_PVP);
        }
        return original;
    }

    @ModifyVariable(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/model/BakedModel;getTransforms()Lnet/minecraft/client/renderer/block/model/ItemTransforms;",
                    shift = At.Shift.BEFORE
            ),
            ordinal = 0
    )
    private BakedModel addDotcItemModelsInRender(
            BakedModel original,
            ItemStack stack,
            ItemDisplayContext context,
            boolean bl,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            int j
    ) {
        if (context == ItemDisplayContext.GUI || context == ItemDisplayContext.FIXED) {
            if (stack.is(DotcTools.MONKEY_KING_BAR))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.MONKEY_KING_BAR);
            else if (stack.is(DotcTools.CRYSTALYS))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.CRYSTALYS);
            else if (stack.is(DotcTools.DAEDALUS))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.DAEDALUS);
            else if (stack.is(DotcTools.BUTTERFLY))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.BUTTERFLY);
            else if (stack.is(DotcPrimitives.DIVINE_RAPIER))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.DIVINE_RAPIER);
            else if (stack.is(DotcTools.DESOLATOR))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.DESOLATOR);
            else if (stack.is(DotcTools.HEAVENS_HALBERD))
                return itemModelShaper.getModelManager().getModel(DotcItemModels.HEAVENS_HALBERD);
        }
        return original;
    }
}
