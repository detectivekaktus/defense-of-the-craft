package net.detectivekaktus.item.tool;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;

import java.util.Optional;

import net.detectivekaktus.core.item.Procable;
import net.detectivekaktus.core.rng.PseudoRandomBaseChances;
import net.detectivekaktus.damage.DotcDamageTypes;
import net.detectivekaktus.item.DotcSwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class MonkeyKingBar extends DotcSwordItem implements Procable {
    public static final float BASE_PROC_CHANCE = PseudoRandomBaseChances.AVG_50;

    public MonkeyKingBar(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public float getProcDamage() {
        return 4.0f;
    }

    @Override
    public DamageSource getProcDamageSource(Player player) {
        return player.level().damageSources().source(
                DotcDamageTypes.MAGICAL,
                player,
                player
        );
    }

    @Override
    public Optional<SoundEvent> getProcSound() {
        return Optional.of(DotcItemSounds.MKB_PIERCE);
    }

    @Override
    public Optional<Holder<MobEffect>> getProcEffect() {
        return Optional.empty();
    }

    @Override
    public int getProcEffectDuration() {
        return 0;
    }

    @Override
    public int getProcCooldownInTicks() {
        return 0;
    }
}
