package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.animation_types.WohSheathAnimation;
import net.kenji.woh.registry.WohColliderPreset;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;


public class ShotogatanaAnimations {

    public static StaticAnimation SHOTOGATANA_UNSHEATHED_IDLE;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_WALK;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_RUN;
    public static StaticAnimation SHOTOGATANA_IDLE;
    public static StaticAnimation SHOTOGATANA_WALK;
    public static StaticAnimation SHOTOGATANA_RUN;
    public static StaticAnimation SHOTOGATANA_GUARD;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_GUARD;
    public static WohSheathAnimation SHOTOGATANA_SHEATH;
    public static WohSheathAnimation SHOTOGATANA_SHEATH_ALT1;
    public static WohSheathAnimation SHOTOGATANA_SHEATH_ALT2;

    public static StaticAnimation SHOTOGATANA_UNSHEATH;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_AUTO_1;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_AUTO_2;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_AUTO_3;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_AUTO_4;
    public static StaticAnimation SHOTOGATANA_UNSHEATHED_AUTO_5;
    public static StaticAnimation SHOTOGATANA_AUTO_1;
    public static StaticAnimation SHOTOGATANA_AUTO_2;
    public static StaticAnimation SHOTOGATANA_AUTO_3;
    public static StaticAnimation SHOTOGATANA_AUTO_4;
    public static StaticAnimation SHOTOGATANA_AUTO_5;
    public static StaticAnimation SHOTOGATANA_AUTO_6;
    public static StaticAnimation SHOTOGATANA_DASH;
    public static StaticAnimation SHOTOGATANA_AIRSLASH;

    public static StaticAnimation SHOTOGATANA_SKILL_INNATE;





