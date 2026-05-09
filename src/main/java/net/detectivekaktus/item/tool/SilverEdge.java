package net.detectivekaktus.item.tool;

import net.detectivekaktus.item.TooltipBuilder;
import net.minecraft.world.item.Tier;

public class SilverEdge extends ShadowBlade {
    public SilverEdge(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 20 * 20;
    }
}
