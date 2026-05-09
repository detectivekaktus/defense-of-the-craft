package net.detectivekaktus.core.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.attach.PlayerMana;
import net.detectivekaktus.attach.PlayerStats;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.component.records.ProcableComponent;
import net.detectivekaktus.core.item.*;
import net.detectivekaktus.core.rng.PseudoRandom;
import net.detectivekaktus.core.util.CombatManagerHolder;
import net.detectivekaktus.damage.DotcDamageTypes;
import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.item.tool.*;
import net.detectivekaktus.sound.gui.DotcGuiSounds;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class CombatManager {
    private final Player player;
    private boolean hitThroughEvasion = false;
    private boolean evaded = false;
    private boolean broke = false;

    public CombatManager(Player player) {
        this.player = player;
    }

    public float crit(float damage) {
        var stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof Critable item) || !stack.has(DotcComponents.PROCABLE_COMPONENT))
            return damage;

        var component = stack.get(DotcComponents.PROCABLE_COMPONENT);
        var chance = PseudoRandom.getProcChance(component.baseChance(), component.scale());
        if (player.getRandom().nextFloat() > chance) {
            stack.set(
                    DotcComponents.PROCABLE_COMPONENT,
                    ProcableComponent.increaseScale(component)
            );
            return damage;
        }

        stack.set(
                DotcComponents.PROCABLE_COMPONENT,
                ProcableComponent.resetScale(component)
        );

        var sound = item.getProcSound();
        sound.ifPresent(soundEvent -> player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                soundEvent,
                player.getSoundSource(),
                1.0f, 1.0f
        ));

        return damage * item.getCritPercent();
    }

    public ShadowWalkingSource revealInvisibility() {
        var flags = PlayerFlags.get(player);
        if (!flags.isShadowWalking())
            return ShadowWalkingSource.NONE;

        player.removeEffect(MobEffects.INVISIBILITY);
        player.removeEffect(MobEffects.MOVEMENT_SPEED);

        flags.setShadowWalking(false);
        return flags.setShadowWalkingSource(ShadowWalkingSource.NONE);
    }

    public float addShadowWalkingDamage() {
        setBroke(false);

        var flags = PlayerFlags.get(player);
        if (!flags.isShadowWalking())
            return 0.0f;

        var oldSource = revealInvisibility();
        if (oldSource == ShadowWalkingSource.SILVER_EDGE) {
            player.level().playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    DotcItemSounds.SILVER_EDGE,
                    SoundSource.PLAYERS
            );
            setBroke(true);
        }
        return oldSource != ShadowWalkingSource.NONE
                && oldSource != ShadowWalkingSource.SHADOW_AMULET ? 4.0f : 0.0f;
    }

    public float manaBurn(Player attacker, Player victim) {
        if (!attacker.getWeaponItem().is(DotcTools.DIFFUSAL_BLADE))
            return 0.0f;

        var mana = PlayerMana.get(victim);
        var manaBurn = Math.min(CombatRules.DIFFUSAL_MANA_BURN, mana.getCurrentMana());
        mana.consume(manaBurn);

        return manaBurn * CombatRules.DIFFUSAL_DAMAGE_PER_MANA;
    }

    public void calculateProcs() {
        setHitThroughEvasion(false);

        var stack = player.getMainHandItem();
        var item = stack.getItem();
        if (broke && stack.is(DotcTools.SILVER_EDGE)) {
            setHitThroughEvasion(true);
            return;
        }

        if (!stack.has(DotcComponents.PROCABLE_COMPONENT) || !(stack.getItem() instanceof Procable))
            return;

        if (((Procable) item).getProcCooldownInTicks() != 0 && player.getCooldowns().isOnCooldown(item))
            return;

        var component = stack.get(DotcComponents.PROCABLE_COMPONENT);
        var chance = PseudoRandom.getProcChance(component.baseChance(), component.scale());
        if (player.getRandom().nextFloat() > chance) {
            stack.set(
                    DotcComponents.PROCABLE_COMPONENT,
                    ProcableComponent.increaseScale(component)
            );
            return;
        }

        stack.set(
                DotcComponents.PROCABLE_COMPONENT,
                ProcableComponent.resetScale(component)
        );

        setHitThroughEvasion(true);
    }

    public boolean proc(Entity entity, boolean hurt) {
        var stack = player.getMainHandItem();
        var item = stack.getItem();

        if (stack.has(DotcComponents.PROCABLE_COMPONENT) && (item instanceof Procable itemWithBonuses)) {
            if (!hitThroughEvasion())
                return hurt;

            var damageSource = itemWithBonuses.getProcDamageSource(player);
            var damage = itemWithBonuses.getProcDamage();
            var sound = itemWithBonuses.getProcSound();
            var effect = itemWithBonuses.getProcEffect();

            sound.ifPresent(soundEvent -> player.level().playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    soundEvent,
                    player.getSoundSource(),
                    1.0f, 1.0f
            ));
            if (effect.isPresent() && entity instanceof LivingEntity livingEntity)
                livingEntity.addEffect(new MobEffectInstance(effect.get(), itemWithBonuses.getProcEffectDuration()));

            applyCooldown(item);
            entity.hurt(damageSource, damage);
        }
        else if (stack.is(DotcTools.ECHO_SABRE)) {
            if (player.getCooldowns().isOnCooldown(item))
                return hurt;

            var damageSource = player.level().damageSources().source(
                    DotcDamageTypes.PHYSICAL,
                    player,
                    player
            );

            var damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            var scale = player.getAttackStrengthScale(0.5f);
            damage *= 0.2f + scale * scale * 0.8f;
            damage += item.getAttackDamageBonus(entity, damage, damageSource);

            // like in dota the echo sabre attack doesn't crit if the first one did,
            // so there's no f *= 1.5 in case of a crit

            player.getCooldowns().addCooldown(item, CombatRules.ECHO_SABRE_COOLDOWN);
            entity.hurt(damageSource, damage);
        }

        return hurt;
    }

    private void applyCooldown(Item item) {
        var cooldown = ((Procable) item).getProcCooldownInTicks();
        if (cooldown == 0)
            return;

        var cooldowns = player.getCooldowns();
        if (!cooldowns.isOnCooldown(item))
            cooldowns.addCooldown(item, cooldown);

        if (!(item instanceof SharesProcCooldown sharesCooldown))
            return;

        for (var sharedCooldownItem : sharesCooldown.getSharesProcCooldownWith()) {
            if (!cooldowns.isOnCooldown(sharedCooldownItem))
                cooldowns.addCooldown(sharedCooldownItem, cooldown);
        }
    }

    private void playEvasionSound() {
        var level = player.level();
        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                DotcGuiSounds.UI_EVADED,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
    }

    public boolean evade(DamageSource damageSource) {
        setEvaded(false);

        if (damageSource.is(DotcDamageTypes.MAGICAL))
            return false;
        else if (player.hasEffect(DotcEffects.BREAK))
            return false;

        var attacker = damageSource.getEntity();
        if (attacker == null)
            return evaded;
        var manager = ((CombatManagerHolder) attacker).getCombatManager();

        var stats = PlayerStats.get(player);
        var evasion = stats.getEvasion();
        var evasionChance = PseudoRandom.getProcChance(evasion, stats.getEvasionScale());
        if (player.getRandom().nextFloat() > evasionChance) {
            if (manager.hasBroken())
                player.addEffect(new MobEffectInstance(DotcEffects.BREAK, 5 * 20));

            stats.addEvasionScale(1);
            return evaded;
        }

        stats.setEvasionScale(0);

        if (!(attacker instanceof ServerPlayer)) {
            setEvaded(true);
            playEvasionSound();
            return evaded;
        }

        var hitThrough = manager.hitThroughEvasion();
        if (hitThrough) {
            if (manager.hasBroken())
                player.addEffect(new MobEffectInstance(DotcEffects.BREAK, 5 * 20));
            return false;
        }

        setEvaded(true);
        playEvasionSound();
        return evaded;
    }

    public float reduceDamage(float damage, DamageSource damageSource) {
        if (!damageSource.is(DotcDamageTypes.MAGICAL))
            return damage;

        var stats = PlayerStats.get(player);
        return damage * (1.0f - stats.getMagicResistance());
    }

    public static void addStickCharge(Player player) {
        var slots = InventoryManager.getModInterestedSlots(player);
        for (var item : slots) {
            boolean isTarget = (item.is(DotcTools.MAGIC_STICK) || item.is(DotcTools.MAGIC_WAND))
                    && item.has(DotcComponents.CHARGEABLE_COMPONENT);
            if (isTarget) {
                var component = item.get(DotcComponents.CHARGEABLE_COMPONENT);
                item.set(
                        DotcComponents.CHARGEABLE_COMPONENT,
                        ChargeableComponent.addCharge(component)
                );
                // Charge only one stick item to prevent abusing sticks by having
                // 9 of them in inventory
                return;
            }
        }
    }

    public boolean hitThroughEvasion() {
        return hitThroughEvasion;
    }

    public void setHitThroughEvasion(boolean hitThroughEvasion) {
        this.hitThroughEvasion = hitThroughEvasion;
    }

    public boolean hasEvaded() {
        return evaded;
    }

    public void setEvaded(boolean evaded) {
        this.evaded = evaded;
    }

    public boolean hasBroken() {
        return broke;
    }

    public void setBroke(boolean broke) {
        this.broke = broke;
    }
}
