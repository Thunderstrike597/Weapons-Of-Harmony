package net.kenji.woh.gameasset.skills;

import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.awt.*;

public class ArbitersSlash extends WeaponInnateSkill {

    public ArbitersSlash(Builder<? extends Skill> builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 20;
        this.maxStackSize = 1;
    }

    @Override
    public void updateContainer(SkillContainer container) {
        if (container.isActivated()){
            PlayerPatch<?> playerPatch = container.getExecuter();
            if(playerPatch.getAnimator().getPlayerFor(null).getAnimation() instanceof BasisAttackAnimation attackAnim){
               if(attackAnim.isAttackBegin()){
                   onBeamSlash(playerPatch, attackAnim);
               }
            }
        }
        super.updateContainer(container);
    }

    private void onBeamSlash(PlayerPatch<?> playerPatch, BasisAttackAnimation basisAttackAnimation){
        ResourceKey<Level> dim = playerPatch.getOriginal().level().dimension();
        ServerLevel serverLevel = playerPatch.getOriginal().getServer().getLevel(dim);
        BlockPos blockPos = playerPatch.getOriginal().blockPosition();
        BeamSlashEntity spawnedEntity = ModEntities.BEAM_SLASH.get().spawn(serverLevel, blockPos, MobSpawnType.TRIGGERED);
        Vec3 lookDir = playerPatch.getOriginal().getLookAngle();
        if(spawnedEntity != null) {
            spawnedEntity.addDeltaMovement(lookDir);
            spawnedEntity.setYRot(playerPatch.getOriginal().getYRot());
            spawnedEntity.setSlashAngle(basisAttackAnimation.getSlashAngle());
        }

            SoundEvent cast1 = SoundEvents.TRIDENT_RIPTIDE_1;
            SoundEvent cast2 = SoundEvents.TRIDENT_RIPTIDE_2;
            SoundEvent cast3 = SoundEvents.TRIDENT_RIPTIDE_3;
            int castIndex = (int) Mth.randomBetween(RandomSource.create(), 0, 2);
            SoundEvent castSound = castIndex == 0 ? cast1 : castIndex == 1 ? cast2 : cast3;

            serverLevel.playSound(
                    null,
                    blockPos,
                    castSound,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
    }
}
