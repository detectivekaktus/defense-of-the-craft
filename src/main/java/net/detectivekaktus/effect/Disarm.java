package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Disarm extends MobEffect {
    protected Disarm() {
        super(MobEffectCategory.HARMFUL, 0xFFD1D7D7);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
