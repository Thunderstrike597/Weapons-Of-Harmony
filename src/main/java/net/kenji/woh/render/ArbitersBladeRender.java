package net.kenji.woh.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PEndermanRenderer;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArbitersBladeRender extends RenderItemBase {
    private final ItemStack bladeStack;

    public ArbitersBladeRender() {
        this.bladeStack = new ItemStack((ItemLike) WohItems.ARBITERS_BLADE.get());
    }

    private boolean shouldRenderEmissive(EntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);
            if (container != null) {
                if (container.isActivated()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ItemStack getGlintStack(EntityPatch<?> entitypatch) {
        ItemStack stack = bladeStack.copy();
        if(shouldRenderEmissive(entitypatch)) {
            stack.getOrCreateTag().putBoolean("woh_force_glint", true);
        }
        else{
            stack.getOrCreateTag().putBoolean("woh_force_glint", false);
        }
        return stack;
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

        if(bladeStack.getItem() instanceof HolsterWeaponBase holsterWeaponBase) {
         if(!shouldRenderEmissive(entitypatch)) {
             if (entitypatch instanceof PlayerPatch<?> playerPatch && holsterWeaponBase.shouldRenderInHand(playerPatch) || !(entitypatch instanceof PlayerPatch<?>)){
                 // 1️⃣ Normal render
                 super.renderItemInHand(
                         stack,
                         entitypatch,
                         hand,
                         armature,
                         poses,
                         buffer,
                         poseStack,
                         packedLight,
                         partialTicks
                 );
             }
             return;
         }
        }
        int fullBright = 0xF000F0;

        ItemStack glintStack = getGlintStack(entitypatch);

        OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
        modelMatrix.mulFront(poses[armature.toolR.getId()]);
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                glintStack,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                15,
                OverlayTexture.WHITE_OVERLAY_V,
                poseStack,
                buffer,
                null,
                0
        );

        poseStack.popPose();
    }
}
