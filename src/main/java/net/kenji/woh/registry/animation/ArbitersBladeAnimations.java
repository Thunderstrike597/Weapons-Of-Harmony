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


public class ArbitersBladeAnimations {
    public static StaticAnimation ARBITERS_BLADE_HOLD;

    public static StaticAnimation ARBITERS_BLADE_AUTO_1;
    public static StaticAnimation ARBITERS_BLADE_AUTO_2;
    public static StaticAnimation ARBITERS_BLADE_AUTO_3;
    public static StaticAnimation ARBITERS_BLADE_AUTO_4;

    public static StaticAnimation ARBITERS_BLADE_SKILL_AUTO_1;
    public static StaticAnimation ARBITERS_BLADE_SKILL_AUTO_2;
    public static StaticAnimation ARBITERS_BLADE_SKILL_AUTO_3;
    public static StaticAnimation ARBITERS_BLADE_SKILL_AUTO_4;
    public static StaticAnimation ARBITERS_BLADE_AUTO_5
            ;
    public static StaticAnimation ARBITERS_BLADE_AIRSLASH;

    public static StaticAnimation ARBITERS_BLADE_SKILL_AIM_AUTO_1;

    public static StaticAnimation ARBITERS_BLADE_SKILL_ACTIVATE_START;
    public static StaticAnimation ARBITERS_BLADE_SKILL_ACTIVATE_MID;
    public static StaticAnimation ARBITERS_BLADE_SKILL_ACTIVATE_END;

    public static StaticAnimation ARBITERS_BLADE_AIM;




    public static void build(){
        HumanoidArmature biped = Armatures.BIPED;

        ARBITERS_BLADE_HOLD = (new StaticAnimation(0.35f, true, "biped/living/arbiters_blade/arbiters_blade_hold", biped));

        ARBITERS_BLADE_SKILL_ACTIVATE_START = (new StaticAnimation(0.35f, false, "biped/skill/arbiters_blade/arbiters_blade_skill_activate_start", biped));
        ARBITERS_BLADE_SKILL_ACTIVATE_MID = (new StaticAnimation(0.35f, false, "biped/skill/arbiters_blade/arbiters_blade_skill_activate_mid", biped));
        ARBITERS_BLADE_SKILL_ACTIVATE_END = (new StaticAnimation(0.35f, false, "biped/skill/arbiters_blade/arbiters_blade_skill_activate_end", biped));

        ARBITERS_BLADE_AIM = (new StaticAnimation(0.35f, false, "biped/skill/arbiters_blade/arbiters_blade_aim", biped));

        ARBITERS_BLADE_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_auto_1",
                2,
                0.35F,
                1F,
                6F,
                0.45F,
                new float[]{0.0F, 0.40F},
                new float[]{0.25F, 0.68F},
                new float[]{0.38F, 0.77F},
                new float[]{0.85F, 0.85F},
                new float[]{1.5f, 1.5f},
                0.95F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR, biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -1
        );
        ARBITERS_BLADE_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_auto_2",
                1,
                0.35F,
                1F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.28F},
                new float[]{0.40F},
                new float[]{0.45f},
                new float[]{1.5f},
                0.95F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -1
        );
        ARBITERS_BLADE_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_auto_3",
                2,
                0.35F,
                1.15F,
                6F,
                0.45F,
                new float[]{0.0F, 0.50F},
                new float[]{0.30F, 0.88F},
                new float[]{0.40F, 1.05F},
                new float[]{1.25f, 1.25f},
                new float[]{1.5f, 1.5f},
                1.2F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR, biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -1
        );
        ARBITERS_BLADE_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/arbiters_blade/arbiters_blade_auto_4",
                1,
                0.35F,
                1F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.35F},
                new float[]{1.32f},
                new float[]{2.5f},
                1.25F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -70
        );
        ARBITERS_BLADE_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/arbiters_blade/arbiters_blade_airslash",
                3,
                0.35F,
                1F,
                6F,
                0.45F,
                new float[]{0.0F, 0.30F, 0.58F},
                new float[]{0.28F, 0.42F, 0.60F},
                new float[]{0.32F,0.53F, 0.70F},
                new float[]{1.32f, 1.32f, 1.32f},
                new float[]{1.60F, 1.60F, 1.60F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(),EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                StunType.SHORT,
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.LONGSWORD, ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR, biped.toolR, biped.toolR},
                new float[] {0.05F, 1.40F},
                -1F,
                -1F
        );

        ARBITERS_BLADE_SKILL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_skill_auto_1",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.36F},
                new float[]{0.42f},
                new float[]{1.5f},
                0.95F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -45
        );
        ARBITERS_BLADE_SKILL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_skill_auto_2",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.38F},
                new float[]{0.46F},
                new float[]{0.56f},
                new float[]{1.5f},
                1.05F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -32
        );
        ARBITERS_BLADE_SKILL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_skill_auto_3",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.38F},
                new float[]{0.46F},
                new float[]{0.5f},
                new float[]{2f},
                1.25F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -28
        );
        ARBITERS_BLADE_SKILL_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/arbiters_blade/arbiters_blade_skill_auto_4",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.5F},
                new float[]{1.15f},
                new float[]{2.5f},
                1.25F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -70
        );
        ARBITERS_BLADE_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/arbiters_blade/arbiters_blade_auto_5",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.3F},
                new float[]{0.40F},
                new float[]{0.53f},
                new float[]{1.38f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        ARBITERS_BLADE_SKILL_AIM_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/arbiters_blade/arbiters_blade_aim_auto_1",
                1,
                0.35F,
                0.05F,
                6F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.36F},
                new float[]{0.42f},
                new float[]{1.5f},
                0.64F,
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F,
                -45
        );
        /*ODACHI_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/odachi/odachi_airslash",
                1,
                0.1F,
                1F,
                5.75F,
                2F,
                new float[]{0.0F},
                new float[]{0.35F},
                new float[]{0.52F},
                new float[]{0.95F},
                new float[]{1.18F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.toolR},
                new float[]{0.05F, 1.10F},
                -1,
                1
        );
       */

    }
}
