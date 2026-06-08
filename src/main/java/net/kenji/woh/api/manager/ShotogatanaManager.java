package net.kenji.woh.api.manager;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.gameasset.WohStyles;
import net.kenji.woh.gameasset.animation_types.WohSheathAnimation;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShotogatanaManager {

    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();
    public static final Map<UUID, Integer> queSheathCounter = new HashMap<>();
    public static Map<UUID, Boolean> renderSheathMap = new HashMap<>();
    public static Map<UUID, Style> sheathStyleMap = new HashMap<>();
    public static Map<UUID, Boolean> sheathPauseMap = new HashMap<>();


    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.remove(playerId);
    }

    public static void setWeaponSheathed(LivingEntity player, boolean sheathed){
        if(!(player.getMainHandItem().getItem() instanceof Shotogatana)){
            if(player.getMainHandItem().getTag() != null){
                player.getMainHandItem().getTag().remove("unsheathed");
                player.getMainHandItem().getTag().remove("sheath_counter");

            }
            return;
        }
            player.getMainHandItem().getOrCreateTag().putBoolean("unsheathed", !sheathed);
    }
    public static boolean getWeaponSheathed(LivingEntity player){
        if(!(player.getMainHandItem().getItem() instanceof Shotogatana)){
            if(player.getMainHandItem().getTag() != null){
                player.getMainHandItem().getTag().remove("unsheathed");
                player.getMainHandItem().getTag().remove("sheath_counter");

            }
            return true;
        }

            return !player.getMainHandItem().getOrCreateTag().getBoolean("unsheathed");
    }

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().getMainHandItem().getItem() instanceof Shotogatana shotogatana) {
            event.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof LivingEntityPatch<?> livingPatch) {
                    AnimationPlayer animPlayer = livingPatch.getAnimator().getPlayerFor(null);
                    if(animPlayer != null) {
                        if(!(animPlayer.getAnimation().get() instanceof AttackAnimation)){

                            int currentSheathCounter = livingPatch.getOriginal().getMainHandItem().getOrCreateTag().getInt("sheath_counter");
                            if (!ShotogatanaManager.getWeaponSheathed(livingPatch.getOriginal())) {
                                livingPatch.getOriginal().getMainHandItem().getOrCreateTag().putInt("sheath_counter", currentSheathCounter + 1);
                                if (livingPatch.getOriginal().level().isClientSide()) {
                                    AnimationPlayer clientAnimPlayer = livingPatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;
                                    if (currentSheathCounter > 10 && !(clientAnimPlayer.getAnimation().get() instanceof WohSheathAnimation)) {
                                        ShotogatanaManager.setWeaponSheathed(livingPatch.getOriginal(), true);
                                    }
                                } else if (livingPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                                    AnimationPlayer serverAnimPlayer = serverPlayerPatch.getServerAnimator().animationPlayer;
                                    if (currentSheathCounter > 10 && !(serverAnimPlayer.getAnimation().get() instanceof WohSheathAnimation)) {
                                        if (!ShotogatanaManager.getWeaponSheathed(event.getEntity()))
                                            ShotogatanaManager.setWeaponSheathed(event.getEntity(), true);
                                    }
                                }
                            }
                        }
                        else event.getEntity().getMainHandItem().getOrCreateTag().putInt("sheath_counter", 0);

                        DynamicAnimation animation = animPlayer.getAnimation().get();
                        if(animation instanceof AttackAnimation attackAnimation) {
                            AttackAnimation.Phase phase =attackAnimation.phases[attackAnimation.phases.length - 1];
                            float fixedRecovery = phase.contact + 0.75f;

                            if (animPlayer.getElapsedTime() > fixedRecovery)
                                livingPatch.getEntityState().setState(EntityState.CAN_BASIC_ATTACK, true);
                        }
                    }
                    boolean sheathed = !event.getEntity().getMainHandItem().getOrCreateTag().getBoolean("unsheathed");
                    CapabilityItem capItem = livingPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                    DynamicAnimation animation = livingPatch.getAnimator().getPlayerFor(null).getAnimation().get();
                    if(event.getEntity() instanceof Player player) {
                        UUID playerId = event.getEntity().getUUID();

                        boolean hasSetup = hasSetupWeapon.getOrDefault(playerId, false);
                        if (player.getMainHandItem().getItem() == WohItems.SHOTOGATANA.get()) {
                            if (!hasSetup) {
                                hasSetupWeapon.put(playerId, true);
                                queSheathCounter.put(playerId, 20);
                                renderSheathMap.put(playerId, true);
                                setWeaponSheathed(player, true);
                                }
                        }

                        if (livingPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                            if (sheathStyleMap.getOrDefault(playerId, capItem.getStyle(livingPatch)) != capItem.getStyle(livingPatch)) {
                                if (!sheathPauseMap.getOrDefault(playerId, false))
                                    serverPlayerPatch.modifyLivingMotionByCurrentItem();
                            }
                            sheathStyleMap.put(playerId, capItem.getStyle(livingPatch));
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDeath(PlayerEvent.PlayerRespawnEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.replace(playerId, false);
    }
}
