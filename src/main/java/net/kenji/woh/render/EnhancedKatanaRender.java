package net.kenji.woh.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.TimeStampManager;
import net.kenji.woh.registry.WOHSkills;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnhancedKatanaRender extends RenderItemBase {
    private static final Map<UUID, Boolean> canShowKatanaInSheath = new HashMap<>();
    public static final Map<UUID, Boolean> attacking = new HashMap<>();


    private final ItemStack katana;
    private final ItemStack sheathStack;
    private final ItemStack sheathedWeaponStack;

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
           canShowKatanaInSheath.remove(playerId);
        }
    private ItemStack getStack(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (sheathAnim == null || unsheathAnim == null)
                return sheathStack;
            // 1. Check if the stance/skill is actually active
            SkillContainer skill = playerPatch.getSkill(WOHSkills.SHEATH_STANCE);
            if (skill == null)
                return sheathStack;

            UUID playerId = entitypatch.getOriginal().getUUID();

            boolean isSheathed = canShowKatanaInSheath.getOrDefault(playerId, false);
            boolean isAttacking = attacking.getOrDefault(playerId, false);

            AnimationPlayer livingPriorityPlayer =
                    playerPatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;

            DynamicAnimation currentLivingAnim = livingPriorityPlayer.getAnimation();



            if (skill.isActivated()) {
                if(!isAttacking) {
                    if (currentLivingAnim instanceof StaticAnimation staticAnimation) {

                        if (currentLivingAnim == sheathAnim) {
                            if (!TimeStampManager.isLowerThanEnd(playerPatch, staticAnimation)) {
                                canShowKatanaInSheath.put(playerId, true);
                                return sheathedWeaponStack;
                            }
                        }
                    }
                    if (isSheathed) {
                        return sheathedWeaponStack;
                    }
                }
                else return sheathStack;
            } else {


                    if (currentLivingAnim instanceof StaticAnimation staticAnimation) {
                        if (currentLivingAnim == unsheathAnim) {
                            if (TimeStampManager.isHigherThanStart(playerPatch, staticAnimation)) {
                                if (isSheathed)
                                    canShowKatanaInSheath.remove(playerId);
                            } else {
                                return sheathedWeaponStack;
                            }
                        }
                    }
                    if (isSheathed) {
                        return sheathedWeaponStack;
                    }
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
