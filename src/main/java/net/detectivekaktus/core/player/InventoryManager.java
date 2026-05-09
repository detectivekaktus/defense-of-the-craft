package net.detectivekaktus.core.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InventoryManager {
    /**
     * This is the most expensive method performance-wise. JIT is smart enough,
     * but I still fear that this may overwhelm the garbage collector if called too
     * frequently. Consider using other ones before calling this one.
     */
    public static List<ItemStack> getModInterestedSlots(Player player) {
        var inventory = player.getInventory();
        var slots = new ArrayList<>(inventory.items.subList(0, 9));
        slots.add(player.getOffhandItem());
        return slots;
    }

    public static void foreachModInterestedSlot(Player player, Consumer<ItemStack> consumer) {
        var inventory = player.getInventory();
        for (var i = 0; i < 9; i++)
            consumer.accept(inventory.items.get(i));

        consumer.accept(player.getOffhandItem());
    }

    public static boolean foreachModInterestedSlot(Player player, Predicate<ItemStack> predicate) {
        var inventory = player.getInventory();
        for (var i = 0; i < 9; i++)
            if (predicate.test(inventory.items.get(i)))
                return true;

        return predicate.test(player.getOffhandItem());
    }
}
