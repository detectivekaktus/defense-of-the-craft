package net.detectivekaktus.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

import net.detectivekaktus.tag.DotcEntityTypeTags;

public class DotcEntityTagProvider extends FabricTagProvider<EntityType<?>> {
    public DotcEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(DotcEntityTypeTags.DIFFUSAL_BLADE_INVULNERABLE)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WITHER)
                .add(EntityType.WARDEN);

        getOrCreateTagBuilder(DotcEntityTypeTags.ABYSSAL_BLADE_INVULNERABLE)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WITHER);

        getOrCreateTagBuilder(DotcEntityTypeTags.HEAVENS_HALBERD_INVULNERABLE)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WITHER);

        getOrCreateTagBuilder(DotcEntityTypeTags.HAND_OF_MIDAS_INVULNERABLE)
                .add(EntityType.PLAYER)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WITHER)
                .add(EntityType.WARDEN)
                .add(EntityType.BREEZE)
                .add(EntityType.VILLAGER)
                .add(EntityType.WITCH)
                .add(EntityType.PILLAGER)
                .add(EntityType.EVOKER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.RAVAGER)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.GHAST)
                .add(EntityType.SHULKER)
                .add(EntityType.IRON_GOLEM)
                .add(EntityType.SNIFFER);
    }
}
