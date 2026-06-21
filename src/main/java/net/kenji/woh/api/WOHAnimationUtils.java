package net.kenji.woh.api;

import net.corruptdog.cdm.gameasset.CorruptSound;
import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.api.animation_types.ShotogatanaStaticAnimation;
import net.kenji.woh.api.animation_types.TessenThrowAttackAnimation;
import net.kenji.woh.api.animation_types.WohAttackAnimation;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.api.manager.TenraiManager;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.gameasset.animation_types.*;
import net.kenji.woh.network.ClientShotogatanaSkillPacket;
import net.kenji.woh.network.SheathStatePacket;
import net.kenji.woh.network.SplitStatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class WOHAnimationUtils {
    public static final List<Supplier<StaticAnimation>> DEFERRED_SETUP = new ArrayList<>();

    public enum AttackAnimationType{
        BASIC_ATTACK,
        BASIC_ATTACK_JUMP,
        BASIC_ATTACK_SHEATH,
        DASH_ATTACK,
        DASH_ATTACK_JUMP,
        AIR_ATTACK
    }


    public static void stopMovementEvent(LivingEntityPatch<?> entityPatch){
        ((LivingEntity) entityPatch.getOriginal()).getMainHandItem().getOrCreateTag().putBoolean("stopMovement", true);
    }
    public static void regainMovementEvent(LivingEntityPatch<?> entityPatch){
        ((LivingEntity) entityPatch.getOriginal()).getMainHandItem().getOrCreateTag().putBoolean("stopMovement", false);
    }
    public static void katanaInEvent(LivingEntityPatch<?> entityPatch) {
        ShotogatanaManager.setWeaponSheathed(entityPatch.getOriginal(), true);
        if (entityPatch instanceof ServerPlayerPatch serverPlayer) {
            WohPacketHandler.sendToPlayer(new ClientShotogatanaSkillPacket(true), serverPlayer.getOriginal());
            BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.TIME_EXPIRED, serverPlayer, serverPlayer.getSkill(SkillSlots.BASIC_ATTACK), Animations.EMPTY_ANIMATION.getAccessor(), 0);
        }
    }
    public static void katanaOutEvent(LivingEntityPatch<?> entityPatch){
        ShotogatanaManager.setWeaponSheathed(entityPatch.getOriginal(), false);
        if(entityPatch.getOriginal() instanceof ServerPlayer serverPlayer)
            WohPacketHandler.sendToPlayer(new ClientShotogatanaSkillPacket(false), serverPlayer);
    }
    public static AnimationEvent.E0 KATANA_IN = ((entitypatch, animation, params) -> {
        katanaInEvent(entitypatch);
    });
    public static AnimationEvent.E0 KATANA_OUT = ((entitypatch, animation, params) -> {
        katanaOutEvent(entitypatch);
    });

    public class ReusableEvents {

        public static final AnimationEvent.E0 UNSHEATH_E0 =
                (entityPatch, animation, params) -> {
                    if(entityPatch instanceof PlayerPatch<?> playerPatch) {

                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();
                        if (player.level().isClientSide) {
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), false));
                        }
                        katanaOutEvent(entityPatch);
                    }
        };

        public static final AnimationEvent.E0 SHEATH_E0 =
                (entityPatch, animation, params) -> {
                    if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();

                        if (player.level().isClientSide) {
                            player.playSound(
                                    CorruptSound.YAMATO_IN.get(),
                                    1.0f,
                                    1.0f
                            );
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), true));
                        }
                        katanaInEvent(entityPatch);
                    }
                };
        public static final AnimationEvent.E0 SPLIT_E0 =
                (entityPatch, animation, params) -> {
                    if(entityPatch instanceof PlayerPatch<?> playerPatch) {

                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();
                        CompoundTag tag = playerPatch.getOriginal().getMainHandItem().getOrCreateTag();

                        TenraiManager.setWeaponSplit(player, true);

                        playerPatch.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
                    }
                };

        public static final AnimationEvent.E0 UNSPLIT_E0 =
                (entityPatch, animation, params) -> {
                    if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();

                        CompoundTag tag = playerPatch.getOriginal().getMainHandItem().getOrCreateTag();
                        TenraiManager.setWeaponSplit(player, false);
                        if(playerPatch instanceof ServerPlayerPatch serverPlayerPatch){
                            BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.TIME_EXPIRED, serverPlayerPatch, serverPlayerPatch.getSkill(SkillSlots.BASIC_ATTACK), Animations.EMPTY_ANIMATION.getAccessor(), 0);
                        }
                        playerPatch.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
                    }
                };
    }
    public static MoveCoordFunctions.MoveCoordSetter scaledRawCoord(float multiplier) {
        return (self, entitypatch, transformSheet) -> {
            // Copy the raw animation coord first (same as RAW_COORD)
            TransformSheet scaled = self.getCoord().copyAll();

            // Scale every keyframe's Z translation (forward movement in Epic Fight's coord system)
            // extendsZCoord scales frames 0→endFrame, then offsets the rest to avoid a gap
            int endFrame = self.getCoord().getKeyframes().length - 1;
            scaled = scaled.extendsZCoord(multiplier, 0, endFrame);

            transformSheet.readFrom(scaled);
        };
    }


    public static AnimationManager.AnimationAccessor<StaticAnimation> createShotogatanaLivingAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            boolean isRepeat,
            float convertTime,
            float absStart,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation =
                builder.nextAccessor(path, accessor -> new ShotogatanaStaticAnimation(isRepeat, accessor, Armatures.BIPED));

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = animation.get();

            boolean stopEndEvent = absEnd <= -1;
            boolean stopStartEvent = absStart <= -1;

            // Register timestamp
            TimeStampManager.register(anim, absStart, absEnd);

            // Add events at those exact timestamps
            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
                });
            }

            // Add extra events if provided
            if (extraEvents != null && extraEvents.length > 0) {
                anim.addEvents(extraEvents);
            }

            return anim;
        };

        // Add to deferred list instead of calling immediately
        DEFERRED_SETUP.add(setupSupplier);

        // Return the animation reference (will be populated later)
        return animation;
    }

    public static AnimationManager.AnimationAccessor<StaticAnimation> createLivingAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            boolean isRepeat,
            float convertTime,
            float absStart,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation =
                builder.nextAccessor(path, accessor -> new StaticAnimation(isRepeat, accessor, Armatures.BIPED));

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = animation.get();

            boolean stopEndEvent = absEnd <= -1;
            boolean stopStartEvent = absStart <= -1;

            // Register timestamp
            TimeStampManager.register(anim, absStart, absEnd);

            // Add events at those exact timestamps
            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
                });
            }

            // Add extra events if provided
            if (extraEvents != null && extraEvents.length > 0) {
                anim.addEvents(extraEvents);
            }

            return anim;
        };

        // Add to deferred list instead of calling immediately
        DEFERRED_SETUP.add(setupSupplier);

        // Return the animation reference (will be populated later)
        return animation;
    }
    public static StaticAnimation createGuardAnimation(

            String path,
            float convertTime,
            AnimationEvent<?,?>[] extraEvents
    ) {
        StaticAnimation animation = (new WohStaticAnimation(convertTime, false, path, Armatures.BIPED, LivingMotions.BLOCK));

        if(extraEvents != null){
            animation.addEvents(extraEvents);
        }
        return animation;
    }
    public static AnimationManager.AnimationAccessor<StaticAnimation> createSheathAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            float convertTime,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation = builder.nextAccessor(path, accessor -> new WohSheathAnimation(convertTime, accessor, Armatures.BIPED, absEnd));

        AnimationManager.AnimationAccessor<? extends StaticAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = finalAnimation.get();

            anim.addEvents(new AnimationEvent[]{
                    AnimationEvent.InTimeEvent.create(
                            absEnd,
                            ReusableEvents.SHEATH_E0,
                            AnimationEvent.Side.BOTH
                    )
            });

            if(extraEvents != null){
                anim.addEvents(extraEvents);
            }
            return anim;
        };

        // ADD TO DEFERRED LIST - DON'T CALL IT!
        DEFERRED_SETUP.add(setupSupplier);

        // Return the accessor reference
        return animation;
    }
    public static AnimationManager.AnimationAccessor<StaticAnimation> createSplitAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            float convertTime,
            float absStart,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation = builder.nextAccessor(path, accessor -> new WohSheathAnimation(convertTime, accessor, Armatures.BIPED, absEnd));

        AnimationManager.AnimationAccessor<? extends StaticAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = finalAnimation.get();

            if(absStart != -1) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(
                                absStart,
                                ReusableEvents.SPLIT_E0,
                                AnimationEvent.Side.BOTH
                        )
                });
            }if(absEnd != -1){
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(
                                absEnd,
                                ReusableEvents.UNSPLIT_E0,
                                AnimationEvent.Side.BOTH
                        )
                });
            }

            if(extraEvents != null){
                anim.addEvents(extraEvents);
            }
            return anim;
        };

        // ADD TO DEFERRED LIST - DON'T CALL IT!
        DEFERRED_SETUP.add(setupSupplier);

        // Return the accessor reference
        return animation;
    }
    public static AnimationManager.AnimationAccessor<AttackAnimation> createTessenThrowAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType attackType,
            String path,
            int phaseCount,
            float convertTime,
            float start,
            float antic,
            float contact,
            float recovery,
            float end,
            float attackSpeed,
            Supplier<SoundEvent> swingSound,
            Supplier<SoundEvent> hitSound,
            RegistryObject<HitParticleType> hitParticle,
            Collider colliders,
            AttackHand throwType,
            StunType stunType,
            float throwStart,
            float ThrowEnd,
            boolean useMovement
    ) {
        AnimationManager.AnimationAccessor<AttackAnimation> animation = null;

        animation = builder.nextAccessor(path, accessor -> new TessenThrowAttackAnimation(convertTime, path,
               accessor, attackSpeed, throwStart, ThrowEnd, phaseCount,
                start, antic, contact, recovery, end,
                swingSound, hitSound, hitParticle, stunType, colliders, throwType, attackType == AttackAnimationType.BASIC_ATTACK_JUMP || attackType == AttackAnimationType.DASH_ATTACK_JUMP, useMovement
        ));
        return animation;
    }



    public static AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> createShotogatanaAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType type,
            String path,
            int phaseCount,
            float speed,
            float convertTime,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            float unsheatheTime,
            float sheathTime,
            float movementMultiplier
    ) {
        AnimationManager.AnimationAccessor<BasicAttackAnimation> animation = null;
        switch(type) {
            case BASIC_ATTACK, BASIC_ATTACK_SHEATH, DASH_ATTACK:
                animation = builder.nextAccessor(path, accessor -> new ShotogatanaAttackAnimation(convertTime, speed,
                        accessor, unsheatheTime, sheathTime, phaseCount,
                        start, antic, contact, recovery, end,
                        swingSound, hitSound, hitParticle, stunType, colliders, colliderJoints, false, movementMultiplier
                ));
                break;
            case BASIC_ATTACK_JUMP:
                animation = builder.nextAccessor(path, accessor -> new ShotogatanaAttackAnimation(convertTime, speed,
                        accessor, unsheatheTime, sheathTime, phaseCount,
                        start, antic, contact, recovery, end,
                        swingSound, hitSound, hitParticle, stunType, colliders, colliderJoints, true, movementMultiplier
                ));
                break;
        }
        AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> finalAnimation = animation;

        boolean skipUnsheatheEvent = unsheatheTime <= -1;
        boolean skipSheatheEvent = sheathTime <= -1;


        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            if(!skipUnsheatheEvent && !skipSheatheEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(unsheatheTime, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(sheathTime, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            }
            if(skipUnsheatheEvent && !skipSheatheEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(sheathTime, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            }
            if(!skipUnsheatheEvent && skipSheatheEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(unsheatheTime, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                });
            }
            return anim;
        };
        DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }


    public static void initializeAnimations() {
        DEFERRED_SETUP.forEach(Supplier::get);
        DEFERRED_SETUP.clear();
    }
}
