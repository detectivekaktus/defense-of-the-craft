package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ComboBreaker extends MobEffect {
    protected ComboBreaker() {
        super(MobEffectCategory.BENEFICIAL, 0xFFF5C27C);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
