package net.detectivekaktus.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import net.detectivekaktus.DefenseOfTheCraft;

public class DotcEntityTypeTags {
    public static final TagKey<EntityType<?>> DIFFUSAL_BLADE_INVULNERABLE = register("diffusal_blade_invulnerable");
    public static final TagKey<EntityType<?>> ABYSSAL_BLADE_INVULNERABLE = register("abyssal_blade_invulnerable");
    public static final TagKey<EntityType<?>> HEAVENS_HALBERD_INVULNERABLE = register("heavens_halberd_invulnerable");
    public static final TagKey<EntityType<?>> HAND_OF_MIDAS_INVULNERABLE = register("hand_of_midas_invulnerable");
    public static final TagKey<EntityType<?>> ROD_OF_ATOS_INVULNERABLE = register("rod_of_atos_invulnerable");

    public static TagKey<EntityType<?>> register(String id) {
        return TagKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, id)
        );
    }

    public static void initialize() { }
}
