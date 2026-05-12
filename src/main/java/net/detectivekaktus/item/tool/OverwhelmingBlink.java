package net.detectivekaktus.item.tool;

import net.detectivekaktus.damage.DotcDamageTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;

import net.detectivekaktus.attach.PlayerStats;
import net.detectivekaktus.item.TooltipBuilder;
import net.detectivekaktus.sound.item.DotcItemSounds;

import java.util.List;

public class OverwhelmingBlink extends BlinkDagger {
    private final float STRENGTH_TO_DAMAGE_PERCENT = 0.05f;

    public OverwhelmingBlink(Tier tier, Properties properties, TooltipBuilder tooltipBuilder) {
        super(tier, properties, tooltipBuilder);
    }

    @Override
    protected void invokeInteractionAbility(Player player, LivingEntity target, ItemStack stack) {
        super.invokeInteractionAbility(player, target, stack);
        var stats = PlayerStats.get(player);
        var aabb = new AABB(player.getOnPos()).inflate(4);
        var entities = player.level().getEntitiesOfClass(
                LivingEntity.class,
                aabb,
                entity -> entity instanceof Enemy
        );

        var damage = stats.getStrength() *  STRENGTH_TO_DAMAGE_PERCENT;
        entities.forEach(entity -> entity.hurt(player.damageSources().source(DotcDamageTypes.PHYSICAL), damage));
    }

    @Override
    public ParticleOptions getAnimationParticle() {
        return new DustParticleOptions(new Vector3f(0.749f, 0.145f, 0.113f), 0.5f);
    }

    @Override
    protected SoundEvent getAbilitySound() {
        return DotcItemSounds.OVERWHELMING_BLINK_TARGET;
    }

    @Override
    public List<Item> getSharesCooldownWith() {
        return List.of(DotcTools.BLINK_DAGGER, DotcTools.SWIFT_BLINK, DotcTools.ARCANE_BLINK);
    }

    @Override
    public float getManaCost() {
        return 50.0f;
    }
}
