package net.kenji.woh.gameasset.animations;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.TimeStampManager;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.render.ShotogatanaRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.*;

public class BasisAttackAnimation extends BasicAttackAnimation {

    public static final Map<UUID, Boolean> isAttacking = new HashMap<>();
    private static final Map<UUID, Boolean> isAttackEnding = new HashMap<>();
    private static final Map<UUID, Boolean> isInAttack = new HashMap<>();

    private AnimationManager.AnimationAccessor<StaticAnimation> endAnimation;
    private boolean ignoreFallDamage = false;
    public WOHAnimationUtils.AttackAnimationType attackType = null;
    private boolean attackStart = false;
    private float slashAngle = -1f;
    private float movementEnd = -1;
    private boolean hasAttacked = false;
    public BasisAttackAnimation(WOHAnimationUtils.AttackAnimationType attackType, float convertTime, String path, AssetAccessor<? extends Armature> armature, AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,Phase... phases) {
        super(convertTime, path, armature, phases);
        this.endAnimation = endAnimation;
        this.attackType = attackType;
    }
    public BasisAttackAnimation(
            WOHAnimationUtils.AttackAnimationType attackType,
            float convertTime,
            AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> accessor,  // ADD THIS!
            AssetAccessor<? extends HumanoidArmature> armature,
            AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            float slashAngle,
            float movementEnd,
            Phase... phases
    ) {
        super(convertTime, accessor, armature,phases);  // Pass accessor to BasicAttackAnimation
        this.endAnimation = endAnimation;
        this.ignoreFallDamage = ignoreFallDamage;
        this.attackType = attackType;
        this.slashAngle = slashAngle;
        this.movementEnd = movementEnd;
    }
    public BasisAttackAnimation(
            WOHAnimationUtils.AttackAnimationType attackType,
            float convertTime,
            AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> accessor,  // ADD THIS!
            AssetAccessor<? extends HumanoidArmature> armature,
            AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            boolean ignoreFallDamage,
            Phase... phases
    ) {
        super(convertTime, accessor, armature,phases);  // Pass accessor to BasicAttackAnimation
        this.endAnimation = endAnimation;
        this.ignoreFallDamage = ignoreFallDamage;
        this.attackType = attackType;
    }

    private static final UUID SLOW_UUID =
            UUID.randomUUID();



    private static AttributeModifier slowModifier = new AttributeModifier(
            SLOW_UUID,
            "woh_animation_lock",
            -0.8D,
            AttributeModifier.Operation.MULTIPLY_TOTAL
    );

    public float getSlashAngle(){
        return slashAngle;
    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(this.accessor);
            if (animPlayer != null) {
                float time = animPlayer.getElapsedTime();
                UUID playerId = playerPatch.getOriginal().getUUID();
                AttributeInstance speed = playerPatch.getOriginal()
                        .getAttribute(Attributes.MOVEMENT_SPEED);
                Phase phase = phases[phases.length - 1];
                TimeStampManager.TimeStampData timeStampData = TimeStampManager.get(animation.get().getRealAnimation().get());
                if (time < phase.recovery || (movementEnd != -1 & time < movementEnd)) {
                    if (time > 0.05F) {
                        isInAttack.putIfAbsent(playerId, true);
                        isAttackEnding.remove(playerId);
                        if (playerPatch.getOriginal().level().isClientSide) {
                            Minecraft mc = Minecraft.getInstance();
                            mc.options.keyUp.setDown(false);
                            mc.options.keyDown.setDown(false);
                            mc.options.keyLeft.setDown(false);
                            mc.options.keyRight.setDown(false);
                        }
                    }
                } else if (time > phase.recovery) {
                    isInAttack.remove(playerId);
                    boolean attackEnding = isAttackEnding.getOrDefault(playerId, false);
                    if (!attackEnding) {
                        isAttackEnding.put(playerId, true);
                    }
                }
                float castTime = (phase.contact + phase.recovery) * 0.18f;

                if (time >= castTime && !hasAttacked) {
                    attackStart = true;
                    hasAttacked = true;
                }
                if (ignoreFallDamage)
                    playerPatch.getOriginal().resetFallDistance();
            }
            super.attackTick(entitypatch, animation);
        }
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ClientEvents {
        @SubscribeEvent
        public static void onPLayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            boolean attacking = isInAttack.getOrDefault(player.getUUID(), false);
            if(player.level().isClientSide){
                Minecraft mc = Minecraft.getInstance();
                if(attacking){
                    mc.options.keyUp.setDown(false);
                    mc.options.keyDown.setDown(false);
                    mc.options.keyLeft.setDown(false);
                    mc.options.keyRight.setDown(false);
                }
            }
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof ServerPlayerPatch playerPatch) {
                    if( playerPatch.getEntityState().attacking()) {
                        playerPatch.getEntityState().setState(EntityState.MOVEMENT_LOCKED, true);
                    }
                    CapabilityItem capItem = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                    UUID playerID = playerPatch.getOriginal().getUUID();
                    if (capItem instanceof WeaponCapability weaponCap) {
                        boolean isSheathed = ShotogatanaManager.sheathWeapon.getOrDefault(playerID, false);

                        if (!isSheathed) {
                            List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> autoAttackMotion = weaponCap.getAutoAttackMotion(playerPatch);
                            for(int i = 0; i < autoAttackMotion.size(); i++) {
                                AttackAnimation attackAnim = autoAttackMotion.get(i).get();
                                if(attackAnim instanceof BasisAttackAnimation basisAttackAnimation) {
                                    if(basisAttackAnimation.attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH) {
                                        int currentComboCounter = playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK).getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());
                                        if(currentComboCounter <= i) {
                                            BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION,
                                                    playerPatch,
                                                    playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK),
                                                    autoAttackMotion.get(i),
                                                    i + 1
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            boolean isInAttack = isAttacking.getOrDefault(playerID, false);

            if(isAttackEnding.getOrDefault(playerID, false)) {
                if (endAnimation != null && nextAnimation != endAnimation && !isInAttack) {
                    playerPatch.playAnimationSynchronized(endAnimation, 0.3f);
                }
            }
            isAttacking.remove(playerID);
            attackStart = false;
            hasAttacked = false;
        }
        super.end(entitypatch, nextAnimation, isEnd);
    }

    public boolean isAttackBegin(){
        if(attackStart){
            attackStart = false;
            return true;
        }
        return false;
    }


    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            isAttacking.put(playerID, true);
        }
        super.begin(entitypatch);
    }
}