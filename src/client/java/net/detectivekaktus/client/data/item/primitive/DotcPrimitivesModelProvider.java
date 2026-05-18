package net.detectivekaktus.client.data.item.primitive;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

import java.util.stream.Stream;

import net.detectivekaktus.item.primitive.DotcPrimitives;

public class DotcPrimitivesModelProvider extends FabricModelProvider {
    public DotcPrimitivesModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) { }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        Stream.of(
                DotcPrimitives.MITHRIL_HAMMER,
                DotcPrimitives.BROADSWORD,
                DotcPrimitives.CLAYMORE,
                DotcPrimitives.BLADE_OF_ALACRITY,
                DotcPrimitives.OGRE_AXE,
                DotcPrimitives.STAFF_OF_WIZARDRY,
                DotcPrimitives.DEMON_EDGE,
                DotcPrimitives.DIVINE_RAPIER,
                DotcPrimitives.MYSTIC_STAFF,
                DotcPrimitives.REAVER,
                DotcPrimitives.OBLIVION_STAFF
        ).forEach(item -> itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM));
    }

    @Override
    public String getName() {
        return "defense-of-the-craft:primitives_models";
    }
}
