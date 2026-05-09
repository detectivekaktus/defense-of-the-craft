package net.detectivekaktus.item.tool;

import net.detectivekaktus.core.item.SharesUseCooldown;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.detectivekaktus.attach.PlayerFlags;
import net.detectivekaktus.core.player.ShadowWalkingSource;
import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShadowBlade extends DotcAbilitySwordItem implements SharesUseCooldown {
    private final int INVISIBILITY_DURATION = 15 * 20;

    public ShadowBlade(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
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
        flags.setShadowWalkingSource(ShadowWalkingSource.SHADOW_BLADE);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, INVISIBILITY_DURATION));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, INVISIBILITY_DURATION));
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return null;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.SHADOW_BLADE;
    }

    @Override
    public float getManaCost() {
        return 35.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 25 * 20;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.SILVER_EDGE);
    }
}
