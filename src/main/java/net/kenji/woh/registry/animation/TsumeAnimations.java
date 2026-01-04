package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.registry.WohColliderPreset;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
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

    public static AnimationManager.AnimationAccessor<StaticAnimation> TSUME_GUARD;

    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        TSUME_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_hold", false, 0.1f, -1, -1,null);
        TSUME_SKILL_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_skill_hold", false, 0.1f, -1, -1,null);

        TSUME_RUN = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/tsume/tsume_run", false, 0.1f, -1, -1,null);
        TSUME_GUARD = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_guard", false, 0.1f, -1, -1,null);

        TSUME_SKILL_ACTIVATE = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_skill_activate", false, 0.1f, -1, -1,null);
        TSUME_SKILL_DEACTIVATE = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/tsume/tsume_skill_deactivate", false, 0.1f, -1, -1,null);


        TSUME_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_auto_1",
                1,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F},
                new float[]{0.35F},
                new float[]{0.43F},
                new float[]{0.78F},
                new float[]{1.5F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1,
                -1
        );

        TSUME_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_auto_2",
                1,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F},
                new float[]{0.22F},
                new float[]{0.32F},
                new float[]{0.7F},
                new float[]{1.32F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );

        TSUME_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_auto_3",
                2,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F, 0.30F},
                new float[]{0.35F, 0.36F},
                new float[]{0.40F, 0.42F},
                new float[]{0.88F, 0.88F},
                new float[]{1.32F, 1.32F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );

        TSUME_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_auto_4",
                2,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F, 0.30F},
                new float[]{0.15F, 0.15F},
                new float[]{0.20F, 0.20F},
                new float[]{0.75F, 0.75F},
                new float[]{1.5F, 1.5F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TSUME_DASH = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/tsume/tsume_dash",
                2,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F, 0.30F},
                new float[]{0.32F, 0.32F},
                new float[]{0.38F, 0.38F},
                new float[]{0.88F, 0.88F},
                new float[]{1.32F, 1.32F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TSUME_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(builder,
                "biped/combat/tsume/tsume_airslash",
                3,
                0.1F,
                1F,
                6F,
                0.2F,
                new float[]{0.0F, 0.38F, 0.70F},
                new float[]{0.13F, 0.42F, 0.70F},
                new float[]{0.20F, 0.50F, 0.80F},
                new float[]{1.18F, 1.18F, 1.18F},
                new float[]{1.30F, 1.30F, 1.30F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL, biped.get().toolR},
                new float[]{0.05F, 1.22F},
                -1,
                -1
        );
        TSUME_SKILL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_skill_auto_1",
                2,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F, 0.0F},
                new float[]{0.20F, 0.52F},
                new float[]{0.30F, 0.58F},
                new float[]{0.78F, 0.78F},
                new float[]{1.5F, 1.5F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TSUME_SKILL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_skill_auto_2",
                2,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F, 0.0F},
                new float[]{0.25F, 0.25F},
                new float[]{0.36F, 0.56F},
                new float[]{0.65F, 0.65F},
                new float[]{1.5F, 1.5F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TSUME_SKILL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tsume/tsume_skill_auto_3",
                2,
                0.1F,
                1F,
                2F,
                0.08F,
                new float[]{0.0F, 0.0F},
                new float[]{0.1F, 0.1F},
                new float[]{0.16F, 0.16F},
                new float[]{0.5F, 0.5F},
                new float[]{1.5F, 1.5F},
                new Supplier[]{EpicFightSounds.WHOOSH_SMALL, EpicFightSounds.WHOOSH_SMALL},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.TSUME_CLAWS, WohColliderPreset.TSUME_CLAWS},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );

    }
}
