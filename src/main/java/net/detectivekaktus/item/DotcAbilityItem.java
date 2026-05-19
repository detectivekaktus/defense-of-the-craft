package net.detectivekaktus.item;

import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.core.item.ItemStackHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.detectivekaktus.attach.PlayerMana;
import net.detectivekaktus.core.item.HasManaCost;
import net.detectivekaktus.core.item.HasUseCooldown;
import net.detectivekaktus.core.item.SharesUseCooldown;
import net.detectivekaktus.core.player.CombatManager;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

public abstract class DotcAbilityItem extends DotcItem implements HasManaCost, HasUseCooldown {
    public DotcAbilityItem(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    protected InteractionResultHolder<ItemStack> interactWithItem(Player player, LivingEntity target, ItemStack stack) {
        var level = player.level();

        var mana = PlayerMana.get(player);
        var hasInfiniteMaterials = player.hasInfiniteMaterials();
        var notEnoughMana = !hasInfiniteMaterials && getManaCost() > mana.getCurrentMana();
        var invulnerable = target != null && getInvulnerableTag() != null && target.getType().is(getInvulnerableTag());

        if (level.isClientSide) {
            if (notEnoughMana)
                level.playLocalSound(
                        player,
                        DotcGuiSounds.UI_NOT_ENOUGH_MANA,
                        SoundSource.PLAYERS,
                        1.0f, 1.0f
                );

            if (invulnerable)
                level.playLocalSound(
                        player,
                        DotcGuiSounds.UI_IMMUNE,
                        SoundSource.PLAYERS,
                        1.0f, 1.0f
                );

            if (notEnoughMana || invulnerable)
                return InteractionResultHolder.fail(stack);

            return InteractionResultHolder.pass(stack);
        }

        if (notEnoughMana || invulnerable)
            return InteractionResultHolder.fail(stack);

        if (!ItemStackHelper.consumeChargeOrFail(player, stack))
            return InteractionResultHolder.fail(stack);

        if (target instanceof Player interactedPlayer)
            CombatManager.addStickCharge(interactedPlayer);

        if (!hasInfiniteMaterials) {
            var manaConsumed = Math.max(
                    (float) Math.ceil(getManaCost() * (1.0f - mana.getManaCostReduction())),
                    0.0f
            );
            mana.consume(manaConsumed);
        }

        invokeInteractionAbility(player, target, stack);
        playAbilitySound(player);

        var cooldowns = player.getCooldowns();
        cooldowns.addCooldown(this, getCooldownInTicks());
        if (this instanceof SharesUseCooldown itemWithSharedCooldown) {
            for (var item : itemWithSharedCooldown.getSharesCooldownWith()) {
                if (cooldowns.isOnCooldown(item))
                    continue;

                cooldowns.addCooldown(item, getCooldownInTicks());
            }
        }

        return InteractionResultHolder.success(stack);
    }

    private void playAbilitySound(Player player) {
        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                getAbilitySound(),
                SoundSource.PLAYERS
        );
    }

    protected abstract TagKey<EntityType<?>> getInvulnerableTag();

    protected abstract void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack);

    protected abstract SoundEvent getAbilitySound();
}
