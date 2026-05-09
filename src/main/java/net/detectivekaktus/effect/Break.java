package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Break extends MobEffect {
    protected Break() {
        super(MobEffectCategory.HARMFUL, 0x007EA8ED);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
