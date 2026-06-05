package net.detectivekaktus.client.data.item.ingredient;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.block.building.DotcBuildingBlocks;
import net.detectivekaktus.item.ingredient.DotcIngredients;

public class DotcIngredientsRecipeProvider extends FabricRecipeProvider {
    public DotcIngredientsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.RADIANT_CRYSTAL)
                .requires(DotcIngredients.RADIANT_CRYSTAL_SHARDS, 2)
                .requires(Items.DIAMOND)
                .unlockedBy(
                        "has_radiant_crystal_shards",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL_SHARDS)
                )
                .save(exporter, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "radiant_crystal_from_shards"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.RADIANT_CRYSTAL, 9)
                .requires(DotcBuildingBlocks.RADIANT_CRYSTAL_BLOCK)
                .unlockedBy(
                        "has_radiant_crystal_block",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcBuildingBlocks.RADIANT_CRYSTAL_BLOCK)
                )
                .save(exporter, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "radiant_crystal_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.DIRE_CRYSTAL)
                .requires(DotcIngredients.DIRE_CRYSTAL_SHARDS, 2)
                .requires(Items.DIAMOND)
                .unlockedBy(
                        "has_dire_crystal_shards",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL_SHARDS)
                )
                .save(exporter, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "dire_crystal_from_shards"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.DIRE_CRYSTAL, 9)
                .requires(DotcBuildingBlocks.DIRE_CRYSTAL_BLOCK)
                .unlockedBy(
                        "has_dire_crystal_block",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcBuildingBlocks.DIRE_CRYSTAL_BLOCK)
                )
                .save(exporter, ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, "dire_crystal_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.RADIANT_CRYSTAL_DUST, 2)
                .requires(DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .group("crystal_dust")
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.DIRE_CRYSTAL_DUST, 2)
                .requires(DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .group("crystal_dust")
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DotcIngredients.MITHRIL_INGOT)
                .requires(Ingredient.of(DotcIngredients.RADIANT_CRYSTAL, DotcIngredients.DIRE_CRYSTAL), 3)
                .requires(Items.IRON_INGOT, 2)
                .unlockedBy(
                        "has_radiant_crystal_and_iron",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                DotcIngredients.RADIANT_CRYSTAL, Items.IRON_INGOT
                        )
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.BLIGHT_STONE)
                .pattern(" # ")
                .pattern("#@#")
                .pattern(" # ")
                .define('@', Items.PUFFERFISH)
                .define('#', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.SACRED_RELIC)
                .pattern("$#$")
                .pattern("#@#")
                .pattern(" # ")
                .define('@', Items.HEART_OF_THE_SEA)
                .define('#', Items.GOLD_INGOT)
                .define('$', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_heart_of_the_sea",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.HEART_OF_THE_SEA)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.GLOVES_OF_HASTE)
                .pattern("## ")
                .pattern("## ")
                .pattern("  #")
                .define('#', Items.LEATHER)
                .unlockedBy(
                        "has_leather",
                        InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.TALISMAN_OF_EVASION)
                .pattern("#@#")
                .pattern("@ @")
                .pattern("#@#")
                .define('#', Items.GOLD_INGOT)
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.EAGLESONG)
                .pattern("@@$")
                .pattern("#  ")
                .pattern("###")
                .define('#', Items.IRON_INGOT)
                .define('@', Items.GOLD_INGOT)
                .define('$', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.BELT_OF_STRENGTH)
                .pattern("#@#")
                .pattern("@ @")
                .pattern("#@#")
                .define('#', Items.LEATHER)
                .define('@', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.BAND_OF_ELVENSKIN)
                .pattern(" @ ")
                .pattern("@@@")
                .pattern(" @ ")
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.ROBE_OF_THE_MAGI)
                .pattern(" @ ")
                .pattern("@#@")
                .pattern(" @ ")
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .define('#', Items.LEATHER_CHESTPLATE)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.VOID_STONE)
                .pattern("## ")
                .pattern(" @@")
                .pattern("   ")
                .define('#', Items.STONE)
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.RING_OF_HEALTH)
                .pattern("#@#")
                .pattern("@ @")
                .pattern("#@#")
                .define('#', Items.GOLD_INGOT)
                .define('@', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.SHADOW_AMULET)
                .pattern("$ $")
                .pattern("#@#")
                .pattern(" # ")
                .define('@', DotcIngredients.RADIANT_CRYSTAL)
                .define('#', Items.AMETHYST_SHARD)
                .define('$', Items.CHAIN)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.RING_OF_TARRASQUE)
                .pattern("@#@")
                .pattern("# #")
                .pattern("@#@")
                .define('@', DotcIngredients.DIRE_CRYSTAL)
                .define('#', Items.COPPER_INGOT)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.ENERGY_BOOSTER)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .define('@', Items.HEART_OF_THE_SEA)
                .define('#', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.VITALITY_BOOSTER)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .define('@', Items.HEART_OF_THE_SEA)
                .define('#', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.SAGES_MASK)
                .pattern("   ")
                .pattern("#@#")
                .pattern("   ")
                .define('@', Items.LEATHER_HELMET)
                .define('#', DotcIngredients.RADIANT_CRYSTAL)
                .unlockedBy(
                        "has_radiant_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL)
                )
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DotcIngredients.MORBID_MASK)
                .pattern("###")
                .pattern("#@#")
                .pattern("###")
                .define('@', Items.WITHER_SKELETON_SKULL)
                .define('#', DotcIngredients.DIRE_CRYSTAL)
                .unlockedBy(
                        "has_dire_crystal",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL)
                )
                .save(exporter);
    }

    @Override
    public String getName() {
        return "defense-of-the-craft:ingredient_recipes";
    }
}
