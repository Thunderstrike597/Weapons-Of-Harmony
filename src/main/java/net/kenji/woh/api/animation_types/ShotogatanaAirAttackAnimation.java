package net.kenji.woh.api.animation_types;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShotogatanaAirAttackAnimation extends DashAttackAnimation {

    public final float unsheatheTime;
    public final float sheathTime;
    private final boolean ignoreFallDamage;
    private static Map<UUID, Boolean> queFallReset = new HashMap<>();

    public ShotogatanaAirAttackAnimation(float convertTime, String path, float unsheatheTime, float sheathTime, int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, float[] airTime, boolean ignoreFallDamage) {
        super(convertTime, path, Armatures.BIPED, buildPhases(phaseCount, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints));
        this.unsheatheTime = unsheatheTime;
        this.sheathTime = sheathTime;
        this.ignoreFallDamage = ignoreFallDamage;

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(airTime));


    }

    @Override
    protected boolean shouldMove(float currentTime) {
        return true;
    }

    private static Phase[] buildPhases(int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] colliderJoints) {
        Phase[] phases = new Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            phases[i] = new Phase(
                    start[i],
                    antic[i],
                    contact[i],
                    recovery[i],
                    end[i],
                    InteractionHand.MAIN_HAND,
                    colliderJoints[i],
                    colliders[i]
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);

        }


        return phases;
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        float time = entitypatch.getAnimator().getPlayerFor(this).getElapsedTime();


    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (ignoreFallDamage)
                queFallReset.put(playerPatch.getOriginal().getUUID(), true);
        }
        super.attackTick(entitypatch, animation);
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ForgeEvents {
        @SubscribeEvent
        public static void onPLayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            boolean resetQued = ShotogatanaAirAttackAnimation.queFallReset.getOrDefault(player.getUUID(), false);

            if(resetQued) {
                player.resetFallDistance();
            }
            if(!player.getBlockStateOn().isAir()){
                queFallReset.remove(player.getUUID());
            }
        }
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.put(playerPatch.getOriginal().getUUID(), true);
        }
        super.begin(entitypatch);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.remove(playerPatch.getOriginal().getUUID());
        }
        super.end(entitypatch, nextAnimation, isEnd);
    }
}
