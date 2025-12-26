package net.kenji.woh.gameasset.animations;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.render.EnhancedKatanaRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BasisAttackAnimation extends BasicAttackAnimation {

    public static final Map<UUID, Boolean> isAttacking = new HashMap<>();
    private static final Map<UUID, Boolean> isAttackEnding = new HashMap<>();

    private StaticAnimation endAnimation;
    private boolean ignoreFallDamage = false;

    public WOHAnimationUtils.AttackAnimationType attackType = null;

    public BasisAttackAnimation(WOHAnimationUtils.AttackAnimationType attackType, float convertTime, String path, Armature armature, StaticAnimation endAnimation,Phase... phases) {
        super(convertTime, path, armature, phases);
        this.endAnimation = endAnimation;
        this.attackType = attackType;
    }
    public BasisAttackAnimation(WOHAnimationUtils.AttackAnimationType attackType, float convertTime, String path, Armature armature, StaticAnimation endAnimation, boolean ignoreFallDamage,Phase... phases) {
        super(convertTime, path, armature, phases);
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

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            float time = playerPatch.getAnimator().getPlayerFor(this).getElapsedTime();
            UUID playerId = playerPatch.getOriginal().getUUID();
            for(Phase phase : this.phases) {
                AttributeInstance speed = playerPatch.getOriginal()
                        .getAttribute(Attributes.MOVEMENT_SPEED);
                if (time < phase.recovery){
                    if(playerPatch.getOriginal().level().isClientSide){
                        Minecraft mc = Minecraft.getInstance();
                        mc.options.keyUp.setDown(false);
                        mc.options.keyDown.setDown(false);
                        mc.options.keyLeft.setDown(false);
                        mc.options.keyRight.setDown(false);
                    }
                }
                else{
                    boolean attackEnding = isAttackEnding.getOrDefault(playerId, false);
                    if(!attackEnding) {
                        isAttackEnding.put(playerId, true);
                    }
                }
            }
            if(ignoreFallDamage)
                playerPatch.getOriginal().resetFallDistance();
        }
        super.attackTick(entitypatch, animation);
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public class ClientEvents {
        @SubscribeEvent
        public static void onPLayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            boolean isEndingAttack = isAttackEnding.getOrDefault(player.getUUID(), false);
            if(player.level().isClientSide && isEndingAttack) {
                LocalPlayer localPlayer = (LocalPlayer) player;

                localPlayer.input.forwardImpulse = 0;
            }
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof ServerPlayerPatch playerPatch) {
                    CapabilityItem capItem = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                    UUID playerID = playerPatch.getOriginal().getUUID();
                    if (capItem instanceof WeaponCapability weaponCap) {
                        boolean isSheathed = EnhancedKatanaRender.sheathWeapon.getOrDefault(playerID, false);
                        if (!isSheathed) {
                            List<AnimationProvider<?>> autoAttackMotion = weaponCap.getAutoAttckMotion(playerPatch);
                            for(int i = 0; i < autoAttackMotion.size(); i++) {
                                if(autoAttackMotion.get(i) instanceof BasisAttackAnimation basisAttackAnimation) {
                                    if(basisAttackAnimation.attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_SHEATH) {
                                        int currentComboCounter = playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK).getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());
                                       if(currentComboCounter <= i) {
                                           BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION,
                                                   playerPatch,
                                                   playerPatch.getSkill(EpicFightSkills.BASIC_ATTACK),
                                                   autoAttackMotion.get(i).get(),
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
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            boolean isInAttack = isAttacking.getOrDefault(playerID, false);

            if (endAnimation != null && nextAnimation != endAnimation && !isInAttack) {
                playerPatch.playAnimationSynchronized(endAnimation, 0.3f);
            }
            isAttacking.remove(playerID);
        }
        super.end(entitypatch, nextAnimation, isEnd);
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