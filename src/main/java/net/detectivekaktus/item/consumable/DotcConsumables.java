package net.detectivekaktus.item.consumable;

import net.minecraft.world.item.Item;

import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.item.DotcItems;
import net.detectivekaktus.item.TooltipBuilder;

public class DotcConsumables {
    public static final Item TANGO = DotcItems.register(
            new Tango(
                    new Item.Properties(),
                    new TooltipBuilder("tango")
                            .description()
                            .active()
            ),
            "tango"
    );
    public static final Item ENCHANTED_MANGO = DotcItems.register(
            new EnchantedMango(
                    new Item.Properties()
                            .food(DotcFoods.MANGO)
                            .stacksTo(16)
                            .component(DotcComponents.BONUS_HP_REGEN_COMPONENT, 0.2f),
                    new TooltipBuilder("enchanted_mango")
                            .description()
                            .passive()
                            .active()
            ),
            "enchanted_mango"
    );
    public static final Item DUST_OF_APPEARANCE = DotcItems.register(
            new DustOfAppearance(
                    new Item.Properties().stacksTo(16),
                    new TooltipBuilder("dust_of_appearance")
                            .description()
                            .active()
            ),
            "dust_of_appearance"
    );

    public static void initialize() { }
}
