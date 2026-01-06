package net.kenji.woh.api;

import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.animations.*;
import net.kenji.woh.network.SheathStatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.render.ShotogatanaRender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.UUID;

public class WOHAnimationUtils {

    public enum AttackAnimationType{
        BASIC_ATTACK,
        BASIC_ATTACK_JUMP,
        BASIC_ATTACK_SHEATH,
        DASH_ATTACK,
        DASH_ATTACK_JUMP,
        AIR_ATTACK
    }


    public static class ReusableEvents{
        static AnimationEvent.AnimationEventConsumer unSheathEvent = (
                (livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch instanceof PlayerPatch<?> playerPatch) {

                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();
                        ShotogatanaManager.sheathWeapon.put(playerId, false);
                        if (player.level().isClientSide) {
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), false));
                        }
                    }
                }
        );
        static AnimationEvent.AnimationEventConsumer sheathEvent = (
                (livingEntityPatch, staticAnimation, objects) -> {
                    if(livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();

                        if (player.level().isClientSide) {
                            player.playSound(
                                    EpicFightSounds.SWORD_IN.get(),
                                    1.0f,
                                    1.0f
                            );

                        }
                        ShotogatanaManager.sheathWeapon.remove(playerId);
                        ShotogatanaManager.sheathWeapon.put(playerId, true);
                        if (player.level().isClientSide) {
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), true));
                        }
                    }
                }
        );
    }



    public static StaticAnimation createLivingAnimation(

            String path,
            boolean isRepeat,
            float convertTime,
            float normalizedStart,
            float normalizedEnd,
            AnimationEvent.TimeStampedEvent[] extraEvents
    ) {
        StaticAnimation animation = (new StaticAnimation(convertTime, isRepeat, path, Armatures.BIPED));


        boolean stopEndEvent = false;
        boolean stopStartEvent = false;

        if(normalizedEnd <= -1){
            stopEndEvent = true;
        }
        if(normalizedStart <= -1){
            stopStartEvent = true;
        }

        // Register timestamp
        TimeStampManager.register(animation, normalizedStart, normalizedEnd);

        float absoluteStart = animation.getTotalTime() * normalizedStart;
        float absoluteEnd = animation.getTotalTime() * normalizedEnd;


        if(!stopEndEvent && !stopStartEvent) {
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopEndEvent && stopStartEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopStartEvent && stopEndEvent) {
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
            });
        }
        if(extraEvents != null){
            animation.addEvents(extraEvents);
        }
        return animation;
    }
    public static StaticAnimation createGuardAnimation(

            String path,
            float convertTime,
            AnimationEvent.TimeStampedEvent[] extraEvents
    ) {
        StaticAnimation animation = (new WohStaticAnimation(convertTime, false, path, Armatures.BIPED, LivingMotions.BLOCK));

        if(extraEvents != null){
            animation.addEvents(extraEvents);
        }
        return animation;
    }
    public static WohSheathAnimation createSheathAnimation(
            String path,
            boolean isRepeat,
            float convertTime,
            float absEnd,
            AnimationEvent.TimeStampedEvent[] extraEvents
    ) {
        WohSheathAnimation animation = (new WohSheathAnimation(convertTime, isRepeat, path, Armatures.BIPED, absEnd));

        animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                AnimationEvent.TimeStampedEvent.create(absEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
        });

        if(extraEvents != null){
            animation.addEvents(extraEvents);
        }
        return animation;
    }



    public static StaticAnimation createAttackAnimation(
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            SoundEvent[] hitSound,
            SoundEvent[] swingSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            float absStart,
            float absEnd
    ) {
        AttackAnimation animation = null;
        switch(type) {
            case BASIC_ATTACK, BASIC_ATTACK_SHEATH:
            animation = new WohAttackAnimation(type,
                    path, null, phaseCount, convertTime, attackSpeed, attackDamage, impact,
                    start, antic, contact, recovery, end,
                    hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, false
            );
            break;
            case BASIC_ATTACK_JUMP:
                animation = new WohAttackAnimation(type,
                        path, null, phaseCount, convertTime, attackSpeed, attackDamage, impact,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, true
                );
                break;
            case DASH_ATTACK:
                animation = new WohDashAttackAnimation(type,
                        path, phaseCount, convertTime, attackSpeed,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType
                );
                break;
            case DASH_ATTACK_JUMP:
                animation = new WohDashAttackAnimation(type,
                        path, phaseCount, convertTime, attackSpeed,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType
                );
                break;
        }
        boolean stopEndEvent = false;
        boolean stopStartEvent = false;

        if(absEnd <= -1){
            stopEndEvent = true;
        }
        if(absStart <= -1){
            stopStartEvent = true;
        }

        // Register timestamp
        TimeStampManager.register(animation, absStart, absEnd);

        // Add events at those exact timestamps

        if(!stopEndEvent && !stopStartEvent) {
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
                    AnimationEvent.TimeStampedEvent.create(absEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopEndEvent && stopStartEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopStartEvent && stopEndEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
            });

        }
        return animation;
    }
    public static StaticAnimation createAttackAnimation(
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            SoundEvent[] hitSound,
            SoundEvent[] swingSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            @Nullable StaticAnimation endAnimation,
            float normalizedStart,
            float normalizedEnd
    ) {
        AttackAnimation animation = null;
        switch(type) {
            case BASIC_ATTACK, BASIC_ATTACK_SHEATH:
                animation = new WohAttackAnimation(type,
                        path, endAnimation, phaseCount, convertTime, attackSpeed, attackDamage, impact,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, false
                );
                break;
            case BASIC_ATTACK_JUMP:
                animation = new WohAttackAnimation(type,
                        path, endAnimation, phaseCount, convertTime, attackSpeed, attackDamage, impact,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, true
                );
                break;
            case DASH_ATTACK:
                animation = new WohDashAttackAnimation(type,
                        path, phaseCount, convertTime, attackSpeed,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType
                );
                break;
        }
        boolean stopEndEvent = false;
        boolean stopStartEvent = false;

        if(normalizedEnd <= -1){
            stopEndEvent = true;
        }
        if(normalizedStart <= -1){
            stopStartEvent = true;
        }

        // Register timestamp
        TimeStampManager.register(animation, normalizedStart, normalizedEnd);

        float absoluteStart = animation.getTotalTime() * normalizedStart;
        float absoluteEnd = animation.getTotalTime() * normalizedEnd;

        // Add events at those exact timestamps

        if(!stopEndEvent && !stopStartEvent) {
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopEndEvent && stopStartEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopStartEvent && stopEndEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
            });

        }
        return animation;
    }
    public static StaticAnimation createAirAttackAnimation(
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            SoundEvent[] hitSound,
            SoundEvent[] swingSound,
            RegistryObject<HitParticleType>[] hitParticle,
            StunType stunType,
            Collider[] colliders,
            Joint[] colliderJoints,
            float[] airTime,
            float normalizedStart,
            float normalizedEnd
    ) {
        AttackAnimation animation = null;


        animation = new WohAirAttackAnimation(
                path, phaseCount, convertTime, attackSpeed, attackDamage, impact,
                start, antic, contact, recovery, end,
                hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, airTime
        );



        boolean stopEndEvent = false;
        boolean stopStartEvent = false;

        if(normalizedEnd <= -1){
            stopEndEvent = true;
        }
        if(normalizedStart <= -1){
            stopStartEvent = true;
        }

        // Register timestamp
        TimeStampManager.register(animation, normalizedStart, normalizedEnd);

        float absoluteStart = animation.getTotalTime() * normalizedStart;
        float absoluteEnd = animation.getTotalTime() * normalizedEnd;

        // Add events at those exact timestamps

        if(!stopEndEvent && !stopStartEvent) {
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopEndEvent && stopStartEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteEnd, ReusableEvents.sheathEvent, AnimationEvent.Side.CLIENT)
            });
        }
        if(!stopStartEvent && stopEndEvent){
            animation.addEvents(new AnimationEvent.TimeStampedEvent[]{
                    AnimationEvent.TimeStampedEvent.create(absoluteStart, ReusableEvents.unSheathEvent, AnimationEvent.Side.CLIENT),
            });

        }
        return animation;
    }

}
