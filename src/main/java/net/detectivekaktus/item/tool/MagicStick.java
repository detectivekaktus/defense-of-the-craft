package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.detectivekaktus.attach.PlayerMana;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.core.item.SharesUseCooldown;
import net.detectivekaktus.item.DotcAbilityItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

import java.util.List;

public class MagicStick extends DotcAbilityItem implements SharesUseCooldown {
    private final float HEALTH_PER_STICK_CHARGE = 0.4f;
    private final float MANA_PER_STICK_CHARGE = 2.0f;

    private final int CHARGE_INTERVAL = 30 * 20;

    public MagicStick(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        return interactWithItem(player, null, stack);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        if (!stack.has(DotcComponents.CHARGEABLE_COMPONENT))
            return;

        var component = stack.get(DotcComponents.CHARGEABLE_COMPONENT);
        var charges = component.charges() + 1; // + 1 because DotcAbilityItem consumes one

        var hpRegen = HEALTH_PER_STICK_CHARGE * charges;
        var manaRegen = MANA_PER_STICK_CHARGE * charges;

        var mana = PlayerMana.get(player);
        player.heal(hpRegen);
        mana.increment(manaRegen);

        stack.set(
                DotcComponents.CHARGEABLE_COMPONENT,
                ChargeableComponent.resetCharges(component)
        );
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
        return DotcItemSounds.MAGIC_STICK;
    }

    @Override
    public int getCooldownInTicks() {
        return 15 * 20;
    }

    @Override
    public float getManaCost() {
        return 0;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.MAGIC_WAND, DotcTools.MAGIC_STICK);
    }
}
