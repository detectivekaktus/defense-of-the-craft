package net.detectivekaktus.item.ingredient;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.core.player.ShadowWalkingSource;
import net.detectivekaktus.item.DotcAbilityItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class ShadowAmulet extends DotcAbilityItem {
    private final int INVISIBILITY_DURATION = 5 * 20;

    public ShadowAmulet(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        return interactWithItem(player, null, stack);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        var flags = PlayerFlags.get(player);
        flags.setShadowWalking(true);
        flags.setShadowWalkingSource(ShadowWalkingSource.SHADOW_AMULET);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, INVISIBILITY_DURATION));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, INVISIBILITY_DURATION, 1));
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.SHADOW_AMULET;
    }

    @Override
    public float getManaCost() {
        return 15.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 15 * 20;
    }
}
