package net.detectivekaktus.client.data.item.misc;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

import java.util.stream.Stream;

import net.detectivekaktus.item.misc.DotcMiscItems;

public class DotcMiscModelProvider extends FabricModelProvider {
    public DotcMiscModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) { }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        Stream.of(
                DotcMiscItems.RADIANT_THEME_MUSIC_DISC,
                DotcMiscItems.DIRE_THEME_MUSIC_DISC
        ).forEach(item -> itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
    }

    @Override
    public String getName() {
        return "defense-of-the-craft:misc_models";
    }
}
