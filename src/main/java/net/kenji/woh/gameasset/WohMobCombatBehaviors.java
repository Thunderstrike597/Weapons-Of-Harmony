//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.kenji.woh.gameasset;

import com.mojang.datafixers.util.Pair;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;

public class WohMobCombatBehaviors {
    public static final CombatBehaviors.Builder<HumanoidMobPatch<?>> RONIN_SKELETON;

    static {

        RONIN_SKELETON = CombatBehaviors.builder().newBehaviorSeries(
                createBehaviourSeries(30, DynamicBehaviour.of(ShotogatanaAnimations.SHOTOGATANA_AUTO_3).distanceMinMax(1, 3).build())
        ).newBehaviorSeries(
                createBehaviourSeries(22, DynamicBehaviour.of(ShotogatanaAnimations.SHOTOGATANA_AUTO_3).distanceMinMax(1, 3).build(), DynamicBehaviour.of(ShotogatanaAnimations.SHOTOGATANA_AUTO_4).distanceMinMax(1, 3).build())
        ).newBehaviorSeries(
                createBehaviourSeries(50, DynamicBehaviour.of(Animations.BIPED_STEP_BACKWARD).distanceMinMax(0, 1.5F).build())
        ).newBehaviorSeries(
                createBehaviourSeries(40, DynamicBehaviour.of(Animations.BIPED_STEP_LEFT).distanceMinMax(0, 1.8F).build())
        ).newBehaviorSeries(
                createBehaviourSeries(40, DynamicBehaviour.of(Animations.BIPED_STEP_RIGHT).distanceMinMax(0, 1.8F).build())
        );
    }
     private static BehaviorSeries.Builder createBehaviourSeries(float weight, DynamicBehaviour... dynamicBehaviours){
        BehaviorSeries.Builder behaviorSeries = BehaviorSeries.builder().weight(weight).canBeInterrupted(false);
        for(DynamicBehaviour dynamicBehaviour : dynamicBehaviours){
            Behavior.Builder behaviour = Behavior.builder().withinEyeHeight().animationBehavior(dynamicBehaviour.animationAccessor).withinDistance(dynamicBehaviour.distance.getFirst(), dynamicBehaviour.distance.getSecond());
            if(dynamicBehaviour.randomChance != -1){
                behaviour.randomChance(dynamicBehaviour.randomChance);
            }
            behaviorSeries.nextBehavior(behaviour);
        }
        return behaviorSeries;
     }


     public static class DynamicBehaviour{
         AnimationManager.AnimationAccessor<? extends StaticAnimation> animationAccessor;
         public final Pair<Float, Float> distance;
         public final float randomChance;

        public DynamicBehaviour(Builder builder){
            this.distance = builder.distance;
            this.randomChance = builder.randomChance;
            this.animationAccessor = builder.animation;
        }

        public static Builder of(AnimationManager.AnimationAccessor<? extends StaticAnimation> animations) {
            return new Builder(animations);
        }
        public static class Builder{
            public AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = null;
            public Pair<Float, Float> distance = new Pair<>(1F, 2.75F);
            public float randomChance = -1;



            private Builder(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation) {
                this.animation = animation;
            }

            public Builder distanceMinMax(float min, float max){
                distance = new Pair<>(min, max);
                return this;
            }
            public Builder randomChance(float chance){
                randomChance = chance;
                return this;
            }

            public DynamicBehaviour build(){
                return new DynamicBehaviour(this);
            }
         }
     }

}
