package net.detectivekaktus.item.tool;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.List;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.core.player.ShadowWalkingSource;
import net.detectivekaktus.item.TooltipBuilder;

public class SilverEdge extends ShadowBlade {
    public SilverEdge(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        super.invokeInteractionAbility(player, target, stack);
        var flags = PlayerFlags.get(player);
        flags.setShadowWalkingSource(ShadowWalkingSource.SILVER_EDGE);
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 20 * 20;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.SHADOW_BLADE);
    }
}
