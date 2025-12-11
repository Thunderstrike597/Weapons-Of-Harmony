package net.kenji.woh.registry.animation;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.TimeStampManager;
import net.kenji.woh.gameasset.animations.EnhancedKatanaPreset;
import net.kenji.woh.render.EnhancedKatanaRender;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;

import java.util.UUID;


@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WOHAnimations {
    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(WeaponsOfHarmony.MODID, WOHAnimations::build);
    }
    public static StaticAnimation ENHANCED_KATANA_IDLE;
    public static StaticAnimation ENHANCED_KATANA_WALK;
    public static StaticAnimation ENHANCED_KATANA_RUN;
    public static StaticAnimation ENHANCED_KATANA_SHEATH;
    public static StaticAnimation ENHANCED_KATANA_UNSHEATH;
    public static StaticAnimation ENHANCED_KATANA_SHEATHED_IDLE;
    public static StaticAnimation ENHANCED_KATANA_SHEATHED_WALK;

    public static StaticAnimation ENHANCED_KATANA_AUTO_1;
    public static StaticAnimation ENHANCED_KATANA_AUTO_2;
    public static StaticAnimation ENHANCED_KATANA_AUTO_3;
    public static StaticAnimation ENHANCED_KATANA_AUTO_4;
    public static StaticAnimation ENHANCED_KATANA_AUTO_5;


    public static StaticAnimation ENHANCED_KATANA_SHEATHED_AUTO_1;
    public static StaticAnimation ENHANCED_KATANA_SHEATHED_AUTO_2;
    public static StaticAnimation ENHANCED_KATANA_SHEATHED_AUTO_3;

    public static StaticAnimation ENHANCED_KATANA_SHEATHED_DASH;

    private static StaticAnimation createSheathedAttackAnimation(
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            SoundEvent[] hitSound,
            SoundEvent[] swingSound,
            RegistryObject<HitParticleType>[] hitParticle,
            float normalizedStart,
            float normalizedEnd
    ) {
        EnhancedKatanaPreset animation = new EnhancedKatanaPreset(
                path, phaseCount, convertTime, attackSpeed,
                start, antic, contact, recovery, end,
                hitSound, swingSound, hitParticle
        );

        // Register timestamp
        TimeStampManager.register(animation, normalizedStart, normalizedEnd);

        float absoluteStart = animation.getTotalTime() * normalizedStart;
        float absoluteEnd = animation.getTotalTime() * normalizedEnd;

        // Add events at those exact timestamps
        animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                AnimationEvent.TimeStampedEvent.create(absoluteStart, sheathAttackBeginEvent, AnimationEvent.Side.CLIENT),
                AnimationEvent.TimeStampedEvent.create(absoluteEnd, sheathAttackEndEvent, AnimationEvent.Side.CLIENT)
        });

        return animation;
    }

    static AnimationEvent.AnimationEventConsumer sheathSoundEvent = (
            (livingEntityPatch, staticAnimation, objects) -> {

                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.playSound(
                            EpicFightSounds.SWORD_IN.get(),
                            1.0f,
                            1.0f
                    );
            });
    static AnimationEvent.AnimationEventConsumer sheathAttackBeginEvent = (
            (livingEntityPatch, staticAnimation, objects) -> {
                Player player = (Player)livingEntityPatch.getOriginal();
                UUID playerId = player.getUUID();
                Log.info("IS ATTACKING");
                EnhancedKatanaRender.attacking.put(playerId, true);
            }
    );
    static AnimationEvent.AnimationEventConsumer sheathAttackEndEvent = (
            (livingEntityPatch, staticAnimation, objects) -> {
                Player player = (Player)livingEntityPatch.getOriginal();
                UUID playerId = player.getUUID();
                EnhancedKatanaRender.attacking.remove(playerId);
            }
    );

    private static void build(){
        HumanoidArmature biped = Armatures.BIPED;

        ENHANCED_KATANA_IDLE = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_idle", biped));
        ENHANCED_KATANA_WALK = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_walk", biped));
        ENHANCED_KATANA_RUN = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_run", biped));
        ENHANCED_KATANA_SHEATH = (new StaticAnimation(0.1f, false, "biped/skill/enhanced_katana/enhanced_katana_sheathe", biped).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(1.225F,sheathSoundEvent, AnimationEvent.Side.CLIENT)}));
        TimeStampManager.register(
                ENHANCED_KATANA_SHEATH,
                0.2f,   // normalized start
                0.75f    // normalized end
        );
        ENHANCED_KATANA_UNSHEATH = (new StaticAnimation(0.1f, false, "biped/skill/enhanced_katana/enhanced_katana_unsheathe", biped));
        TimeStampManager.register(
                ENHANCED_KATANA_UNSHEATH,
                0.23f,   // normalized start
                0.65f    // normalized end
        );
        ENHANCED_KATANA_SHEATHED_IDLE = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_sheathed_idle", biped));
        ENHANCED_KATANA_SHEATHED_WALK = (new StaticAnimation(0.1f, true, "biped/living/enhanced_katana/enhanced_katana_sheathed_walk", biped));



        ENHANCED_KATANA_AUTO_1 = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_auto_1",
                1,
                0.1F,
                1F,
                new float[]{0.0F},
                new float[]{0.75F},
                new float[]{1.1F},
                new float[]{1.65f},
                new float[]{1.8f},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
        new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
               new RegistryObject[]{EpicFightParticles.HIT_BLADE}

        ));

        ENHANCED_KATANA_AUTO_2 = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_auto_2",
                2,
                0.1F,
                1.0F,
                new float[]{0.0F, 1F},
                new float[]{0.35F, 1.1F},
                new float[]{0.7F, 1.3F},
                new float[]{0.8f, 1.7F},
                new float[]{0.9f, 2F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE}

        ));

        ENHANCED_KATANA_AUTO_3 = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_auto_3",
                2,
                0.1F,
                1.0F,
                new float[]{0.0F, 1F},
                new float[]{0.5F, 1.1F},
                new float[]{0.8F, 1.2F},
                new float[]{2.5F, 2.5F},
                new float[]{1F, 2.2F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE}

        ));
        ENHANCED_KATANA_AUTO_4 = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_auto_4",
                1,
                0.1F,
                1.0F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE}

        ));
        ENHANCED_KATANA_AUTO_5 = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_auto_5",
                1,
                0.1F,
                1.0F,
                new float[]{0.0F},
                new float[]{0.5F},
                new float[]{0.9F},
                new float[]{1.15F},
                new float[]{1.25F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE}

        ));

        ENHANCED_KATANA_SHEATHED_AUTO_1 = createSheathedAttackAnimation(
                "biped/combat/enhanced_katana/enhanced_katana_sheathed_auto_1",
                1,
                0.45F,
                1F,
                new float[]{0.0F},
                new float[]{0.55F},
                new float[]{0.65F},
                new float[]{0.8F},
                new float[]{1.0F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                0.15f,  // start timestamp
                0.8f    // end timestamp
        );

        ENHANCED_KATANA_SHEATHED_AUTO_2 = createSheathedAttackAnimation(
                "biped/combat/enhanced_katana/enhanced_katana_sheathed_auto_2",
                1,
                0.05F,
                1F,
                new float[]{0.15F},
                new float[]{0.75F},
                new float[]{0.9F},
                new float[]{1.4F},
                new float[]{1.5F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                0.0f,
                0.9f
        );
        ENHANCED_KATANA_SHEATHED_AUTO_3 = createSheathedAttackAnimation(
                "biped/combat/enhanced_katana/enhanced_katana_sheathed_auto_3",
                2,
                0.15F,
                1.0F,
                new float[]{0.0F, 0.5F},
                new float[]{0.2F, 0.6F},
                new float[]{0.1F, 0.6F},
                new float[]{0.2F, 2.75F},
                new float[]{0.3F, 3.65F},
                new SoundEvent[]{EpicFightSounds.WHOOSH.get(), EpicFightSounds.WHOOSH.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get(), EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                0.0f,
                0.9f
        );


        ENHANCED_KATANA_SHEATHED_DASH = (new EnhancedKatanaPreset(
                "biped/combat/enhanced_katana/enhanced_katana_sheathed_dash",
                1,
                0.05F,
                1F,
                new float[]{0.15F},
                new float[]{0.2F},
                new float[]{0.45F},
                new float[]{2.8F},
                new float[]{3F},
                new SoundEvent[]{EpicFightSounds.WHOOSH_SHARP.get()},
                new SoundEvent[]{EpicFightSounds.BLADE_HIT.get()},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE}
        ));
        TimeStampManager.register(
                ENHANCED_KATANA_SHEATHED_DASH,
                0.1f,   // normalized start
                0.9f    // normalized end
        );
    }
}
