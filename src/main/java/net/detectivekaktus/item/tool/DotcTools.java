package net.detectivekaktus.item.tool;

import net.minecraft.world.item.Item;

import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.component.records.ProcableComponent;
import net.detectivekaktus.component.records.ItemStatsComponent;
import net.detectivekaktus.core.rng.PseudoRandomBaseChances;
import net.detectivekaktus.item.*;
import net.detectivekaktus.item.material.DotcToolMaterial;

public class DotcTools {
    public static final Item JAVELIN = DotcItems.register(
            new Javelin(
                    DotcToolMaterial.DIRE_COMPONENT,
                    new Item.Properties()
                            .attributes(DotcSwordItem.createAttributes(
                                    DotcToolMaterial.DIRE_COMPONENT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(Javelin.BASE_PROC_CHANCE, 0)
                            ),
                    new TooltipBuilder("javelin").description().passive()
            ),
            "javelin"
    );
    public static final Item CRYSTALYS = DotcItems.register(
            new Crystalys(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(
                                    DotcSwordItem.createAttributes(
                                            DotcToolMaterial.DIRE_ARTEFACT, 1, -2.0f
                                    )
                            )
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(Crystalys.BASE_PROC_CHANCE, 0)
                            ),
                    new TooltipBuilder("crystalys").description().passive()
            ),
            "crystalys"
    );
    public static final Item DAEDALUS = DotcItems.register(
            new Daedalus(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(DotcPickaxeItem.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 3, -2.0f
                            ))
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(Daedalus.BASE_PROC_CHANCE, 0)
                            ),
                    new TooltipBuilder("daedalus").description().passive()
            ),
            "daedalus"
    );
    public static final Item BUTTERFLY = DotcItems.register(
            new DotcSwordItem(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(
                                    DotcSwordItem.createAttributes(
                                            DotcToolMaterial.RADIANT_ARTEFACT, 3, -2.0f
                                    )
                            )
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 35, 0)
                            )
                            .component(
                                    DotcComponents.EVASION_COMPONENT,
                                    PseudoRandomBaseChances.AVG_20
                            ),
                    new TooltipBuilder("butterfly")
                            .description()
                            .stats(0, 35, 0)
                            .passive()
            ),
            "butterfly"
    );
    public static final Item MONKEY_KING_BAR = DotcItems.register(
            new MonkeyKingBar(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(DotcSwordItem.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 2, -1.5f
                            ))
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(MonkeyKingBar.BASE_PROC_CHANCE, 0)
                            ),
                    new TooltipBuilder("monkey_king_bar").description().passive()
            ),
            "monkey_king_bar"
    );
    public static final Item ECHO_SABRE = DotcItems.register(
            new DotcSwordItem(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(DotcSwordItem.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(15, 0, 0)
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_REGEN_COMPONENT,
                                    1.75f
                            ),
                    new TooltipBuilder("echo_sabre")
                            .description()
                            .stats(15, 0, 0)
                            .passive()
            ),
            "echo_sabre"
    );
    public static final Item MAGIC_STICK = DotcItems.register(
            new MagicStick(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(
                                    DotcComponents.CHARGEABLE_COMPONENT,
                                    new ChargeableComponent(0, 10, 0)
                            ),
                    new TooltipBuilder("magic_stick")
                            .description()
                            .active()
            ),
            "magic_stick"
    );
    public static final Item MAGIC_WAND = DotcItems.register(
            new MagicStick(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(3, 3, 3)
                            )
                            .component(
                                    DotcComponents.CHARGEABLE_COMPONENT,
                                    new ChargeableComponent(0, 20, 0)
                            ),
                    new TooltipBuilder("magic_wand")
                            .description()
                            .stats(3, 3, 3)
                            .active()
            ),
            "magic_wand"
    );
    public static final Item DIFFUSAL_BLADE = DotcItems.register(
            new DiffusalBlade(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(DotcSwordItem.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -1.75f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 15, 10)
                            ),
                    new TooltipBuilder("diffusal_blade")
                            .description()
                            .stats(0, 15, 10)
                            .passive()
                            .active()
            ),
            "diffusal_blade"
    );
    public static final Item DESOLATOR = DotcItems.register(
            new Desolator(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(DotcHoeItem.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 3, -2.0f
                            )),
                    new TooltipBuilder("desolator")
                            .description()
                            .passive()
            ),
            "desolator"
    );
    public static final Item SKULL_BASHER = DotcItems.register(
            new SkullBasher(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(SkullBasher.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 2, -2.5f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(10, 0, 0)
                            )
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(SkullBasher.BASE_PROC_CHANCE, 0)
                            ),
                    new TooltipBuilder("skull_basher")
                            .description()
                            .stats(10, 0, 0)
                            .passive()
            ),
            "skull_basher"
    );
    public static final Item ABYSSAL_BLADE = DotcItems.register(
            new AbyssalBlade(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(AbyssalBlade.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 3, -2.5f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(26, 0, 0)
                            )
                            .component(
                                    DotcComponents.PROCABLE_COMPONENT,
                                    new ProcableComponent(AbyssalBlade.BASE_PROC_CHANCE, 0)
                            )
                            .component(
                                    DotcComponents.HP_REGEN_AMPLIFICATION_COMPONENT,
                                    0.16f
                            ),
                    new TooltipBuilder("abyssal_blade")
                            .description()
                            .stats(26, 0, 0)
                            .passive()
                            .active()
            ),
            "abyssal_blade"
    );
    public static final Item HEAVENS_HALBERD = DotcItems.register(
            new HeavensHalberd(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(HeavensHalberd.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 3, -2.0f
                            ))
                            .component(
                                    DotcComponents.EVASION_COMPONENT,
                                    PseudoRandomBaseChances.AVG_15
                            ),
                    new TooltipBuilder("heavens_halberd")
                            .description()
                            .passive()
                            .active()
            ),
            "heavens_halberd"
    );
    public static final Item HAND_OF_MIDAS = DotcItems.register(
            new HandOfMidas(
                    new Item.Properties()
                            .component(
                                    DotcComponents.USE_COUNTER_COMPONENT,
                                    0
                            ),
                    new TooltipBuilder("hand_of_midas")
                            .description()
                            .active()
            ),
            "hand_of_midas"
    );
    public static final Item SHADOW_BLADE = DotcItems.register(
            new ShadowBlade(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(ShadowBlade.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 3, -2.25f
                            )),
                    new TooltipBuilder("shadow_blade")
                            .description()
                            .active()
            ),
            "shadow_blade"
    );
    public static final Item SILVER_EDGE = DotcItems.register(
            new SilverEdge(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(SilverEdge.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 2, -2.0f
                            )),
                    new TooltipBuilder("silver_edge")
                            .description()
                            .active()
            ),
            "silver_edge"
    );
    public static final Item RADIANCE = DotcItems.register(
            new Radiance(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(Radiance.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 3, -2.75f
                            ))
                            .component(
                                    DotcComponents.USE_MODE_COMPONENT,
                                    Radiance.Mode.DISABLED.id
                            )
                            .component(
                                    DotcComponents.EVASION_COMPONENT,
                                    PseudoRandomBaseChances.AVG_10
                            ),
                    new TooltipBuilder("radiance")
                            .description()
                            .passive()
                            .active()
            ),
            "radiance"
    );
    public static final Item BLINK_DAGGER = DotcItems.register(
            new BlinkDagger(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(BlinkDagger.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 1, -2.0f
                            )),
                    new TooltipBuilder("blink_dagger")
                            .description()
                            .active()
            ),
            "blink_dagger"
    );
    public static final Item SWIFT_BLINK = DotcItems.register(
            new SwiftBlink(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(SwiftBlink.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 25, 0)
                            ),
                    new TooltipBuilder("swift_blink")
                            .description()
                            .stats(0, 25, 0)
                            .active()
            ),
            "swift_blink"
    );
    public static final Item ARCANE_BLINK = DotcItems.register(
            new ArcaneBlink(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(ArcaneBlink.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 0, 25)
                            ),
                    new TooltipBuilder("arcane_blink")
                            .description()
                            .stats(0, 0, 25)
                            .active()
            ),
            "arcane_blink"
    );
    public static final Item OVERWHELMING_BLINK = DotcItems.register(
            new OverwhelmingBlink(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(OverwhelmingBlink.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(25, 0, 0)
                            ),
                    new TooltipBuilder("overwhelming_blink")
                            .description()
                            .stats(25, 0, 0)
                            .active()
            ),
            "overwhelming_blink"
    );
    public static final Item HEART_OF_TARRASQUE = DotcItems.register(
            new DotcItem(
                    new Item.Properties()
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(35, 0, 0)
                            )
                            .component(
                                    DotcComponents.BONUS_HP_REGEN_COMPONENT,
                                    4.0f
                            )
                            .component(
                                    DotcComponents.HP_REGEN_AMPLIFICATION_COMPONENT,
                                    0.2f
                            ),
                    new TooltipBuilder("heart_of_tarrasque")
                            .description()
                            .stats(35, 0, 0)
                            .passive()
            ),
            "heart_of_tarrasque"
    );
    public static final Item AEON_DISK = DotcItems.register(
            new DotcItem(
                    new Item.Properties()
                            .component(
                                    DotcComponents.BONUS_HP_COMPONENT,
                                    4.0f
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_COMPONENT,
                                    50.0f
                            ),
                    new TooltipBuilder("aeon_disk")
                            .description()
                            .passive()
            ),
            "aeon_disk"
    );
    public static final Item ORCHID_MALEVOLENCE = DotcItems.register(
            new OrchidMalevolence(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(OrchidMalevolence.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -1.75f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 0, 12)
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_REGEN_COMPONENT,
                                    2.0f
                            ),
                    new TooltipBuilder("orchid_malevolence")
                            .description()
                            .stats(0, 0, 12)
                            .passive()
                            .active()
            ),
            "orchid_malevolence"
    );
    public static final Item BLOODTHORN = DotcItems.register(
            new Bloodthorn(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(Bloodthorn.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 3, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 0, 20)
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_REGEN_COMPONENT,
                                    3.0f
                            ),
                    new TooltipBuilder("bloodthorn")
                            .description()
                            .stats(0, 0, 20)
                            .passive()
                            .active()
            ),
            "bloodthorn"
    );
    public static final Item ROD_OF_ATOS = DotcItems.register(
            new RodOfAtos(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(RodOfAtos.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -2.25f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 0, 15)
                            )
                            .component(
                                    DotcComponents.BONUS_HP_COMPONENT,
                                    4.5f
                            ),
                    new TooltipBuilder("rod_of_atos")
                            .description()
                            .stats(0, 0, 15)
                            .passive()
                            .active()
            ),
            "rod_of_atos"
    );
    public static final Item URN_OF_SHADOWS = DotcItems.register(
            new UrnOfShadows(
                    new Item.Properties()
                            .component(
                                    DotcComponents.BONUS_HP_REGEN_COMPONENT,
                                    0.5f
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_REGEN_COMPONENT,
                                    0.5f
                            )
                            .component(
                                    DotcComponents.CHARGEABLE_COMPONENT,
                                    new ChargeableComponent(0, 5, 0)
                            ),
                    new TooltipBuilder("urn_of_shadows")
                            .description()
                            .passive()
                            .active()
            ),
            "urn_of_shadows"
    );
    public static final Item SPIRIT_VESSEL = DotcItems.register(
            new SpiritVessel(
                    new Item.Properties()
                            .component(
                                    DotcComponents.BONUS_HP_REGEN_COMPONENT,
                                    0.75f
                            )
                            .component(
                                    DotcComponents.BONUS_MANA_REGEN_COMPONENT,
                                    0.75f
                            )
                            .component(
                                    DotcComponents.CHARGEABLE_COMPONENT,
                                    new ChargeableComponent(0, 10, 0)
                            ),
                    new TooltipBuilder("spirit_vessel")
                            .description()
                            .passive()
                            .active()
            ),
            "spirit_vessel"
    );
    public static final Item FORCE_STAFF = DotcItems.register(
            new ForceStaff(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(ForceStaff.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 2, -2.0f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(0, 0, 12)
                            ),
                    new TooltipBuilder("force_staff")
                            .description()
                            .stats(0, 0, 12)
                            .active()
            ),
            "force_staff"
    );
    public static final Item HURRICANE_PIKE = DotcItems.register(
            new HurricanePike(
                    DotcToolMaterial.RADIANT_ARTEFACT,
                    new Item.Properties()
                            .attributes(HurricanePike.createAttributes(
                                    DotcToolMaterial.RADIANT_ARTEFACT, 3, -2.25f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(10, 15, 12)
                            ),
                    new TooltipBuilder("hurricane_pike")
                            .description()
                            .stats(10, 15, 12)
                            .active()
            ),
            "hurricane_pike"
    );
    public static final Item SATANIC = DotcItems.register(
            new Satanic(
                    DotcToolMaterial.DIRE_ARTEFACT,
                    new Item.Properties()
                            .attributes(Satanic.createAttributes(
                                    DotcToolMaterial.DIRE_ARTEFACT, 2, -2.5f
                            ))
                            .component(
                                    DotcComponents.ITEM_STATS_COMPONENT,
                                    new ItemStatsComponent(25, 0, 0)
                            )
                            .component(
                                    DotcComponents.LIFE_STEAL_COMPONENT,
                                    0.2f
                            ),
                    new TooltipBuilder("satanic")
                            .description()
                            .stats(25, 0, 0)
                            .passive()
                            .active()
            ),
            "satanic"
    );

    public static void initialize() { }
}
