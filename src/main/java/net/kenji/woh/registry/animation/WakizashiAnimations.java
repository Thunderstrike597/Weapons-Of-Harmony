package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;


public class WakizashiAnimations {
    public static StaticAnimation WAKIZASHI_HOLD;
    public static StaticAnimation WAKIZASHI_DUAL_HOLD;

    public static StaticAnimation WAKIZASHI_AUTO_1;
    public static StaticAnimation WAKIZASHI_AUTO_2;
    public static StaticAnimation WAKIZASHI_AUTO_3;

    public static StaticAnimation WAKIZASHI_DUAL_AUTO_1;
    public static StaticAnimation WAKIZASHI_DUAL_AUTO_2;
    public static StaticAnimation WAKIZASHI_DUAL_AUTO_3;
    public static StaticAnimation WAKIZASHI_DUAL_AUTO_4;
    public static StaticAnimation WAKIZASHI_DUAL_AUTO_5;
    public static StaticAnimation WAKIZASHI_DUAL_AUTO_6;

    public static StaticAnimation WAKIZASHI_GUARD;

    public static StaticAnimation WAKIZASHI_DUAL_GUARD;

    public static void build(){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        WAKIZASHI_HOLD = (new StaticAnimation(0.1f, true, "biped/living/wakizashi/wakizashi_hold", biped));
        WAKIZASHI_DUAL_HOLD = (new StaticAnimation(0.1f, true, "biped/living/wakizashi/wakizashi_dual_hold", biped));

        WAKIZASHI_DUAL_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/wakizashi/wakizashi_dual_guard", 0.25F, null);
        WAKIZASHI_GUARD = WOHAnimationUtils.createGuardAnimation("biped/skill/wakizashi/wakizashi_guard", 0.25F, null);


        WAKIZASHI_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_auto_1",
                1,
                0.1F,
                1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.3F},
                new float[]{0.5F},
                new float[]{0.8f},
                new float[]{1f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        WAKIZASHI_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_auto_2",
                1,
                0.1F,
                1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.3F},
                new float[]{0.6F},
                new float[]{0.8f},
                new float[]{1.2f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_auto_3",
                1,
                0.1F,
                1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.3F},
                new float[]{0.45F},
                new float[]{0.8f},
                new float[]{1.1f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_1",
                1,
                0.1F,
                1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.35F},
                new float[]{0.62f},
                new float[]{1.45f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_2",
                1,
                0.1F,
                1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.32F},
                new float[]{0.42F},
                new float[]{0.78f},
                new float[]{1.30f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_3",
                2,
                0.1F,
                0.85F,
                3F,
                0.45F,
                new float[]{0.0F, 0.65F},
                new float[]{0.4F, 0.7F},
                new float[]{0.60F, 0.82F},
                new float[]{0.62F, 1.07F},
                new float[]{0.63F, 1.35F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI,ColliderPreset.TACHI},
                new Joint[]{biped.get().toolR, biped.get().toolL},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_4 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_4",
                3,
                0.1F,
                0.6F,
                3F,
                0.45F,
                new float[]{0.0F, 0.4F, 0.55F},
                new float[]{0.2F, 0.42F, 0.58F},
                new float[]{0.32F, 0.45F, 0.65F},
                new float[]{1.26F, 1.26F, 1.26F},
                new float[]{1.43F, 1.43F, 1.43F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_ROD.get(), EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH_ROD.get()},
                new SoundEvent[]{EpicFightSounds.BLUNT_HIT.get(), EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLUNT_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.SWORD,ColliderPreset.TACHI,ColliderPreset.SWORD},
                new Joint[]{biped.get().legR, biped.get().toolR, biped.get().legL},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_5 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_5",
                2,
                0.1F,
                0.75F,
                3F,
                0.45F,
                new float[]{0.0F, 0.55F},
                new float[]{0.18F, 0.6F},
                new float[]{0.4F, 0.85F},
                new float[]{1.25f, 1.25f},
                new float[]{1.8F, 1.8F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI,ColliderPreset.TACHI},
                new Joint[]{biped.get().toolL, biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        WAKIZASHI_DUAL_AUTO_6 = WOHAnimationUtils.createAttackAnimation(
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/wakizashi/wakizashi_dual_auto_6",
                2,
                0.1F,
                0.75F,
                3F,
                0.45F,
                new float[]{0.0F, 0.48F},
                new float[]{0.1F, 0.52F},
                new float[]{0.18F, 0.65F},
                new float[]{2.0F, 2.0F},
                new float[]{2.05F, 2.05F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.TACHI,ColliderPreset.TACHI},
                new Joint[]{biped.get().toolL, biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
    }
}
