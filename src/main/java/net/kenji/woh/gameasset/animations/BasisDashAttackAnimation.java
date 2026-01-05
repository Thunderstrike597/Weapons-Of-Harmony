package net.kenji.woh.gameasset.animations;

import net.kenji.woh.api.WOHAnimationUtils;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasisDashAttackAnimation extends DashAttackAnimation {
    public static final Map<UUID, Boolean> isAttacking = new HashMap<>();

    private WOHAnimationUtils.AttackAnimationType attackAnimationType;


    public BasisDashAttackAnimation(
            WOHAnimationUtils.AttackAnimationType attackType,
            float convertTime,
            AnimationManager.AnimationAccessor<? extends DashAttackAnimation> accessor,  // ADD THIS!
            AssetAccessor<? extends HumanoidArmature> armature,
            AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            boolean ignoreFallDamage,
            Phase... phases
    ) {
        super(convertTime, accessor, armature, phases);  // Pass accessor to parent (DashAttackAnimation)
        this.attackAnimationType = attackType;
    }


    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        super.attackTick(entitypatch, animation);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
       if(entitypatch instanceof PlayerPatch<?> playerPatch) {
          UUID playerID = playerPatch.getOriginal().getUUID();
           isAttacking.put(playerID, false);
       }
        super.end(entitypatch, nextAnimation, isEnd);
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if(attackAnimationType == WOHAnimationUtils.AttackAnimationType.DASH_ATTACK_JUMP)
                playerPatch.getOriginal().resetFallDistance();
        }
        super.tick(entitypatch);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            isAttacking.put(playerID, true);
        }
        super.begin(entitypatch);
    }
}

