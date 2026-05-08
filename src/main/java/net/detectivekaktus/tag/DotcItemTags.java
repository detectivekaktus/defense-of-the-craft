package net.detectivekaktus.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.detectivekaktus.DefenseOfTheCraft;

public class DotcItemTags {
    public static final TagKey<Item> CRYSTAL_DUSTS = register("crystal_dusts");
    public static final TagKey<Item> CRYSTALS = register("crystals");

    public static TagKey<Item> register(String id) {
        return TagKey.create(
                Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, id)
        );
    }

    public static void initialize() { }
}
