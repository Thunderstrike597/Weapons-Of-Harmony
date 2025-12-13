package net.kenji.woh.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.animations.KatanaAttackAnimation;
import net.kenji.woh.registry.animation.WOHAnimations;
import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.ServerAnimator;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnhancedKatanaRender extends RenderItemBase {
    public static final Map<UUID, Boolean> sheathWeapon = new HashMap<>();
    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();


    private final ItemStack katana;
    private final ItemStack sheathStack;
    private final ItemStack sheathedWeaponStack;

    private StaticAnimation idleSheathAnim = WOHAnimations.ENHANCED_KATANA_IDLE;
    private StaticAnimation walkSheathAnim = WOHAnimations.ENHANCED_KATANA_WALK;

    private StaticAnimation sheathAnim = WOHAnimations.ENHANCED_KATANA_SHEATH;
    private StaticAnimation unsheathAnim = WOHAnimations.ENHANCED_KATANA_UNSHEATH;

    public EnhancedKatanaRender() {
        this.katana = new ItemStack((ItemLike) WOHItems.ENHANCED_KATANA.get());
        this.sheathStack = new ItemStack((ItemLike) WOHItems.ENHANCED_KATANA_SHEATH.get());
        this.sheathedWeaponStack = new ItemStack((ItemLike) WOHItems.ENHANCED_KATANA_IN_SHEATH.get());
    }

        @SubscribeEvent
        public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
           UUID playerId = event.getEntity().getUUID();
           sheathWeapon.remove(playerId);
            hasSetupWeapon.remove(playerId);
        }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        UUID playerId = event.player.getUUID();
        boolean hasSetup = hasSetupWeapon.getOrDefault(playerId, false);
        if(event.player.getMainHandItem().getItem() == WOHItems.ENHANCED_KATANA.get()){
            if(!hasSetup){
                sheathWeapon.put(playerId, true);
                hasSetupWeapon.put(playerId, true);
            }
        }
    }
    @SubscribeEvent
    public static void onDeath(PlayerEvent.PlayerRespawnEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.replace(playerId, false);

    }

    private ItemStack getStack(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (sheathAnim == null || unsheathAnim == null)
                return sheathStack;
            UUID playerID = playerPatch.getOriginal().getUUID();
            boolean isSheathed = sheathWeapon.getOrDefault(playerID, false);

            AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.LOWEST).animationPlayer;

            if(animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.MIDDLE).animationPlayer;
            }
            if(animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;
            }

            if(animPlayer.getAnimation() == idleSheathAnim || animPlayer.getAnimation() == walkSheathAnim) {
               boolean isAttacking = KatanaAttackAnimation.isAttacking.getOrDefault(playerID, true);
                if(!isAttacking) {
                   isSheathed = true;
               }
            }

            if(isSheathed){
                return sheathedWeaponStack;
            }
        }
        // Skill inactive → katana in hand
        return sheathStack;
    }


    @Override
    public void renderItemInHand(
            ItemStack stack,
            LivingEntityPatch<?> entitypatch,
            InteractionHand hand,
            HumanoidArmature armature,
            OpenMatrix4f[] poses,
            MultiBufferSource buffer,
            PoseStack poseStack,
            int packedLight,
            float partialTicks
    ) {
        ItemStack sheathItem = getStack(entitypatch);
        boolean sheathed = (sheathItem == sheathedWeaponStack);

        if (entitypatch.getOriginal() instanceof Player) {
            if (!sheathed) {
                // Only render katana in hand when NOT sheathed
                OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
                modelMatrix.mulFront(poses[armature.toolR.getId()]);
                poseStack.pushPose();
                this.mulPoseStack(poseStack, modelMatrix);

                Minecraft.getInstance().getItemRenderer().renderStatic(
                        katana,
                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        null,
                        0
                );

                poseStack.popPose();
            }

            // ---------------------------
            // LEFT HAND (sheath side)
            // ---------------------------
            OpenMatrix4f matrixL = new OpenMatrix4f(this.mainhandcorrectionMatrix);
            matrixL.mulFront(poses[armature.toolL.getId()]);
            poseStack.pushPose();
            this.mulPoseStack(poseStack, matrixL);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    sheathItem,
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND, // <---- use correct context
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    null,
                    0
            );

            poseStack.popPose();
        }
        else{
                // Only render katana in hand when NOT sheathed
                OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
                modelMatrix.mulFront(poses[armature.toolR.getId()]);
                poseStack.pushPose();
                this.mulPoseStack(poseStack, modelMatrix);

                Minecraft.getInstance().getItemRenderer().renderStatic(
                        katana,
                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        null,
                        0
                );

                poseStack.popPose();

            // ---------------------------
            // LEFT HAND (sheath side)
            // ---------------------------
            OpenMatrix4f matrixL = new OpenMatrix4f(this.mainhandcorrectionMatrix);
            matrixL.mulFront(poses[armature.toolL.getId()]);
            poseStack.pushPose();
            this.mulPoseStack(poseStack, matrixL);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    sheathItem,
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND, // <---- use correct context
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    null,
                    0
            );

            poseStack.popPose();
        }
    }
}
