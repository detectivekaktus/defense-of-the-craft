package net.detectivekaktus.core.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;

public class CooldownManager {
    public static final CooldownManager INSTANCE = new CooldownManager();
    // player -> item -> tick when cooldown expires
    private final Map<UUID, Map<Item, Long>> playerToCooldownsMap = new HashMap<>();

    public CooldownManager() { }

    private Map<Item, Long> getCooldowns(Player player) {
        var uuid = player.getUUID();
        return playerToCooldownsMap.computeIfAbsent(uuid, k -> new HashMap<>());
    }

    public void addCooldown(Player player, Item item, int duration) {
        var cooldowns = getCooldowns(player);
        player.getCooldowns().addCooldown(item, duration);
        cooldowns.put(item, player.level().getGameTime() + duration);
    }

    public void removeCooldown(Player player, Item item) {
        var cooldowns = getCooldowns(player);
        player.getCooldowns().removeCooldown(item);
        cooldowns.remove(item);
    }

    private void ensureOnCooldown(Player player, Item item, long expiryTick) {
        var cooldowns = player.getCooldowns();
        if (cooldowns.isOnCooldown(item))
            return;

        var remaining = expiryTick - player.level().getGameTime();
        cooldowns.addCooldown(item, (int) remaining);
    }

    public boolean isOnCooldown(Player player, Item item) {
        var cooldowns = getCooldowns(player);
        var cooldown = cooldowns.get(item);
        if (cooldown == null)
            return false;

        ensureOnCooldown(player, item, cooldown);
        return true;
    }

    public void tick(MinecraftServer server) {
        for (var playerCooldown : playerToCooldownsMap.entrySet()) {
            var uuid = playerCooldown.getKey();
            var player = server.getPlayerList().getPlayer(uuid);
            if (player == null)
                continue;

            var cooldowns = playerCooldown.getValue();
            if (cooldowns.isEmpty())
                continue;

            List<Item> expired = new ArrayList<>();
            for (var cooldown : cooldowns.entrySet()) {
                if (cooldown.getValue() <= player.level().getGameTime())
                    expired.add(cooldown.getKey());
            }
            expired.forEach(item -> removeCooldown(player, item));
        }
    }
}
