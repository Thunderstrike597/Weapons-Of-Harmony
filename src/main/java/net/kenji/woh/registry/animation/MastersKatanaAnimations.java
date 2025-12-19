package net.kenji.woh.registry.animation;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.animations.WohSheathAnimation;
import net.kenji.woh.gameasset.animations.WohStaticAnimation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;


public class MastersKatanaAnimations {

    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_IDLE;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_WALK;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_RUN;
    public static StaticAnimation ENHANCED_KATANA_IDLE;
    public static StaticAnimation ENHANCED_KATANA_WALK;
    public static WohSheathAnimation ENHANCED_KATANA_SHEATH;
    public static WohSheathAnimation ENHANCED_KATANA_SHEATH_ALT1;
    public static WohSheathAnimation ENHANCED_KATANA_SHEATH_ALT2;

    public static StaticAnimation ENHANCED_KATANA_UNSHEATH;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_AUTO_1;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_AUTO_2;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_AUTO_3;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_AUTO_4;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATHED_AUTO_5;
    public static StaticAnimation ENHANCED_KATANA_AUTO_1;
    public static StaticAnimation ENHANCED_KATANA_AUTO_2;
    public static StaticAnimation ENHANCED_KATANA_AUTO_3;
    public static StaticAnimation ENHANCED_KATANA_AUTO_4;
    public static StaticAnimation ENHANCED_KATANA_DASH;
    public static StaticAnimation ENHANCED_KATANA_AIRSLASH;





    public static StaticAnimation COMBAT_FIST_AUTO_1;
    public static StaticAnimation COMBAT_FIST_AUTO_2;
    public static StaticAnimation COMBAT_FIST_DASH;
    public static StaticAnimation COMBAT_FIST_AIRKICK;

    public static void build(){


        HumanoidArmature biped = Armatures.BIPED;

        ENHANCED_KATANA_UNSHEATHED_IDLE = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_idle", biped));
        ENHANCED_KATANA_UNSHEATHED_WALK = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_walk", biped));
        ENHANCED_KATANA_UNSHEATHED_RUN = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_run", biped));
        ENHANCED_KATANA_SHEATH = WOHAnimationUtils.createSheathAnimation("biped/skill/enhanced_katana/enhanced_katana_sheathe", false, 0.1f, 1.73f, null);
        ENHANCED_KATANA_SHEATH_ALT1 = WOHAnimationUtils.createSheathAnimation("biped/skill/enhanced_katana/enhanced_katana_sheathe_alt1", false,  0.1f, 2.5f, null);
        ENHANCED_KATANA_SHEATH_ALT2 = WOHAnimationUtils.createSheathAnimation("biped/skill/enhanced_katana/enhanced_katana_sheathe_alt2", false,  0.1f, 1.93f, null);

        ENHANCED_KATANA_UNSHEATH = WOHAnimationUtils.createLivingAnimation("biped/skill/enhanced_katana/enhanced_katana_unsheathe", false, 0.1f, 0.23f, -1, null);

        ENHANCED_KATANA_IDLE = WOHAnimationUtils.createLivingAnimation("biped/living/enhanced_katana/enhanced_katana_sheathed_idle", true, 0.1f, -1f, -1, null);
        ENHANCED_KATANA_WALK = WOHAnimationUtils.createLivingAnimation("biped/living/enhanced_katana/enhanced_katana_sheathed_walk", true, 0.1f, -1f, -1, null);



        ENHANCED_KATANA_UNSHEATHED_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_unsheathed_auto_1",
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

        ENHANCED_KATANA_UNSHEATHED_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_unsheathed_auto_2",
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

        ENHANCED_KATANA_UNSHEATHED_AUTO_3 =WOHAnimationUtils. createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_unsheathed_auto_3",
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
        ENHANCED_KATANA_UNSHEATHED_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_unsheathed_auto_4",
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
        ENHANCED_KATANA_UNSHEATHED_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_unsheathed_auto_5",
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

        ENHANCED_KATANA_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_auto_1",
                1,
                0.15F,
                1F,
                5F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.33F},
                new float[]{0.65f},
                new float[]{0.85f},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                MastersKatanaAnimations.ENHANCED_KATANA_SHEATH_ALT1,
                0.2f,
                -1
        );
        ENHANCED_KATANA_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_auto_2",
                1,
                0.15F,
                1F,
                4F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.42F},
                new float[]{0.75F},
                new float[]{1.33F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                MastersKatanaAnimations.ENHANCED_KATANA_SHEATH_ALT2,
                0.2f,
                -1
        );
        ENHANCED_KATANA_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_auto_3",
                1,
                0.15F,
                1F,
                4F,
                1.8F,
                new float[]{0.0F},
                new float[]{0.26F},
                new float[]{0.35F},
                new float[]{1.8F},
                new float[]{4.35F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                MastersKatanaAnimations.ENHANCED_KATANA_SHEATH_ALT1,
                0.2f,
                -1
        );
        ENHANCED_KATANA_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP,
                "biped/combat/enhanced_katana/enhanced_katana_auto_4",
                1,
                0.15F,
                1F,
                5.3F,
                2.4F,
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
                MastersKatanaAnimations.ENHANCED_KATANA_SHEATH,
                0.2f,
                -1
        );

        ENHANCED_KATANA_DASH = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/enhanced_katana/enhanced_katana_dash",
                1,
                0.05F,
                1F,
                6F,
                2F,
                new float[]{0.15F},
                new float[]{0.2F},
                new float[]{0.45F},
                new float[]{2.8F},
                new float[]{3F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI},
                new Joint[]{biped.toolR},
                StunType.LONG,
                0.1f,
                0.9f
        );
        ENHANCED_KATANA_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(
                "biped/combat/enhanced_katana/enhanced_katana_airslash",
                1,
                0.1F,
                1F,
                5.75F,
                2F,
                new float[]{0.15F},
                new float[]{0.62F},
                new float[]{0.75F},
                new float[]{1.75F},
                new float[]{3.20F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.TACHI},
                new float[]{0.05F, 3F},
                0.1f,
                0.9f
        );
        COMBAT_FIST_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/combat_fist/combat_fist_auto_1",
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

        COMBAT_FIST_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/combat_fist/combat_fist_auto_2",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.05F},
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
        COMBAT_FIST_DASH = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/combat_fist/combat_fist_dash",
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
                "biped/combat/combat_fist/combat_fist_airkick",
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
                new float[]{0.0F, 0.45F},
                -1,
                -1
        );
    }
}
