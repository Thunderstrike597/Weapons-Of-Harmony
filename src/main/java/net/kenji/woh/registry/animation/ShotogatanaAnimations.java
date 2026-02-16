package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.registry.WohColliderPreset;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
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


public class ShotogatanaAnimations {

    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH_ALT1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH_ALT2;

    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATH;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_UNSHEATHED_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_UNSHEATHED_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_UNSHEATHED_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_UNSHEATHED_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_UNSHEATHED_AUTO_5;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_5;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_6;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_DASH;
    public static AnimationManager.AnimationAccessor<? extends AirSlashAnimation> SHOTOGATANA_AIRSLASH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_SKILL_INNATE;





    public static void build(AnimationManager.AnimationBuilder builder){


        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        SHOTOGATANA_UNSHEATHED_IDLE = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_idle", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_UNSHEATHED_WALK = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_walk", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_UNSHEATHED_GUARD = builder.nextAccessor("biped/skill/shotogatana/shotogatana_guard_unsheathed", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_GUARD = builder.nextAccessor("biped/skill/shotogatana/shotogatana_guard", accessor -> new StaticAnimation(true,accessor, biped));

        SHOTOGATANA_UNSHEATHED_RUN = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_run", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_SHEATH = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe", 0.1f, 1.73f, null);
        SHOTOGATANA_SHEATH_ALT1 = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe_alt1",   0.1f, 2.5f, null);
        SHOTOGATANA_SHEATH_ALT2 = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe_alt2",  0.1f, 1.93f, null);

        SHOTOGATANA_UNSHEATH = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/shotogatana/shotogatana_unsheathe", false, 0.1f, 0.23f, -1, null);

        SHOTOGATANA_IDLE = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_idle", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_WALK = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_walk", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_RUN = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_run", true, 0.1f, -1f, -1, null);



        SHOTOGATANA_UNSHEATHED_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_1",
                1,
                0.1F,
                1F,
                3F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.75F},
                new float[]{1.1F},
                new float[]{1.65f},
                new float[]{1.8f},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_UNSHEATHED_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_2",
                2,
                0.1F,
                0.1F,
                3F,
                1.8F,
                new float[]{0.0F, 1F},
                new float[]{0.35F, 1.1F},
                new float[]{0.7F, 1.3F},
                new float[]{0.8f, 1.7F},
                new float[]{0.9f, 2F},
                new Supplier[]{EpicFightSounds.WHOOSH, EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR, biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_UNSHEATHED_AUTO_3 =WOHAnimationUtils. createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_3",
                2,
                0.1F,
                0.1F,
                3F,
                2.4F,
                new float[]{0.0F, 1F},
                new float[]{0.5F, 1.1F},
                new float[]{0.8F, 1.2F},
                new float[]{2.5F, 2.5F},
                new float[]{1F, 2.2F},
                new Supplier[]{EpicFightSounds.WHOOSH, EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR, biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        SHOTOGATANA_UNSHEATHED_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_4",
                1,
                0.1F,
                0.1F,
                3.5F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        SHOTOGATANA_UNSHEATHED_AUTO_5 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_5",
                1,
                0.1F,
                0.1F,
                3.5F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH,
                "biped/combat/shotogatana/shotogatana_auto_1",
                1,
                0.15F,
                -1F,
                6.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.37F},
                new float[]{0.78f},
                new float[]{0.85f},
                new Supplier[]{EpicFightSounds.WHOOSH_SHARP},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.LONG,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT1,
                0.28f,
                -1
        );
        SHOTOGATANA_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_2",
                1,
                0.15F,
                0.1F,
                5.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.42F},
                new float[]{0.8F},
                new float[]{1.52F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_3",
                1,
                0.15F,
                0.1F,
                5.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.33F},
                new float[]{0.42F},
                new float[]{0.80F},
                new float[]{1.18F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT1,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_4",
                1,
                0.15F,
                0.1F,
                5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.28F},
                new float[]{0.38F},
                new float[]{0.65F},
                new float[]{0.93F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_5 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_5",
                2,
                0.15F,
                0.1F,
                6F,
                0.5F,
                new float[]{0.0F, 0.5F},
                new float[]{0.28F, 0.75F},
                new float[]{0.32F, 1.05F},
                new float[]{1.42F, 1.42F},
                new float[]{1.60F, 1.60F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR, biped.get().toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_6 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/shotogatana/shotogatana_auto_6",
                1,
                0.15F,
                0.1F,
                7F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.8F},
                new float[]{1.15F},
                new float[]{2.30f},
                new float[]{3.18f},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH,
                -1,
                -1
        );

        SHOTOGATANA_DASH = WOHAnimationUtils.createDashAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK_JUMP,
                "biped/combat/shotogatana/shotogatana_dash",
                1,
                0.05F,
                0.1F,
                6F,
                2F,
                new float[]{0.0F},
                new float[]{0.2F},
                new float[]{0.45F},
                new float[]{1F},
                new float[]{1.25F},
                new Supplier[]{EpicFightSounds.WHOOSH_SHARP},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                StunType.LONG,
                null,
                0.25f,
                -1
        );
        SHOTOGATANA_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(builder,
                "biped/combat/shotogatana/shotogatana_airslash",
                1,
                0.1F,
                0.1F,
                5.75F,
                2F,
                new float[]{0.0F},
                new float[]{0.62F},
                new float[]{0.75F},
                new float[]{1.75F},
                new float[]{3.20F},
                new Supplier[]{EpicFightSounds.WHOOSH_SHARP},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR},
                new float[]{0.05F, 3F},
                0.1f,
                0.9f
        );
        SHOTOGATANA_SKILL_INNATE = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH,
                "biped/skill/shotogatana/shotogatana_skill_innate",
                2,
                0.05F,
                0.1F,
                6F,
                0.2F,
                new float[]{0.0F, 0.88F},
                new float[]{0.55F, 1.0F},
                new float[]{0.70F, 1.10F},
                new float[]{1.42F, 1.42F},
                new float[]{1.88F, 1.88F},
                new Supplier[]{() -> SoundEvents.EMPTY, () -> SoundEvents.EMPTY},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.SHEATHED_BLADE, WohColliderPreset.SHEATHED_BLADE},
                new Joint[]{biped.get().torso, biped.get().torso},
                StunType.LONG,
                0.38f,
                0.54f
        );
    }
}
