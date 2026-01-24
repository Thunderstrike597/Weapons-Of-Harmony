package net.kenji.woh.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.item.custom.base.HolsterShieldBase;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.atomic.AtomicReference;

import static net.kenji.woh.WohConfigClient.HOLSTER_HIDDEN_ITEMS;

@OnlyIn(Dist.CLIENT)
public class OffHandHolsteredItemLayer extends ModelRenderLayer<
        AbstractClientPlayer,
        AbstractClientPlayerPatch<AbstractClientPlayer>,
        PlayerModel<AbstractClientPlayer>,
        RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>,
        HumanoidMesh
        > {

    public OffHandHolsteredItemLayer(Meshes.MeshAccessor<HumanoidMesh> mesh) {
        super(mesh);
    }

    public static boolean showHolsterFor(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return false;

        return !HOLSTER_HIDDEN_ITEMS.get().contains(id.toString());
    }

    @Override
    protected void renderLayer(
            AbstractClientPlayerPatch<AbstractClientPlayer> patch,
            AbstractClientPlayer player,
            @Nullable RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parentLayer,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            OpenMatrix4f[] poses,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks
    ) {
        int currentLightLevel = LevelRenderer.getLightColor(
                player.level(),
                player.blockPosition()
        );
        ItemStack stack = findOffHandHolsteredItem(player);
        boolean showItem = showHolsterFor(stack.getItem());

        if (stack.isEmpty() || !showItem) return;

        if ((stack.getItem() instanceof HolsterWeaponBase holsterItem)) {


            if (!holsterItem.canOffHandHolster) return;
            if (holsterItem.holsterTransform == null) return;

            // Use offhand transform values
            Vec3 offHandPos = holsterItem.holsterTransform.translationPair.value;
            Vec3 offhandSize = holsterItem.holsterTransform.scalePair.value;
            Quaternionf offhandRot = holsterItem.holsterTransform.rotationPair.value;

            poseStack.pushPose();
            try {
                // 🔥 Attach to Epic Fight BODY bone (same as mainhand)
                Matrix4f jomlMatrix = getMatrix4f(poses, holsterItem);
                poseStack.mulPoseMatrix(jomlMatrix);

                // Local transform with offhand-specific values
                poseStack.translate(offHandPos.x, offHandPos.y, offHandPos.z);
                poseStack.mulPose(offhandRot);
                poseStack.scale((float) offhandSize.x, (float) offhandSize.y, (float) offhandSize.z);

                // Render the holstered item
                if (!stack.isEmpty()) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(
                            stack,
                            ItemDisplayContext.NONE,
                            currentLightLevel,
                            OverlayTexture.NO_OVERLAY,
                            poseStack,
                            buffer,
                            player.level(),
                            0
                    );
                }
            } finally {
                poseStack.popPose();
            }
        }

        if ((stack.getItem() instanceof HolsterShieldBase holsterItem)) {
            if (holsterItem.holsterTransform == null) return;

            // Use offhand transform values
            Vec3 offHandPos = holsterItem.holsterTransform.translationPair.value;
            Vec3 offhandSize = holsterItem.holsterTransform.scalePair.value;
            Quaternionf offhandRot = holsterItem.holsterTransform.rotationPair.value;

            poseStack.pushPose();
            try {
                // 🔥 Attach to Epic Fight BODY bone (same as mainhand)
                Matrix4f jomlMatrix = getMatrix4f(poses, holsterItem);
                poseStack.mulPoseMatrix(jomlMatrix);

                // Local transform with offhand-specific values
                poseStack.translate(offHandPos.x, offHandPos.y, offHandPos.z);
                poseStack.mulPose(offhandRot);
                poseStack.scale((float) offhandSize.x, (float) offhandSize.y, (float) offhandSize.z);

                // Render the holstered item
                if (!stack.isEmpty()) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(
                            stack,
                            ItemDisplayContext.NONE,
                            currentLightLevel,
                            OverlayTexture.NO_OVERLAY,
                            poseStack,
                            buffer,
                            player.level(),
                            0
                    );
                }
            } finally {
                poseStack.popPose();
            }
        }

    }

    private static @NotNull Matrix4f getMatrix4f(OpenMatrix4f[] poses, HolsterWeaponBase holsterItem) {
        OpenMatrix4f bodyMatrix = poses[holsterItem.holsterJoints.getHotBarJoint().getId()];

        // Manual conversion preserving all components
        Matrix4f jomlMatrix = new Matrix4f(
                bodyMatrix.m00, bodyMatrix.m01, bodyMatrix.m02, bodyMatrix.m03,
                bodyMatrix.m10, bodyMatrix.m11, bodyMatrix.m12, bodyMatrix.m13,
                bodyMatrix.m20, bodyMatrix.m21, bodyMatrix.m22, bodyMatrix.m23,
                bodyMatrix.m30, bodyMatrix.m31, bodyMatrix.m32, bodyMatrix.m33
        );
        return jomlMatrix;
    }
    private static @NotNull Matrix4f getMatrix4f(OpenMatrix4f[] poses, HolsterShieldBase holsterItem) {
        OpenMatrix4f bodyMatrix = poses[holsterItem.holsterJoints.getHotBarJoint().getId()];

        // Manual conversion preserving all components
        Matrix4f jomlMatrix = new Matrix4f(
                bodyMatrix.m00, bodyMatrix.m01, bodyMatrix.m02, bodyMatrix.m03,
                bodyMatrix.m10, bodyMatrix.m11, bodyMatrix.m12, bodyMatrix.m13,
                bodyMatrix.m20, bodyMatrix.m21, bodyMatrix.m22, bodyMatrix.m23,
                bodyMatrix.m30, bodyMatrix.m31, bodyMatrix.m32, bodyMatrix.m33
        );
        return jomlMatrix;
    }

    private ItemStack findOffHandHolsteredItem(Player player) {
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        ItemStack offHandItem = player.getOffhandItem();

        player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
            if (cap instanceof PlayerPatch<?> patch) {
                boolean isValid = patch.isOffhandItemValid();
                if (offHandItem.getItem() instanceof HolsterWeaponBase holsterBaseItem) {
                    if (isValid) {
                        ItemStack offHandStack = patch.getValidItemInHand(InteractionHand.OFF_HAND);
                        if (holsterBaseItem.unholsteredItem != null) {
                            stack.set(holsterBaseItem.unholsteredItem.getDefaultInstance());
                        }
                    } else {
                        if (holsterBaseItem.holsterItem != null) {
                            stack.set(holsterBaseItem.holsterItem.getDefaultInstance());
                        }
                    }
                }
                if (offHandItem.getItem() instanceof HolsterShieldBase holsterBaseItem) {
                    if (!holsterBaseItem.shouldRenderInHand(patch, WohItems.ARBITERS_BLADE.get())) {
                        stack.set(holsterBaseItem.getDefaultInstance());
                    }
                }
            }
        });
        return stack.get();
    }
}