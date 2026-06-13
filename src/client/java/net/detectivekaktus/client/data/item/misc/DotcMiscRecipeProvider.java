package net.detectivekaktus.client.data.item.misc;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import net.detectivekaktus.item.ingredient.DotcIngredients;
import net.detectivekaktus.item.misc.DotcMiscItems;

public class DotcMiscRecipeProvider extends FabricRecipeProvider {
    public DotcMiscRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcMiscItems.RADIANT_THEME_MUSIC_DISC)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .define('#', Blocks.BASALT)
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcMiscItems.DIRE_THEME_MUSIC_DISC)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .define('#', Blocks.BASALT)
                .define('@', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);
    }

    @Override
    public String getName() {
        return "defense-of-the-craft:misc_recipes";
    }
}
