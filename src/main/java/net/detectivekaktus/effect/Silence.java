package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Silence extends MobEffect {
    protected Silence() {
        super(MobEffectCategory.HARMFUL, 0xFF1A70AD);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
