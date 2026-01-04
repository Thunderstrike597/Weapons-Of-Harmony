package net.kenji.woh.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.item.custom.base.HolsterBaseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.atomic.AtomicReference;

@OnlyIn(Dist.CLIENT)
public class OffHandHolsteredItemLayer<T extends Player, M extends PlayerModel<T>>
        extends RenderLayer<T, M> {

    public OffHandHolsteredItemLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            T player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
            if (cap instanceof PlayerPatch<?> patch) {

                HumanoidArmature armature = (HumanoidArmature) patch.getArmature();


                OpenMatrix4f bodyMatrix = armature.getBoundTransformFor(patch.getAnimator().getPose(1), Armatures.BIPED.get().chest);


                ItemStack offHandStack = player.getOffhandItem();
                ItemStack stack = findOffHandHolsteredItem(player);

                if (!stack.isEmpty() && !offHandStack.isEmpty()) {

                    if (offHandStack.getItem() instanceof HolsterBaseItem holsterItem) {
                        if (holsterItem.canOffHandHolster) {
                            Vec3 offHandPos = holsterItem.holsterTransform.translationPair.value;
                            Vec3 offhandSize = holsterItem.holsterTransform.scalePair.value;
                            Quaternionf offhandRot = holsterItem.holsterTransform.rotationPair.value;
                            poseStack.pushPose();

                            try {
                                //Vec3f translation = bodyMatrix.toTranslationVector();
                                //poseStack.translate(translation.x, translation.y, translation.z);
                                //Quaternionf rotation = bodyMatrix.toQuaternion();
                                //poseStack.mulPose(rotation);
                                this.getParentModel().body.translateAndRotate(poseStack);

                                poseStack.translate(offHandPos.x, offHandPos.y, offHandPos.z);
                                poseStack.scale((float) offhandSize.x, (float) offhandSize.y, (float) offhandSize.z);
                                poseStack.mulPose(offhandRot);


                                Minecraft.getInstance().getItemRenderer()
                                        .renderStatic(
                                                stack,
                                                ItemDisplayContext.NONE,
                                                light,
                                                OverlayTexture.NO_OVERLAY,
                                                poseStack,
                                                buffer,
                                                player.level(),
                                                0
                                        );
                            } finally {
                                poseStack.popPose();
                            }
                        }
                    }
                }
            }
        });
    }

    private ItemStack findOffHandHolsteredItem(Player player){
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        ItemStack offHandItem = player.getOffhandItem();
        player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
            if (cap instanceof PlayerPatch<?> patch) {
                boolean isValid = patch.isOffhandItemValid();
                if (offHandItem.getItem() instanceof HolsterBaseItem holsterBaseItem) {
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
            }
        });
        return stack.get();
    }
}
