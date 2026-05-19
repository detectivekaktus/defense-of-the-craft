package net.detectivekaktus.core.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.component.DotcComponents;
import net.detectivekaktus.component.records.ChargeableComponent;
import net.detectivekaktus.core.player.ShadowWalkingSource;
import net.detectivekaktus.effect.DotcEffects;
import net.detectivekaktus.sound.gui.DotcGuiSounds;

public class ItemStackHelper {
    public static boolean cancelInteractionIfDisabled(Player player) {
        if (cancelInteractionIfStunned(player))
            return true;
        return cancelInteractionIfSilenced(player);
    }

    private static boolean cancelInteractionIfStunned(Player player) {
        if (!player.hasEffect(DotcEffects.STUN))
            return false;

        if (player.level().isClientSide)
            player.playSound(DotcGuiSounds.UI_GENERAL_DENY);

        return true;
    }

    private static boolean cancelInteractionIfSilenced(Player player) {
        if (!player.hasEffect(DotcEffects.SILENCE) && !player.hasEffect(DotcEffects.SOUL_REND))
            return false;

        if (player.level().isClientSide)
            player.playSound(DotcGuiSounds.UI_SILENCE);

        return true;
    }

    public static ShadowWalkingSource revealInvisibility(Player player) {
        var flags = PlayerFlags.get(player);
        if (!flags.isShadowWalking())
            return ShadowWalkingSource.NONE;

        player.removeEffect(MobEffects.INVISIBILITY);
        player.removeEffect(MobEffects.MOVEMENT_SPEED);

        flags.setShadowWalking(false);
        return flags.setShadowWalkingSource(ShadowWalkingSource.NONE);
    }

    public static boolean consumeChargeOrFail(Player player, ItemStack stack) {
        if (!player.hasInfiniteMaterials() && stack.has(DotcComponents.CHARGEABLE_COMPONENT)) {
            var component = stack.get(DotcComponents.CHARGEABLE_COMPONENT);
            if (component.charges() == 0)
                return false;

            stack.set(
                    DotcComponents.CHARGEABLE_COMPONENT,
                    ChargeableComponent.consumeCharge(component)
            );
        }
        return true;
    }
}
