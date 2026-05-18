package net.detectivekaktus.effect;

import net.detectivekaktus.DefenseOfTheCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class DotcEffects {
    public static final Holder<MobEffect> ARMOR_REDUCTION = register("armor_reduction", new ArmorReduction());
    public static final Holder<MobEffect> STUN = register("stun", new Stun());
    public static final Holder<MobEffect> DISARM = register("disarm", new Disarm());
    public static final Holder<MobEffect> BREAK = register("break", new Break());
    public static final Holder<MobEffect> COMBO_BREAKER = register("combo_breaker", new ComboBreaker());
    public static final Holder<MobEffect> SILENCE = register("silence", new Silence());
    public static final Holder<MobEffect> SOUL_REND = register("soul_rend", new Silence());

    public static Holder<MobEffect> register(String id, MobEffect effect) {
        return Registry.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(DefenseOfTheCraft.MOD_ID, id),
                effect
        );
    }

    public static void initialize() { }
}
