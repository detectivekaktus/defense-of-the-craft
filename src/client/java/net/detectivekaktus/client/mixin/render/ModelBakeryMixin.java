package net.detectivekaktus.client.mixin.render;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.detectivekaktus.client.core.DotcItemModels;

import java.util.List;
import java.util.Map;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin {
    @Shadow
    private void loadSpecialItemModelAndDependencies(ModelResourceLocation modelResourceLocation) {}
    @Shadow
    private void loadItemModelAndDependencies(ResourceLocation resourceLocation) {}

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void loadDotc2dModels(
            BlockColors blockColors,
            ProfilerFiller profilerFiller,
            Map<ResourceLocation, BlockModel> map,
            Map<ResourceLocation, List<BlockStateModelLoader.LoadedJson>> map2,
            CallbackInfo callbackInfo
    ) {
        loadItemModelAndDependencies(DotcItemModels.RADIANCE_PVE.id());
        loadItemModelAndDependencies(DotcItemModels.RADIANCE_PVP.id());
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 1
            )
    )
    private void loadDotc3dModels(
            BlockColors blockColors,
            ProfilerFiller profilerFiller,
            Map<ResourceLocation, BlockModel> map,
            Map<ResourceLocation, List<BlockStateModelLoader.LoadedJson>> map2,
            CallbackInfo callbackInfo
    ) {
        loadSpecialItemModelAndDependencies(DotcItemModels.MONKEY_KING_BAR_IN_HAND);
        loadSpecialItemModelAndDependencies(DotcItemModels.DIVINE_RAPIER_IN_HAND);
        loadSpecialItemModelAndDependencies(DotcItemModels.DESOLATOR_IN_HAND);
        loadSpecialItemModelAndDependencies(DotcItemModels.HEAVENS_HALBERD_IN_HAND);
    }
}
