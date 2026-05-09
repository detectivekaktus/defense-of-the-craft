package net.detectivekaktus.core.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    public static List<ItemStack> getModInterestedSlots(Player player) {
        var inventory = player.getInventory();
        var slots = new ArrayList<>(inventory.items.subList(0, 9));
        slots.add(player.getOffhandItem());
        return slots;
    }
}
