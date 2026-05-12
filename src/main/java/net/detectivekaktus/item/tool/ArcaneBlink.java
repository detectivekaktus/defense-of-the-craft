package net.detectivekaktus.item.tool;

import net.detectivekaktus.attach.PlayerMana;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.joml.Vector3f;

import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

import java.util.List;

public class ArcaneBlink extends BlinkDagger {
    public ArcaneBlink(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        super.invokeInteractionAbility(player, target, stack);
        var mana = PlayerMana.get(player);
        player.heal(4.0f);
        mana.increment(30.0f);
    }

    @Override
    public ParticleOptions getAnimationParticle() {
        return new DustParticleOptions(new Vector3f(0.960f, 0.258f, 0.666f), 0.5f);
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.ARCANE_BLINK_TARGET;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.BLINK_DAGGER, DotcTools.SWIFT_BLINK, DotcTools.OVERWHELMING_BLINK);
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }
}
