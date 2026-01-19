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


public class GenericAnimations {
    public static StaticAnimation DEFEAT_IDLE;
    public static StaticAnimation DEFEAT_KNEEL;

    public static StaticAnimation KATAJUTSU_IDLE;

    public static StaticAnimation KATAJUTSU_AUTO_1;
    public static StaticAnimation KATAJUTSU_AUTO_2;
    public static StaticAnimation KATAJUTSU_AUTO_3;

    public static StaticAnimation COMBAT_FIST_DASH;
    public static StaticAnimation COMBAT_FIST_AIRKICK;


    public static void build(){
        HumanoidArmature biped = Armatures.BIPED;

        DEFEAT_IDLE = (new StaticAnimation(0.35f, true, "biped/living/generic/defeat_idle", biped));
        DEFEAT_KNEEL = (new StaticAnimation(0.35f, false, "biped/living/generic/defeat_kneel", biped));
        KATAJUTSU_IDLE = (new StaticAnimation(0.35f, true, "biped/living/katajutsu/katajutsu_idle", biped));

        KATAJUTSU_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_1",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.3F},
                new float[]{0.4F},
                new float[]{0.6F},
                new float[]{0.8F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1,
                -1
        );

        KATAJUTSU_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_2",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.07F},
                new float[]{0.15F},
                new float[]{0.25F},
                new float[]{0.45F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.toolL},
                StunType.SHORT,
                -1,
                -1
        );
        KATAJUTSU_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_3",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.38F},
                new float[]{0.60F},
                new float[]{1.05F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.legR},
                StunType.SHORT,
                -1,
                -1
        );
        COMBAT_FIST_DASH = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/generic/combat_fist_dash",
                2,
                0.05F,
                1F,
                2F,
                0.4F,
                new float[]{0.0F, 0.3F},
                new float[]{0.05F, 0.38F},
                new float[]{0.1F, 0.5F},
                new float[]{0.15F, 0.95F},
                new float[]{0.2F, 1F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get(), EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT, EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST, ColliderPreset.FIST},
                new Joint[]{biped.toolL, biped.toolR},
                StunType.LONG,
                -1,
                -1
        );
        COMBAT_FIST_AIRKICK = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/generic/combat_fist_airkick",
                1,
                0.1F,
                1F,
                2F,
                1.2F,
                new float[]{0.0F},
                new float[]{0.1F},
                new float[]{0.18F},
                new float[]{0.25F},
                new float[]{0.8F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_ROD.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                StunType.SHORT,
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.toolR},
                new float[]{0.0F, 0.45F},
                -1,
                -1
        );
    }
}
