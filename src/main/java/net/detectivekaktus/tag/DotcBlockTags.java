package net.detectivekaktus.tag;

import net.detectivekaktus.DefenseOfTheCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class DotcBlockTags {
    public static final TagKey<Block> NON_SOLID_BLINKABLE = register("non_solid_blinkable");
    public static final TagKey<Block> ORES = register("ores");

    public static TagKey<Block> register(String id) {
        return TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, id)
        );
    }
}
