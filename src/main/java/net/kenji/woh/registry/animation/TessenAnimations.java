package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
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


public class TessenAnimations {
    public static StaticAnimation TESSEN_HOLD;
    public static StaticAnimation TESSEN_RUN;
    public static StaticAnimation TESSEN_SKILL_HOLD;
    public static StaticAnimation TESSEN_SKILL_WALK;
    public static StaticAnimation TESSEN_SKILL_ACTIVATE;
    public static StaticAnimation TESSEN_SKILL_DEACTIVATE;
    public static StaticAnimation TESSEN_GUARD;
    public static StaticAnimation TESSEN_DUAL_GUARD;

    public static StaticAnimation TESSEN_AUTO_1;
    public static StaticAnimation TESSEN_AUTO_2;
    public static StaticAnimation TESSEN_AUTO_3;
    public static StaticAnimation TESSEN_AUTO_4;

    public static StaticAnimation TESSEN_DUAL_AUTO_1;
    public static StaticAnimation TESSEN_DUAL_AUTO_2;
    public static StaticAnimation TESSEN_DUAL_AUTO_3;
    public static StaticAnimation TESSEN_DUAL_AUTO_4;
    public static StaticAnimation TESSEN_DUAL_AUTO_5;
    public static StaticAnimation TESSEN_DUAL_AIRSLASH;

    public static StaticAnimation TESSEN_SKILL_AUTO_1;
    public static StaticAnimation TESSEN_SKILL_AUTO_2;
    public static StaticAnimation TESSEN_SKILL_AUTO_3;
    public static StaticAnimation TESSEN_SKILL_AUTO_4;

    public static StaticAnimation TESSEN_SKILL_DUAL_AUTO_1;
    public static StaticAnimation TESSEN_SKILL_DUAL_AUTO_2;
    public static StaticAnimation TESSEN_SKILL_DUAL_AUTO_3;
    public static StaticAnimation TESSEN_SKILL_DUAL_AUTO_4;


    public static StaticAnimation TESSEN_SKILL_DASH;

    public static StaticAnimation TESSEN_SKILL_AIRSLASH;



    public static void build(){
        HumanoidArmature biped = Armatures.BIPED;

        TESSEN_HOLD = (new StaticAnimation(0.35f, true, "biped/living/tessen/tessen_dual_hold", biped));
        TESSEN_RUN = (new StaticAnimation(0.35f, true, "biped/living/tessen/tessen_dual_run", biped));
        TESSEN_SKILL_HOLD = (new StaticAnimation(0.25f, true, "biped/living/tessen/tessen_skill_hold", biped));
        TESSEN_SKILL_WALK = (new StaticAnimation(0.25f, true, "biped/living/tessen/tessen_skill_walk", biped));
        TESSEN_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/tessen/tessen_guard", 0.25F, null);
        TESSEN_DUAL_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/tessen/tessen_dual_guard", 0.25F, null);

        TESSEN_SKILL_ACTIVATE = (new StaticAnimation(0.3f, false, "biped/skill/tessen/tessen_skill_activate", biped));
        TESSEN_SKILL_DEACTIVATE = (new StaticAnimation(0.3f, false, "biped/skill/tessen/tessen_skill_deactivate", biped));

        TESSEN_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_auto_1",
                1,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F},
                new float[]{0.18F},
                new float[]{0.26F},
                new float[]{0.62F},
                new float[]{0.9F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_auto_2",
                1,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F},
                new float[]{0.2F},
                new float[]{0.25F},
                new float[]{0.66F},
                new float[]{1.15F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_auto_3",
                1,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.32F},
                new float[]{0.68F},
                new float[]{1.15F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_auto_4",
                1,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F},
                new float[]{0.42F},
                new float[]{0.5F},
                new float[]{0.98F},
                new float[]{1.55F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_DUAL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_dual_auto_1",
                2,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F},
                new float[]{0.07F, 0.15F},
                new float[]{0.15F, 0.22F},
                new float[]{0.55F, 0.55F},
                new float[]{0.9F, 0.9F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );

