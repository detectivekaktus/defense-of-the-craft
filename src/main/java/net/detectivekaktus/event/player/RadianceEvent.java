package net.detectivekaktus.event.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.DefenseOfTheCraft;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.core.player.InventoryManager;
import net.detectivekaktus.damage.DotcDamageTypes;
import net.detectivekaktus.item.tool.DotcTools;
import net.detectivekaktus.item.tool.Radiance;

public class RadianceEvent {
    public static void tick(ServerPlayer player) {
        var flags = PlayerFlags.get(player);
        var utilTick = flags.getUtilTick();
        if (utilTick % 20 != 0 || utilTick == 0)
            return;

        // You can't have non-final values inside lambdas, so I came up
        // with this wrapper hoping it should work just fine
        var modeWrapper = new Object() {
            Radiance.Mode mode = Radiance.Mode.DISABLED;
        };
        var hasRadiance = InventoryManager.foreachModInterestedSlot(
                player,
                stack -> {
                    var isRadiance = stack.is(DotcTools.RADIANCE);
                    if (isRadiance && stack.has(DotcComponents.USE_MODE_COMPONENT))
                        modeWrapper.mode = Radiance.Mode.fromId(stack.get(DotcComponents.USE_MODE_COMPONENT));
                    return isRadiance;
                }
        );
        if (!hasRadiance || modeWrapper.mode == Radiance.Mode.DISABLED)
            return;

        var aabb = new AABB(player.getOnPos()).inflate(4);
        var level = player.level();
        var server = level.getServer();
        if (server == null) {
            DefenseOfTheCraft.LOGGER.error("No server instance was found during Radiance event. No damage will be applies in these 20 ticks.");
            return;
        }

        var entities = modeWrapper.mode == Radiance.Mode.PVE
                ? level.getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity instanceof Enemy
        )
                : level.getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity != player && (server.isPvpAllowed() || entity instanceof Enemy)
        );
        entities.forEach(entity -> entity.hurt(player.damageSources().source(DotcDamageTypes.MAGICAL), 2.0f));
    }
}
