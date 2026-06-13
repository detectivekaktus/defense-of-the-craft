package net.detectivekaktus.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.detectivekaktus.item.ingredient.DotcIngredients;
import net.detectivekaktus.item.primitive.DotcPrimitives;
import net.detectivekaktus.item.tool.DotcTools;

public class DotcAdvancementProvider extends FabricAdvancementProvider {
    public DotcAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        var root = Advancement.Builder.advancement()
                .display(
                        DotcIngredients.DIRE_CRYSTAL,
                        Component.translatable("advancement.defense-of-the-craft.root"),
                        Component.translatable("advancement.defense-of-the-craft.root.description"),
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("killed_something", KilledTrigger.TriggerInstance.playerKilledEntity())
                .addCriterion("killed_by_something", KilledTrigger.TriggerInstance.entityKilledPlayer())
                .save(consumer, "defense-of-the-craft:root");

        var radiant = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        DotcIngredients.RADIANT_CRYSTAL,
                        Component.translatable("advancement.defense-of-the-craft.shiny"),
                        Component.translatable("advancement.defense-of-the-craft.shiny.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_radiant_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.RADIANT_CRYSTAL))
                .save(consumer, "defense-of-the-craft:get_radiant_crystal");

        Advancement.Builder.advancement()
                .parent(radiant)
                .display(
                        DotcTools.HAND_OF_MIDAS,
                        Component.translatable("advancement.defense-of-the-craft.im_not_in_it_for_the_money"),
                        Component.translatable("advancement.defense-of-the-craft.im_not_in_it_for_the_money.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_hand_of_midas", InventoryChangeTrigger.TriggerInstance.hasItems(DotcTools.HAND_OF_MIDAS))
                .save(consumer, "defense-of-the-craft:get_hand_of_midas");

        Advancement.Builder.advancement()
                .parent(radiant)
                .display(
                        DotcPrimitives.YASHA,
                        Component.translatable("advancement.defense-of-the-craft.easy_now"),
                        Component.translatable("advancement.defense-of-the-craft.easy_now.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_yasha", InventoryChangeTrigger.TriggerInstance.hasItems(DotcPrimitives.YASHA))
                .save(consumer, "defense-of-the-craft:get_yasha");

        Advancement.Builder.advancement()
                .parent(radiant)
                .display(
                        DotcTools.BLINK_DAGGER,
                        Component.translatable("advancement.defense-of-the-craft.antiquated_but_effective"),
                        Component.translatable("advancement.defense-of-the-craft.antiquated_but_effective.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("got_blink_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(DotcTools.BLINK_DAGGER))
                .save(consumer, "defense-of-the-craft:get_blink_dagger");

        Advancement.Builder.advancement()
                .parent(radiant)
                .display(
                        DotcIngredients.SHADOW_AMULET,
                        Component.translatable("advancement.defense-of-the-craft.proper_skux"),
                        Component.translatable("advancement.defense-of-the-craft.proper_skux.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_shadow_amulet", InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.SHADOW_AMULET))
                .save(consumer, "defense-of-the-craft:get_shadow_amulet");

        var dire = Advancement.Builder.advancement()
                .parent(radiant)
                .display(
                        DotcIngredients.DIRE_CRYSTAL,
                        Component.translatable("advancement.defense-of-the-craft.i_wonder_what_this_does"),
                        Component.translatable("advancement.defense-of-the-craft.i_wonder_what_this_does.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_dire_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(DotcIngredients.DIRE_CRYSTAL))
                .save(consumer, "defense-of-the-craft:get_dire_crystal");

        Advancement.Builder.advancement()
                .parent(dire)
                .display(
                        DotcTools.RADIANCE,
                        Component.translatable("advancement.defense-of-the-craft.all_the_sexy"),
                        Component.translatable("advancement.defense-of-the-craft.all_the_sexy.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        true
                )
                .addCriterion("got_radiance", InventoryChangeTrigger.TriggerInstance.hasItems(DotcTools.RADIANCE))
                .save(consumer, "defense-of-the-craft:get_radiance");

        Advancement.Builder.advancement()
                .parent(dire)
                .display(
                        DotcTools.HEART_OF_TARRASQUE,
                        Component.translatable("advancement.defense-of-the-craft.song_in_heart"),
                        Component.translatable("advancement.defense-of-the-craft.song_in_heart.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_heart_of_tarrasque", InventoryChangeTrigger.TriggerInstance.hasItems(DotcTools.HEART_OF_TARRASQUE))
                .save(consumer, "defense-of-the-craft:get_heart_of_tarrasque");

        Advancement.Builder.advancement()
                .parent(dire)
                .display(
                        DotcTools.URN_OF_SHADOWS,
                        Component.translatable("advancement.defense-of-the-craft.souls"),
                        Component.translatable("advancement.defense-of-the-craft.souls.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("got_urn_of_shadows", InventoryChangeTrigger.TriggerInstance.hasItems(DotcTools.URN_OF_SHADOWS))
                .save(consumer, "defense-of-the-craft:get_urn_of_shadows");

        Advancement.Builder.advancement()
                .parent(dire)
                .display(
                        DotcPrimitives.DIVINE_RAPIER,
                        Component.translatable("advancement.defense-of-the-craft.they_sell_it_to_kids"),
                        Component.translatable("advancement.defense-of-the-craft.they_sell_it_to_kids.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("got_divine_rapier", InventoryChangeTrigger.TriggerInstance.hasItems(DotcPrimitives.DIVINE_RAPIER))
                .save(consumer, "defense-of-the-craft:get_divine_rapier");
    }
}
