package net.detectivekaktus.item.tool;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import net.detectivekaktus.item.DotcAbilitySwordItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;
import net.detectivekaktus.tag.DotcEntityTypeTags;

public class ForceStaff extends DotcAbilitySwordItem {
    public ForceStaff(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
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
            var viewVector = player.getLookAngle().scale(4);
            player.addDeltaMovement(new Vec3(viewVector.x, 0, viewVector.z));
            player.hurtMarked = true;
            return;
        }

        var viewVector = target.getLookAngle().scale(4);
        target.addDeltaMovement(new Vec3(viewVector.x, 0, viewVector.z));
        target.hurtMarked = true;
    }

    @Override
    protected TagKey<EntityType<?>> getInvulnerableTag() {
        return DotcEntityTypeTags.FORCE_MOVEMENT_INVULNERABLE;
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.FORCE_STAFF;
    }

    @Override
    public float getManaCost() {
        return 25.0f;
    }

    @Override
    public int getCooldownInTicks() {
        return 15 * 20;
    }
}
