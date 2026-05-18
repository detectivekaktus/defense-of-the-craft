package net.detectivekaktus.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Root extends MobEffect {
    protected Root() {
        super(MobEffectCategory.HARMFUL, 0xFFCF5C0E);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