    public static void build(){


        HumanoidArmature biped = Armatures.BIPED;

        SHOTOGATANA_UNSHEATHED_IDLE = (new StaticAnimation(0.1f, true, "biped/living/shotogatana/shotogatana_unsheathed_idle", biped));
        SHOTOGATANA_UNSHEATHED_WALK = (new StaticAnimation(0.1f, true, "biped/living/shotogatana/shotogatana_unsheathed_walk", biped));
        SHOTOGATANA_UNSHEATHED_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/shotogatana/shotogatana_guard_unsheathed", 0.25F, null);
        SHOTOGATANA_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/shotogatana/shotogatana_guard", 0.25F, null);

        SHOTOGATANA_UNSHEATHED_RUN = (new StaticAnimation(0.1f, true, "biped/living/shotogatana/shotogatana_unsheathed_run", biped));
        SHOTOGATANA_SHEATH = WOHAnimationUtils.createSheathAnimation("biped/skill/shotogatana/shotogatana_sheathe", false, 0.1f, 1.73f, null);
        SHOTOGATANA_SHEATH_ALT1 = WOHAnimationUtils.createSheathAnimation("biped/skill/shotogatana/shotogatana_sheathe_alt1", false,  0.1f, 2.5f, null);
        SHOTOGATANA_SHEATH_ALT2 = WOHAnimationUtils.createSheathAnimation("biped/skill/shotogatana/shotogatana_sheathe_alt2", false,  0.1f, 1.93f, null);

        SHOTOGATANA_UNSHEATH = WOHAnimationUtils.createLivingAnimation("biped/skill/shotogatana/shotogatana_unsheathe", false, 0.1f, 0.23f, -1, null);

        SHOTOGATANA_IDLE = WOHAnimationUtils.createLivingAnimation("biped/living/shotogatana/shotogatana_idle", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_WALK = WOHAnimationUtils.createLivingAnimation("biped/living/shotogatana/shotogatana_walk", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_RUN = WOHAnimationUtils.createLivingAnimation("biped/living/shotogatana/shotogatana_run", true, 0.1f, -1f, -1, null);



        SHOTOGATANA_UNSHEATHED_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
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
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_UNSHEATHED_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_2",
                2,
                0.1F,
                1.0F,
                3F,
                1.8F,
                new float[]{0.0F, 1F},
                new float[]{0.35F, 1.1F},
                new float[]{0.7F, 1.3F},
                new float[]{0.8f, 1.7F},
                new float[]{0.9f, 2F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.toolR, biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_UNSHEATHED_AUTO_3 =WOHAnimationUtils. createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_3",
                2,
                0.1F,
                1.0F,
                3F,
                2.4F,
                new float[]{0.0F, 1F},
                new float[]{0.5F, 1.1F},
                new float[]{0.8F, 1.2F},
                new float[]{2.5F, 2.5F},
                new float[]{1F, 2.2F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.toolR, biped.toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        SHOTOGATANA_UNSHEATHED_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_4",
                1,
                0.1F,
                1.0F,
                3.5F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        SHOTOGATANA_UNSHEATHED_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_unsheathed_auto_5",
                1,
                0.1F,
                1.0F,
                3.5F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        SHOTOGATANA_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH,
                "biped/combat/shotogatana/shotogatana_auto_1",
                1,
                0.15F,
                1F,
                6.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.37F},
                new float[]{0.78f},
                new float[]{0.85f},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.LONG,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT1,
                0.2f,
                -1
        );
        SHOTOGATANA_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_2",
                1,
                0.15F,
                1F,
                5.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.42F},
                new float[]{0.8F},
                new float[]{1.52F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_3",
                1,
                0.15F,
                1F,
                5.5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.33F},
                new float[]{0.42F},
                new float[]{0.80F},
                new float[]{1.18F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT1,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_4",
                1,
                0.15F,
                1F,
                5F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.28F},
                new float[]{0.38F},
                new float[]{0.65F},
                new float[]{0.93F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/shotogatana/shotogatana_auto_5",
                2,
                0.15F,
                1F,
                6F,
                0.5F,
                new float[]{0.0F, 0.5F},
                new float[]{0.28F, 0.75F},
                new float[]{0.32F, 1.05F},
                new float[]{1.42F, 1.42F},
                new float[]{1.60F, 1.60F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_ROD.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI, ColliderPreset.TACHI},
                new Joint[]{biped.toolR, biped.toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH_ALT2,
                -1,
                -1
        );
        SHOTOGATANA_AUTO_6 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/shotogatana/shotogatana_auto_6",
                1,
                0.15F,
                1F,
                7F,
                0.5F,
                new float[]{0.0F},
                new float[]{0.8F},
                new float[]{1.15F},
                new float[]{2.30f},
                new float[]{3.18f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                ShotogatanaAnimations.SHOTOGATANA_SHEATH,
                -1,
                -1
        );

        SHOTOGATANA_DASH = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK_JUMP,
                "biped/combat/shotogatana/shotogatana_dash",
                1,
                0.05F,
                1F,
                6F,
                2F,
                new float[]{0.0F},
                new float[]{0.2F},
                new float[]{0.45F},
                new float[]{1F},
                new float[]{1.25F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.LONG,
                0.25f,
                -1
        );
        SHOTOGATANA_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/shotogatana/shotogatana_airslash",
                1,
                0.1F,
                1F,
                5.75F,
                2F,
                new float[]{0.0F},
                new float[]{0.62F},
                new float[]{0.75F},
                new float[]{1.75F},
                new float[]{3.20F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                new float[]{0.05F, 3F},
                0.1f,
                0.9f
        );
        SHOTOGATANA_SKILL_INNATE = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH,
                "biped/skill/shotogatana/shotogatana_skill_innate",
                2,
                0.05F,
                1F,
                6F,
                0.2F,
                new float[]{0.0F, 0.88F},
                new float[]{0.55F, 1.0F},
                new float[]{0.70F, 1.10F},
                new float[]{1.42F, 1.42F},
                new float[]{1.88F, 1.88F},
                new SoundEvent[]{EpicFightSounds.NO_SOUND.get(), EpicFightSounds.NO_SOUND.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WohColliderPreset.SHEATHED_BLADE, WohColliderPreset.SHEATHED_BLADE},
                new Joint[]{biped.torso, biped.torso},
                StunType.LONG,
                0.38f,
                0.8f
        );
    }
}
