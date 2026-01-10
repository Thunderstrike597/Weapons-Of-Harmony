package net.kenji.woh.entities.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BeamSlashEntity extends Entity {
    public int life = 18;
    private static final EntityDataAccessor<Float> SLASH_ANGLE =
            SynchedEntityData.defineId(BeamSlashEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_ROT =
            SynchedEntityData.defineId(BeamSlashEntity.class, EntityDataSerializers.FLOAT);

    private PlayerPatch<?> caster;
    private StaticAnimation castAnimation;

    private float hitCooldown;
    private float maxCooldown = 8;
    private final Map<UUID, Integer> hitCooldowns = new HashMap<>();
    private static final int HIT_COOLDOWN_TICKS = 18;

    public BeamSlashEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.hitCooldown = maxCooldown;
    }

    public void setCasterAndAnimation(PlayerPatch<?> playerPatch, StaticAnimation animation){
        caster = playerPatch;
        castAnimation = animation;
    }
    public PlayerPatch<?> getCaster(){
        return caster;
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(SLASH_ANGLE, -1f);
        this.entityData.define(Y_ROT, 0f);
    }

    @Override
    public void setYRot(float pYRot) {
        this.entityData.set(Y_ROT, pYRot);
    }

    public void setSlashAngle(float angle) {
        this.entityData.set(SLASH_ANGLE, angle);
    }

    @Override
    public float getYRot() {
        return this.entityData.get(Y_ROT);
    }

    public float getSlashAngle() {
        return this.entityData.get(SLASH_ANGLE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        life = tag.getInt("Life");
        setSlashAngle(tag.getFloat("SlashAngle"));
        setYRot(tag.getFloat("YRot"));

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Life", life);
        tag.putFloat("SlashAngle", getSlashAngle());
    }
    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide)
            return;

        // Decrement cooldowns first
        hitCooldowns.replaceAll((uuid, ticks) -> ticks - 1);
        hitCooldowns.entrySet().removeIf(entry -> entry.getValue() <= 0);

        // Expand hitbox slightly
        AABB hitBox = this.getBoundingBox().inflate(0.5);

        List<LivingEntity> hits = this.level().getEntitiesOfClass(
                LivingEntity.class,
                hitBox,
                LivingEntity::isAlive
        );

        for (LivingEntity target : hits) {
            UUID id = target.getUUID();

            if (target == caster.getOriginal())
                continue;

            if (hitCooldowns.containsKey(id))
                continue;

            // HIT
            onHitLiving(target);

            // Start cooldown for THIS entity only
            hitCooldowns.put(id, HIT_COOLDOWN_TICKS);
            Log.info("ON HIT");
        }

        // Move
        setPos(position().add(getDeltaMovement()));

        if (--life <= 0) discard();
    }

    public void onHitLiving(LivingEntity livingEntity) {
        if (caster != null && castAnimation != null) {
            if (livingEntity != caster.getOriginal()) {
                EpicFightDamageSource efSource =
                        new EpicFightDamageSource(
                                caster.getDamageSource(castAnimation, InteractionHand.MAIN_HAND)
                        )
                                .setHurtItem(caster.getOriginal().getMainHandItem())
                                .setImpact(1.0F)
                                .setArmorNegation(0.25F)
                                .setStunType(StunType.SHORT)
                                .setAnimation(castAnimation)
                                .setDamageModifier(ValueModifier.setter(5));

                caster.attack(efSource, livingEntity, InteractionHand.MAIN_HAND);

                spawnHitParticles((ServerLevel) livingEntity.level(), livingEntity, caster);
                livingEntity.level().playSound(
                        null,
                        livingEntity.blockPosition(),
                        EpicFightSounds.BLADE_HIT.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }
    }
    private static void spawnHitParticles(ServerLevel level, LivingEntity target, PlayerPatch<?> caster) {
        Vec3 pos = target.position().add(0, target.getBbHeight() * 0.5, 0);

        level.sendParticles(
                caster.getWeaponHitParticle(InteractionHand.MAIN_HAND),
                pos.x, pos.y, pos.z,
                1,          // count
                0.3, 0.3, 0.3, // spread
                0.1         // speed
        );
    }
}
