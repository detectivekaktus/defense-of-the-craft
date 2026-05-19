package net.detectivekaktus.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.detectivekaktus.DefenseOfTheCraft;

public class DotcEffects {
    public static final Holder<MobEffect> ARMOR_REDUCTION = register(
            "armor_reduction", new ArmorReduction()
    );
    public static final Holder<MobEffect> STUN = register(
            "stun", new DotcEffect(MobEffectCategory.HARMFUL, 0xFF5E2A8A)
    );
    public static final Holder<MobEffect> DISARM = register(
            "disarm", new DotcEffect(MobEffectCategory.HARMFUL, 0xFFD1D7D7)
    );
    public static final Holder<MobEffect> BREAK = register(
            "break", new DotcEffect(MobEffectCategory.HARMFUL, 0x007EA8ED)
    );
    public static final Holder<MobEffect> COMBO_BREAKER = register(
            "combo_breaker", new DotcEffect(MobEffectCategory.BENEFICIAL, 0xFFF5C27C)
    );
    public static final Holder<MobEffect> SILENCE = register(
            "silence", new DotcEffect(MobEffectCategory.HARMFUL, 0xFF1A70AD)
    );
    public static final Holder<MobEffect> SOUL_REND = register(
            "soul_rend", new DotcEffect(MobEffectCategory.HARMFUL, 0xFF1A70AD)
    );
    public static final Holder<MobEffect> ROOT = register(
            "root", new DotcEffect(MobEffectCategory.HARMFUL, 0xFFCF5C0E)
    );
    public static final Holder<MobEffect> SOUL_RELEASE = register(
            "soul_release", new SoulRelease()
    );

    public static Holder<MobEffect> register(String id, MobEffect effect) {
        return Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, id),
                effect
        );
    }

    public static void initialize() { }
}
