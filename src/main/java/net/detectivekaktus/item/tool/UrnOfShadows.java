package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.item.DotcAbilityItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class UrnOfShadows extends DotcAbilityItem {
    private final int CHARGE_INTERVAL = (2 * 60) * 20;

    public UrnOfShadows(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        return interactWithItem(player, null, stack);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        return interactWithItem(player, livingEntity, itemStack).getResult();
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        if (target == null) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 8 * 20, 1));
            return;
        }

        target.addEffect(new MobEffectInstance(DotcEffects.SOUL_RELEASE, 8 * 20));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if (level.isClientSide)
            return;

        if (!itemStack.has(DotcComponents.CHARGEABLE_COMPONENT))
            return;

        var component = itemStack.get(DotcComponents.CHARGEABLE_COMPONENT);
        var shouldAddCharge = component.charges() < component.maxCharges()
                && level.getGameTime() - component.lastTickSync() >= CHARGE_INTERVAL;
        if (shouldAddCharge) {
            itemStack.set(
                    DotcComponents.CHARGEABLE_COMPONENT,
                    ChargeableComponent.addCharge(component, level)
            );
        }
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.URN_OF_SHADOWS;
    }

    @Override
    public float getManaCost() {
        return 0.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 30 * 20;
    }
}
