package net.kenji.woh.gameasset;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.animation.MastersKatanaAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnhancedKatanaSkillInnate extends WeaponInnateSkill {

    public EnhancedKatanaSkillInnate(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.PARRYING.getSkillTexture();
    }

    public ItemStack lastMainHandItem = ItemStack.EMPTY;

    boolean isActivated;
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        private static final Map<UUID, Boolean> didFirstAttack = new HashMap<>();

        private static final Map<UUID, BasicAttackAnimation> storedOriginalAttacks = new HashMap<>();


        /*@SubscribeEvent
        public static void onServerPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (player.level().isClientSide) return; // Check early

            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof PlayerPatch<?> patch) {
                    SkillContainer container = patch.getSkill(WOHSkills.SHEATH_STANCE);

                    if (container != null && container.isActivated()) { // Only when skill is active
                        CapabilityItem itemCapability = patch.getAdvancedHoldingItemCapability(InteractionHand.MAIN_HAND);

                        if (itemCapability instanceof WeaponCapability weaponCap) {
                            List<AnimationProvider<?>> autoAttacks = weaponCap.getAutoAttckMotion(patch);

                            UUID playerId = player.getUUID();
                            boolean playerDidFirstAttack = didFirstAttack.getOrDefault(playerId, false);

                            AnimationPlayer attackPlayer = patch.getServerAnimator().animationPlayer;
                            DynamicAnimation currentAnim = attackPlayer.getAnimation();

                            // Check if first attack is playing
                            DynamicAnimation firstAttack = autoAttacks.get(0).get().getRealAnimation();
                            if (currentAnim == firstAttack) {

                                didFirstAttack.put(playerId, true);
                            }

                            // Block further attacks after first completes
                            if (playerDidFirstAttack && !patch.isLastAttackSuccess()) {
                                patch.getEntityState().setState(EntityState.CAN_BASIC_ATTACK, false);
                                if (patch.getOriginal().level().isClientSide) {
                                    ((LocalPlayer) patch.getOriginal()).input.forwardImpulse = 0.0F;
                                    ((LocalPlayer) patch.getOriginal()).input.leftImpulse = 0.0F;
                                    ((LocalPlayer) patch.getOriginal()).input.up = false;
                                }
                            }

                            // Reset when no longer attacking
                            if (playerDidFirstAttack) {
                                boolean isAttacking = autoAttacks.stream()
                                        .anyMatch(attack -> currentAnim == attack.get().getRealAnimation());

                                if (!isAttacking) {
                                    didFirstAttack.remove(playerId);
                                }
                            }
                        }
                    } else {
                        // Skill not active, clean up
                        didFirstAttack.remove(player.getUUID());
                    }
                }
            });
        }

        @SubscribeEvent
        public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (!player.level().isClientSide) return; // Check early

            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof PlayerPatch<?> patch) {
                    UUID playerId = player.getUUID();
                    boolean playerDidFirstAttack = didFirstAttack.getOrDefault(playerId, false);

                    if (playerDidFirstAttack && !patch.isLastAttackSuccess()) {
                         Minecraft mc = Minecraft.getInstance();
                         mc.options.keyUp.setDown(false);
                         mc.options.keyDown.setDown(false);
                        mc.options.keyLeft.setDown(false);
                        mc.options.keyRight.setDown(false);
                    }
                }
            });
        }*/
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return true;
    }

    @Override
    public void updateContainer(SkillContainer container) {


    }


    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        if (executer.getSkill(this).isActivated()) {
            this.cancelOnServer(executer, args);
        } else {
            super.executeOnServer(executer, args);
            executer.getSkill(this).activate();
            executer.modifyLivingMotionByCurrentItem(false);
            executer.playAnimationSynchronized(MastersKatanaAnimations.ENHANCED_KATANA_UNSHEATH, 0.1F);
        }

    }
    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.getSkill(this).deactivate();
        super.cancelOnServer(executer, args);
        executer.modifyLivingMotionByCurrentItem(false);
        executer.playAnimationSynchronized(MastersKatanaAnimations.ENHANCED_KATANA_SHEATH, 0.1F);
        Log.info("Is Activated: " + executer.getSkill(this).isActivated()) ;

    }
    @Override
    public void executeOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnClient(executer, args);
        executer.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.cancelOnClient(executer, args);
        executer.getSkill(this).deactivate();
    }
}
