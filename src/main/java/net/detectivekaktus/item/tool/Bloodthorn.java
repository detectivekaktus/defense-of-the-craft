package net.detectivekaktus.item.tool;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.detectivekaktus.core.item.Procable;
import net.detectivekaktus.core.item.SharesUseCooldown;
import net.detectivekaktus.damage.DotcDamageTypes;
import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

import java.util.List;
import java.util.Optional;

public class Bloodthorn extends DotcAbilitySwordItem implements Procable, SharesUseCooldown {
    public Bloodthorn(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return interactWithItem(player, livingEntity, itemStack).getResult();
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        target.addEffect(new MobEffectInstance(DotcEffects.SOUL_REND, 5 * 20));
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.BLOODTHORN;
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 20 * 20;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.ORCHID_MALEVOLENCE);
    }

    @Override
    public float getProcDamage() {
        return 3.0f;
    }

    @Override
    public DamageSource getProcDamageSource(Player player) {
        return player.damageSources().source(DotcDamageTypes.MAGICAL, player, player);
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

    @Override
    public Optional<SoundEvent> getProcSound() {
        return Optional.of(DotcItemSounds.BLOODTHORN_TARGET);
    }
}
