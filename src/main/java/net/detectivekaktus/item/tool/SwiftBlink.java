package net.detectivekaktus.item.tool;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import org.joml.Vector3f;

import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

import java.util.List;

public class SwiftBlink extends BlinkDagger {
    private final int EFFECTS_DURATION = 5 * 20;

    public SwiftBlink(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        super.invokeInteractionAbility(player, target, stack);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECTS_DURATION, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, EFFECTS_DURATION, 1));
    }

    @Override
    public ParticleOptions getAnimationParticle() {
        return new DustParticleOptions(new Vector3f(0.415f, 0.745f, 0.086f), 0.5f);
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.SWIFT_BLINK_TARGET;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.BLINK_DAGGER);
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }
}