        TESSEN_DUAL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_dual_auto_2",
                2,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F},
                new float[]{0.02F, 0.15F},
                new float[]{0.1F, 0.20F},
                new float[]{0.56F, 0.56F},
                new float[]{0.85F, 0.85F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_DUAL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_dual_auto_3",
                3,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.0F},
                new float[]{0.08F, 0.46F, 0.46F},
                new float[]{0.12F, 0.55F, 0.55F},
                new float[]{0.92F, 0.92F, 0.92F},
                new float[]{1F, 1F, 1F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolR, biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_DUAL_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_dual_auto_4",
                2,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F},
                new float[]{0.1F, 0.1F},
                new float[]{0.105F, 0.105F},
                new float[]{0.42F, 0.42F},
                new float[]{0.7F, 0.7F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_DUAL_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_dual_auto_5",
                3,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.0F},
                new float[]{0.1F, 0.2F, 0.28F },
                new float[]{0.12F, 0.25F, 0.43F},
                new float[]{0.78F, 0.78F, 0.78F},
                new float[]{1.2F, 1.2F, 1.2F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_DUAL_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/tessen/tessen_dual_airslash",
                4,
                0.1F,
                1F,
                5.75F,
                2F,
                new float[]{0.0F, 0.1F, 0.28F, 0.35F},
                new float[]{0.1F, 0.15F, 0.3F, 0.38F},
                new float[]{0.18F, 0.24F, 0.38F, 0.42F},
                new float[]{0.68F, 0.68F,0.68F, 0.68F,},
                new float[]{1.28F, 0.68F, 0.68F, 0.68F,},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL, biped.toolR},
                new float[]{0.05F, 1.2F},
                -1,
                -1
        );

        TESSEN_SKILL_DUAL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_dual_auto_1",
                5,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.43F, 0.56F, 0.71F, 0.73F},
                new float[]{0.28F, 0.44F, 0.57F, 0.72F, 0.74F},
                new float[]{0.42F, 0.55F, 0.7F, 0.78F, 0.98F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolL, biped.toolL, biped.toolL, biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_DUAL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_dual_auto_2",
                5,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.43F, 0.56F, 0.71F, 0.73F},
                new float[]{0.28F, 0.44F, 0.57F, 0.72F, 0.92F},
                new float[]{0.45F, 0.55F, 0.65F, 0.82F, 0.98F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolR, biped.toolR, biped.toolR, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_DUAL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_dual_auto_3",
                8,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.58F, 0.58F, 0.78F, 0.78F, 1F, 1F},
                new float[]{0.45F, 0.45F, 0.62F, 0.62F, 0.8F, 0.8F, 1.1F, 1.1F},
                new float[]{0.55F, 0.55F, 0.76F, 0.76F, 0.92F, 0.92F, 1.2F, 1.2F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_DUAL_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_dual_auto_4",
                8,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.35F, 0.35F, 0.46F, 0.46F, 0.56F, 0.56F},
                new float[]{0.1F, 0.11F, 0.38F, 0.38F, 0.48F, 0.48F, 0.58F, 0.58F},
                new float[]{0.33F, 0.33F, 0.45F, 0.45F, 0.55F, 0.55F, 0.68F, 0.68F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/tessen/tessen_skill_airslash",
                8,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.35F, 0.35F, 0.46F, 0.46F, 0.56F, 0.56F},
                new float[]{0.1F, 0.11F, 0.38F, 0.38F, 0.48F, 0.48F, 0.58F, 0.58F},
                new float[]{0.33F, 0.33F, 0.45F, 0.45F, 0.55F, 0.55F, 0.68F, 0.68F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR},
                new float[]{0.05F, 1.75F},
                -1,
                -1
        );
        TESSEN_SKILL_DASH = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/tessen/tessen_skill_dash",
                8,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.0F, 0.35F, 0.35F, 0.46F, 0.46F, 0.56F, 0.56F},
                new float[]{0.52F, 0.52F, 0.65F, 0.65F, 0.72F, 0.72F, 0.9F, 0.9F},
                new float[]{0.62F, 0.62F, 0.70F, 0.70F, 0.85F, 0.85F, 0.95F, 0.95F},
                new float[]{1.85F, 1.85F, 1.85F, 1.85F, 1.85F, 1.85F, 1.85F, 1.85F},
                new float[]{3.0F, 3.0F, 3.0F, 3.0F, 3.0F, 3.0F, 3.0F, 3.0F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR, biped.toolL, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_auto_1",
                5,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.43F, 0.56F, 0.71F, 0.73F},
                new float[]{0.28F, 0.44F, 0.57F, 0.72F, 0.92F},
                new float[]{0.45F, 0.55F, 0.65F, 0.82F, 0.98F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolR, biped.toolR, biped.toolR, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_auto_2",
                5,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.43F, 0.56F, 0.71F, 0.83F},
                new float[]{0.28F, 0.44F, 0.57F, 0.72F, 0.85F},
                new float[]{0.45F, 0.55F, 0.65F, 0.82F, 0.9F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolR, biped.toolR, biped.toolR, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
        TESSEN_SKILL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tessen/tessen_skill_auto_3",
                5,
                0.1F,
                1F,
                2F,
                0.1F,
                new float[]{0.0F, 0.58F, 0.68F, 0.85F, 1F},
                new float[]{0.48F, 0.60F, 0.72F, 0.9F, 1.10F},
                new float[]{0.55F, 0.65F, 0.8F, 0.98F, 1.25F},
                new float[]{1.5F, 1.5F, 1.5F, 1.5F, 1.5F},
                new float[]{1.73F, 1.73F, 1.73F, 1.73F, 1.73F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get(), EpicFightSounds.WHOOSH_SMALL.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER, ColliderPreset.DAGGER},
                new Joint[]{biped.toolR, biped.toolR, biped.toolR, biped.toolR, biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );
    }
}
