package net.detectivekaktus.item.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import net.detectivekaktus.item.DotcSwordItem;
import net.detectivekaktus.item.TooltipBuilder;

public class Radiance extends DotcSwordItem {
    public Radiance(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);

        if (player.level().isClientSide)
            return InteractionResultHolder.pass(stack);

        return InteractionResultHolder.success(stack);
    }

    public enum Mode {
        DISABLED(0),
        PVE(1),
        PVP(2);

        public final int id;

        Mode(int id) {
            this.id = id;
        }

        public static Mode fromId(int id) {
            for (var val : Mode.values()) {
                if (val.id == id)
                    return val;
            }
            return DISABLED;
        }
    }
}
