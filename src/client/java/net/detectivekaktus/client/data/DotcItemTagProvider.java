package net.detectivekaktus.client.data;

import net.detectivekaktus.tag.DotcItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import net.detectivekaktus.item.primitive.DotcPrimitives;
import net.detectivekaktus.item.ingredient.DotcIngredients;
import net.detectivekaktus.item.tool.DotcTools;

import java.util.concurrent.CompletableFuture;

public class DotcItemTagProvider extends FabricTagProvider<Item> {
    public DotcItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(DotcPrimitives.MITHRIL_HAMMER)
                .add(DotcTools.DAEDALUS)
                .add(DotcTools.SKULL_BASHER);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(DotcIngredients.BLADES_OF_ATTACK)
                .add(DotcPrimitives.BLADE_OF_ALACRITY)
                .add(DotcPrimitives.BROADSWORD)
                .add(DotcPrimitives.CLAYMORE)
                .add(DotcPrimitives.DEMON_EDGE)
                .add(DotcPrimitives.STAFF_OF_WIZARDRY)
                .add(DotcTools.CRYSTALYS)
                .add(DotcTools.BUTTERFLY)
                .add(DotcTools.SANGE)
                .add(DotcTools.YASHA)
                .add(DotcTools.KAYA)
                .add(DotcPrimitives.DIVINE_RAPIER)
                .add(DotcTools.ECHO_SABRE)
                .add(DotcTools.DIFFUSAL_BLADE)
                .add(DotcTools.ABYSSAL_BLADE)
                .add(DotcTools.SHADOW_BLADE)
                .add(DotcTools.SILVER_EDGE)
                .add(DotcTools.RADIANCE)
                .add(DotcTools.BLINK_DAGGER)
                .add(DotcTools.SWIFT_BLINK);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(DotcPrimitives.OGRE_AXE);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(DotcTools.DESOLATOR);

        getOrCreateTagBuilder(DotcItemTags.CRYSTAL_DUSTS)
                .add(DotcIngredients.RADIANT_CRYSTAL_DUST)
                .add(DotcIngredients.DIRE_CRYSTAL_DUST);

        getOrCreateTagBuilder(DotcItemTags.CRYSTALS)
                .add(DotcIngredients.RADIANT_CRYSTAL)
                .add(DotcIngredients.DIRE_CRYSTAL);

        getOrCreateTagBuilder(DotcItemTags.SHADOW_WALKABLE)
                .add(DotcIngredients.SHADOW_AMULET)
                .add(DotcTools.SHADOW_BLADE)
                .add(DotcTools.SILVER_EDGE);
    }

    @Override
    public String getName() {
        return "defense-of-the-craft:item_tags";
    }
}
