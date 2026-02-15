package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.WohStyles;
import net.kenji.woh.gameasset.animation_types.BasisAttackAnimation;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Shotogatana extends HolsterWeaponBase {


    static Vec3Pair holsterPos = new Vec3Pair(
            0.047998372F, 0.46F, 0.14F,
            -1, -1,-1);

    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0, 0, 0);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            47, -160, 468,    // Hotbar: slight forward tilt, diagonal angle, flipped
            15, -45, 180
    );

    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );
    public static JointPair holsterJoints = new JointPair(
            () -> Armatures.BIPED.rootJoint,
            () -> null
    );

    public Shotogatana(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, 0, -2.35f, builder, hasTooltip, tooltipColor, holsterTransform, WohItems.SHOTOGATANA_IN_SHEATH.get(), WohItems.SHOTOGATANA_SHEATH.get(),false, holsterJoints);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }

    @Override
    public boolean shouldRenderInHand(PlayerPatch<?> playerPatch) {
        return ShotogatanaManager.sheathWeapon.getOrDefault(playerPatch.getOriginal().getUUID(), false) && playerPatch.getOriginal().getMainHandItem().getItem() == this;
    }

    @Override
    public boolean shouldRenderUnholstered(PlayerPatch<?> player) {
        if(player instanceof LocalPlayerPatch localPlayerPatch) {
            return player.getSkill(WohSkills.SHEATH_STANCE) != null && player.getSkill(WohSkills.SHEATH_STANCE).isActivated() && !ShotogatanaManager.sheathWeapon.getOrDefault(player.getOriginal().getUUID(), false) && localPlayerPatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer.getAnimation() != ShotogatanaAnimations.SHOTOGATANA_UNSHEATH;
        }
        return false;
    }

    @Override
    public boolean shouldRenderHolstered(PlayerPatch<?> player) {
        return player.getOriginal().getMainHandItem().getItem() != this;
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        public static boolean queSheath;
        private static int queSheathCounter = 20;
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event){
           Player player = event.player;
            if(!player.level().isClientSide()) return;

            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent((cap) -> {
                if(cap instanceof PlayerPatch<?> playerPatch){
                    CapabilityItem itemCap = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                    AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(null);

                    if(queSheath){
                        if(!(animPlayer.getAnimation() instanceof AttackAnimation)) {
                            playerPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SHEATH, 0.2F);
                            queSheath = false;
                            queSheathCounter = 0;
                        }
                    }
                    if(queSheathCounter < 20)
                        queSheathCounter++;
                }
            });
        }
    }

}
