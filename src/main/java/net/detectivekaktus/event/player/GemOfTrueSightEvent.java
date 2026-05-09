package net.detectivekaktus.event.player;

import net.detectivekaktus.core.player.InventoryManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import net.detectivekaktus.item.consumable.DotcConsumables;

public class GemOfTrueSightEvent {
    public static void tick(Player player) {
        var hasGem = false;
        var slots = InventoryManager.getModInterestedSlots(player);
        for (var item : slots) {
            if (item.is(DotcConsumables.GEM_OF_TRUE_SIGHT)) {
                hasGem = true;
                break;
            }
        }

        if (!hasGem)
            return;

        var aabb = new AABB(player.getOnPos()).inflate(7);
        var entities = player.level().getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity != player && entity.hasEffect(MobEffects.INVISIBILITY)
        );
        entities.forEach(entity -> entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10)));
    }
}
