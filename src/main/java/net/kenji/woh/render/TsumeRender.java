package net.kenji.woh.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.animations.BasisAirAttackAnimation;
import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.kenji.woh.gameasset.animations.WohSheathAnimation;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WOHItems;
import net.kenji.woh.registry.WOHSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TsumeRender extends RenderItemBase {
    private final ItemStack tsumeStack;
    private static final Map<EntityType<?>, Float> HAND_INWARD_OFFSETS = new HashMap<>();

    public TsumeRender() {
        this.tsumeStack = new ItemStack((ItemLike) WOHItems.TSUME.get());
    }

    static {
        HAND_INWARD_OFFSETS.put(EntityType.PLAYER, 0F);
        HAND_INWARD_OFFSETS.put(EntityType.ZOMBIE_VILLAGER, 0.035f);
    };


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
        float finalOffset = HAND_INWARD_OFFSETS.getOrDefault(entitypatch.getOriginal().getType(), 0F);

        // Only render katana in hand when NOT in_sheath
        OpenMatrix4f modelMatrix = new OpenMatrix4f(this.offhandCorrectionMatrix);
        modelMatrix.mulFront(poses[armature.toolL.getId()]);
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        poseStack.translate(-finalOffset, 0.0F, 0.0F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                tsumeStack,
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                null,
                0
        );
        poseStack.popPose();

        OpenMatrix4f modelRMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
        modelRMatrix.mulFront(poses[armature.toolR.getId()]);
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelRMatrix);
        poseStack.translate(finalOffset, 0.0F, 0.0F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                tsumeStack,
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
}
