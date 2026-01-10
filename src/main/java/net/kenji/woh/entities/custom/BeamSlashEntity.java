package net.kenji.woh.entities.custom;

import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.types.StaticAnimation;


public class BeamSlashEntity extends Entity {
    public int life = 26;
    private static final EntityDataAccessor<Float> SLASH_ANGLE =
            SynchedEntityData.defineId(BeamSlashEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y_ROT =
            SynchedEntityData.defineId(BeamSlashEntity.class, EntityDataSerializers.FLOAT);

    public BeamSlashEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
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

        setPos(position().add(getDeltaMovement()));

        if (--life <= 0) discard();
    }
}
