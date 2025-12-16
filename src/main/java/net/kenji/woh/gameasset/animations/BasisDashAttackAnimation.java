package net.kenji.woh.gameasset.animations;

import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasisDashAttackAnimation extends DashAttackAnimation {
    public static final Map<UUID, Boolean> isAttacking = new HashMap<>();

    public BasisDashAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }


    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        super.attackTick(entitypatch, animation);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
       if(entitypatch instanceof PlayerPatch<?> playerPatch) {
          UUID playerID = playerPatch.getOriginal().getUUID();
           isAttacking.put(playerID, false);
       }
        super.end(entitypatch, nextAnimation, isEnd);
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

