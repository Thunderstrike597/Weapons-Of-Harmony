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


public class OdachiAnimations {
    public static StaticAnimation ODACHI_HOLD;

    public static StaticAnimation ODACHI_AUTO_1;
    public static StaticAnimation ODACHI_AUTO_2;
    public static StaticAnimation ODACHI_AUTO_3;

    public static void build(){
        HumanoidArmature biped = Armatures.BIPED;

        ODACHI_HOLD = (new StaticAnimation(0.1f, true, "biped/living/odachi/odachi_hold", biped));

        ODACHI_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_1",
                1,
                0.25F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.4F},
                new float[]{0.5F},
                new float[]{0.88f},
                new float[]{1f},
                new SoundEvent[]{EpicFightSounds.WHOOSH_BIG.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        ODACHI_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_2",
                1,
                0.25F,
                0.6F,
                8F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.48F},
                new float[]{0.55F},
                new float[]{0.96f},
                new float[]{1.22f},
                new SoundEvent[]{EpicFightSounds.WHOOSH_BIG.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        ODACHI_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_3",
                1,
                0.25F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.54F},
                new float[]{0.65F},
                new float[]{1.1f},
                new float[]{1.35f},
                new SoundEvent[]{EpicFightSounds.WHOOSH_BIG.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
    }
}
