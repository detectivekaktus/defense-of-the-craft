package net.detectivekaktus.event.player;

import net.minecraft.world.entity.player.Player;

import net.detectivekaktus.attach.PlayerStats;
import net.detectivekaktus.effect.DotcEffects;

public class HpRegenEvent {
    public static void tick(Player player) {
        var stats = PlayerStats.get(player);
        var hpTick = stats.getHpTick();

        if (hpTick >= 20) {
            stats.setHpTick(0);
            if (player.hasEffect(DotcEffects.BREAK))
                return;

            var regen = stats.getHpRegen() + stats.getBonusHpRegen();
            if (regen > 0) {
                regen *= (1.0f + stats.getHpRegenAmplification());
                player.heal(regen);
            }
            return;
        }

        stats.setHpTick(++hpTick);
    }
}
