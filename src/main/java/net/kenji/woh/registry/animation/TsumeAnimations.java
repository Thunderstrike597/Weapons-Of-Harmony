package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.WohAnimationBuilder;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.gameasset.WohColliderPreset;
import net.minecraftforge.registries.RegistryObject;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import java.util.function.Supplier;


public class TsumeAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_SKILL_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_RUN;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_DASH;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_AIRSLASH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_SKILL_ACTIVATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_SKILL_DEACTIVATE;


    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_SKILL_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_SKILL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_SKILL_AUTO_3;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_NEW_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_NEW_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_NEW_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_NEW_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TSUME_NEW_AIRSLASH;


    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_GUARD;

    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        TSUME_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_hold", true, 0.1f, -1, -1,null);
        TSUME_WALK = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_walk", true, 0.1f, -1, -1,null);

        TSUME_SKILL_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_skill_hold", true, 0.1f, -1, -1,null);

        TSUME_RUN = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_run", true, 0.1f, -1, -1,null);
        TSUME_GUARD = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_guard", true, 0.1f, -1, -1,null);

        TSUME_SKILL_ACTIVATE = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_skill_activate", false, 0.1f, -1, -1,null);
        TSUME_SKILL_DEACTIVATE = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_skill_deactivate", false, 0.1f, -1, -1,null);


        TSUME_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tsume/auto_1")
                        .phases(0.0F, 0.32F, 0.40F, 0.62F, 2.20F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .speed(0.075F)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR)
                        .movementMultiplier(1F)
                        .build());

        TSUME_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tsume/auto_2")
                        .phases(0.0F, 0.27F, 0.33F, 0.50F, 2.05F)
                        .speed(0.075F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL)
                        .movementMultiplier(1F)
                        .build());
        TSUME_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tsume/auto_3")
                        .phases(0.0F, 0.34F, 0.40F, 0.88F, 0.42F)
                        .phases(0.44F, 0.48F, 0.52F, 0.88F, 2.05F)
                        .speed(0.04F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(AttackAnimation.JointColliderPair.of(biped.get().toolR, ColliderPreset.DAGGER), AttackAnimation.JointColliderPair.of(biped.get().toolL, ColliderPreset.DAGGER))
                        .movementMultiplier(1F)
                        .build());

        TSUME_AUTO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tsume/auto_4")
                        .phases(0.0F, 0.36F, 0.45F, 0.76F, 2.25F)
                        .speed(0.035F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .build());

        TSUME_DASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tsume/tsume_dash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.DASH_ATTACK)
                        .phases(0.0F, 0.32F, 0.38F, 0.88F, 1.32F)
                        .phases(0.30F, 0.32F, 0.38F, 0.88F, 1.32F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .build());

        TSUME_AIRSLASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tsume/tsume_airslash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
                        .phases(0.0F, 0.13F, 0.20F, 1.18F, 1.30F)
                        .phases(0.38F, 0.42F, 0.50F, 1.18F, 1.30F)
                        .phases(0.70F, 0.70F, 0.80F, 1.18F, 1.30F)
                        .stun(StunType.LONG)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 2)
                        .airTime(0.05F, 1.22F)
                        .build());

        TSUME_SKILL_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tsume/tsume_skill_auto_1")
                        .phases(0.0F, 0.20F, 0.30F, 0.78F, 1.5F)
                        .phases(0.0F, 0.52F, 0.58F, 0.78F, 1.5F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .build());

        TSUME_SKILL_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tsume/tsume_skill_auto_2")
                        .phases(0.0F, 0.25F, 0.36F, 0.65F, 1.5F)
                        .phases(0.0F, 0.25F, 0.56F, 0.65F, 1.5F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .build());

        TSUME_SKILL_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tsume/tsume_skill_auto_3")
                        .phases(0.0F, 0.1F, 0.16F, 0.5F, 1.5F)
                        .phases(0.0F, 0.1F, 0.16F, 0.5F, 1.5F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolR, 0)
                        .collider(WohColliderPreset.TSUME_CLAWS, biped.get().toolL, 1)
                        .build());

        TSUME_NEW_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tsume/tsume_auto_1")
                        .phases(0.0F, 0.32F, 0.41F, 0.55F, 1.38F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolR)
                        .build());

        TSUME_NEW_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tsume/tsume_auto_2")
                        .phases(0.0F, 0.35F, 0.46F, 0.55F, 1.52F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL)
                        .build());

        TSUME_NEW_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tsume/tsume_auto_3")
                        .phases(0.0F, 0.35F, 0.40F, 0.90F, 1.70F)
                        .phases(0.45F, 0.62F, 0.73F, 0.90F, 1.70F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .build());

        TSUME_NEW_AUTO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tsume/tsume_auto_4")
                        .phases(0.0F, 0.58F, 0.65F, 1.20F, 2.25F)
                        .phases(0.45F, 0.58F, 0.65F, 1.20F, 2.25F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .build());

        TSUME_NEW_AIRSLASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tsume/tsume_airslash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
                        .phases(0.0F, 0.16F, 0.26F, 1.18F, 1.30F)
                        .phases(0.38F, 0.42F, 0.52F, 1.18F, 1.30F)
                        .phases(0.70F, 0.80F, 0.98F, 1.18F, 1.30F)
                        .stun(StunType.LONG)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .collider(ColliderPreset.SWORD, biped.get().toolR, 2)
                        .airTime(0.05F, 1.22F)
                        .build());
    }
}
