package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.WohAnimationBuilder;
import net.kenji.woh.gameasset.AttackHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import java.util.function.Supplier;


public class TessenAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_DUAL_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_NEW_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_NEW_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_RUN;

    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_SKILL_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_SKILL_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_SKILL_ACTIVATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_SKILL_DEACTIVATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TESSEN_DUAL_GUARD;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_AIRSLASH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_AIRSLASH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_DUAL_DASH;


    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_NEW_SKILL_DUAL_AUTO_4;


    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_SKILL_DASH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TESSEN_SKILL_AIRSLASH;



    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        TESSEN_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_dual_hold", true, 0.1f, -1, -1,null);
        TESSEN_DUAL_RUN =  WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_dual_run", true, 0.1f, -1, -1,null);
        TESSEN_NEW_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_hold", true, 0.1f, -1, -1,null);
        TESSEN_NEW_WALK =  WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_walk", true, 0.1f, -1, -1,null);
        TESSEN_RUN =  WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_run", true, 0.1f, -1, -1,null);

        TESSEN_SKILL_HOLD =  WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_skill_hold", true, 0.1f, -1, -1,null);
        TESSEN_SKILL_WALK =  WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tessen/tessen_skill_walk", true, 0.1f, -1, -1,null);
        TESSEN_GUARD =  WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tessen/tessen_guard", true, 0.1f, -1, -1,null);
        TESSEN_DUAL_GUARD = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tessen/tessen_dual_guard", true, 0.1f, -1, -1,null);

        TESSEN_SKILL_ACTIVATE =  WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tessen/tessen_skill_activate", false, 0.1f, -1, -1,null);
        TESSEN_SKILL_DEACTIVATE =  WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tessen/tessen_skill_deactivate", false, 0.1f, -1, -1,null);

        TESSEN_NEW_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_auto_1")
                        .phases(0.0F, 0.35F, 0.43F, 0.82F, 1.55F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR)
                        .build());

        TESSEN_NEW_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_auto_2")
                        .phases(0.0F, 0.13F, 0.18F, 0.50F, 1.23F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR)
                        .build());

        TESSEN_NEW_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_auto_3")
                        .phases(0.0F, 0.30F, 0.37F, 1.12F, 1.85F)
                        .phases(0.52F, 0.58F, 0.73F, 1.12F, 1.85F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 1)
                        .build());

        TESSEN_NEW_AIRSLASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_airslash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
                        .phases(0.0F, 0.25F, 0.30F, 0.8F, 1.05F)
                        .phases(0.42F, 0.46F, 0.50F, 0.8F, 1.05F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 1)
                        .airTime(0.05F, 1F)
                        .build());

        TESSEN_NEW_DUAL_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_dual_auto_1")
                        .speed(0.2F)
                        .phases(0.0F, 0.36F, 0.43F, 0.88F, 1.50F)
                        .phases(0.0F, 0.38F, 0.47F, 0.88F, 1.50F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 1)
                        .build());

        TESSEN_NEW_DUAL_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_dual_auto_2")
                        .speed(0.2F)
                        .phases(0.0F, 0.36F, 0.42F, 0.90F, 1.50F)
                        .phases(0.0F, 0.38F, 0.52F, 0.90F, 1.50F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 1)
                        .build());

        TESSEN_NEW_DUAL_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_dual_auto_3")
                        .speed(0.18F)
                        .phases(0.0F, 0.42F, 0.48F, 1.23F, 1.50F)
                        .phases(0.68F, 0.75F, 0.82F, 1.23F, 1.50F)
                        .phases(0.68F, 0.78F, 0.88F, 1.23F, 1.50F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 1)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 2)
                        .build());

        TESSEN_NEW_DUAL_AUTO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/new/tessen/tessen_dual_auto_4")
                        .speed(0.2F)
                        .phases(0.0F, 0.38F, 0.42F, 0.98F, 1.90F)
                        .phases(0.0F, 0.38F, 0.42F, 0.98F, 1.90F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_SMALL)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 1)
                        .build());


        TESSEN_NEW_SKILL_AUTO_1 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_auto_1",
                6,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DAGGER,
                AttackHand.RIGHT_HAND,
                StunType.SHORT,
                0.16F,
                1.15F,
                false
        );
        TESSEN_NEW_SKILL_AUTO_2 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_auto_2",
                10,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DAGGER,
                AttackHand.RIGHT_HAND,
                StunType.SHORT,
                0.16F,
                1.70F,
                false
        );
        TESSEN_NEW_SKILL_AUTO_3 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_auto_3",
                10,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DAGGER,
                AttackHand.RIGHT_HAND,
                StunType.SHORT,
                0.16F,
                2.20F,
                false
        );
        TESSEN_NEW_SKILL_AUTO_4 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_auto_4",
                10,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DAGGER,
                AttackHand.RIGHT_HAND,
                StunType.SHORT,
                0.16F,
                2.08F,
                false
        );
        TESSEN_NEW_SKILL_DUAL_AUTO_2 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_dual_auto_2",
                8,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DAGGER,
                AttackHand.LEFT_HAND,
                StunType.SHORT,
                0.16F,
                1.40F,
                false
        );
        TESSEN_NEW_SKILL_DUAL_AUTO_3 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_dual_auto_3",
                12,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.LONGSWORD,
                AttackHand.HANDS,
                StunType.SHORT,
                0.16F,
                1.70F,
                false
        );
        TESSEN_NEW_SKILL_DUAL_AUTO_4 = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/new/tessen/tessen_skill_dual_auto_4",
                8,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DUAL_SWORD,
                AttackHand.HANDS,
                StunType.SHORT,
                0.16F,
                1.95F,
                false
        );
        TESSEN_SKILL_DASH = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_dash",
                8,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                1.50F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DUAL_SWORD,
                AttackHand.HANDS,
                StunType.SHORT,
                0.48F,
                1.50F,
                true
        );
        TESSEN_SKILL_AIRSLASH = WOHAnimationUtils.createTessenThrowAttackAnimation(
                builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_airslash",
                8,
                0.1F,
                0.0F,
                0.05F,
                0.08F,
                0.12F,
                0.15F,
                0.05F,
                EpicFightSounds.WHOOSH_SMALL,
                EpicFightSounds.BLADE_HIT,
                EpicFightParticles.HIT_BLADE,
                ColliderPreset.DUAL_SWORD,
                AttackHand.HANDS,
                StunType.SHORT,
                0.26F,
                0.82F,
                false
        );
    }
}
