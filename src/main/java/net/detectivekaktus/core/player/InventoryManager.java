package net.detectivekaktus.core.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InventoryManager {
    public static List<ItemStack> getModInterestedSlots(Player player) {
        var inventory = player.getInventory();
        var items = inventory.items.subList(0, 9);
        items.add(player.getOffhandItem());
        return items;
    }
}
