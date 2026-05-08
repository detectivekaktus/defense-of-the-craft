package net.detectivekaktus.item.consumable;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;

import net.detectivekaktus.core.animation.DustOfAppearanceAnimation;
import net.detectivekaktus.core.animation.ParticleAnimationManager;
import net.detectivekaktus.core.item.HasUseCooldown;
import net.detectivekaktus.item.DotcItem;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

public class DustOfAppearance extends DotcItem implements HasUseCooldown {
    private final int DUST_RANGE = 12;
    private final int DUST_SPREAD_SPEED = 5;

    private final int SLOW_DURATION = 10 * 20;

    public DustOfAppearance(Properties properties, TooltipBuilder tooltipBuilder) {
        super(properties, tooltipBuilder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        var stack = player.getItemInHand(interactionHand);
        if (level.isClientSide)
            return InteractionResultHolder.pass(stack);

        var aabb = new AABB(player.getOnPos()).inflate(DUST_RANGE);
        var entities = level.getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity != player && entity.hasEffect(MobEffects.INVISIBILITY)
        );
        entities.forEach(entity -> {
            entity.removeEffect(MobEffects.INVISIBILITY);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOW_DURATION, 1));
        });

        playAnimation((ServerLevel) level, player.getX(), player.getY(), player.getZ());
        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                DotcItemSounds.DUST_OF_APPEARANCE,
                SoundSource.PLAYERS
        );

        player.getCooldowns().addCooldown(this, getCooldownInTicks());

        return InteractionResultHolder.success(stack);
    }

    private void playAnimation(ServerLevel level, double x, double y, double z) {
        y += 0.5;

        var particle = new DustParticleOptions(new Vector3f(0.705f, 0.733f, 0.909f), 1.0f);
        for (var distance = 0; distance != DUST_RANGE; distance++) {
            ParticleAnimationManager.INSTANCE.addAnimation(new DustOfAppearanceAnimation(
                    level,
                    x, y, z,
                    distance,
                    true,
                    distance * DUST_SPREAD_SPEED,
                    particle
            ));
            ParticleAnimationManager.INSTANCE.addAnimation(new DustOfAppearanceAnimation(
                    level,
                    x, y, z,
                    -distance,
                    true,
                    distance * DUST_SPREAD_SPEED,
                    particle
            ));ParticleAnimationManager.INSTANCE.addAnimation(new DustOfAppearanceAnimation(
                    level,
                    x, y, z,
                    distance,
                    false,
                    distance * DUST_SPREAD_SPEED,
                    particle
            ));
            ParticleAnimationManager.INSTANCE.addAnimation(new DustOfAppearanceAnimation(
                    level,
                    x, y, z,
                    -distance,
                    false,
                    distance * DUST_SPREAD_SPEED,
                    particle
            ));
        }
    }

    @Override
    public int getCooldownInTicks() {
        return 20 * 20;
    }
}
