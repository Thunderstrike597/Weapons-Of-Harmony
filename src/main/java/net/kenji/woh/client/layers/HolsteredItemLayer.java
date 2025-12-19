package net.kenji.woh.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.item.HolsterBaseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.atomic.AtomicReference;

public class HolsteredItemLayer <T extends Player, M extends PlayerModel<T>>
        extends RenderLayer<T, M> {
    private int itemSlotIndex;

    public HolsteredItemLayer(RenderLayerParent<T, M> parent, int hotbarSlot) {
        super(parent);
        itemSlotIndex = hotbarSlot;
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


        ItemStack stack = findHolsteredItem(player);
        if (!stack.isEmpty()) {

            if (stack.getItem() instanceof HolsterBaseItem holsterItem) {

                ItemStack holsterStack = holsterItem.holsterItem != null ?
                        holsterItem.holsterItem.getDefaultInstance() : ItemStack.EMPTY;
                ItemStack unholsterStack = holsterItem.unholsteredItem != null ?
                        holsterItem.unholsteredItem.getDefaultInstance() : ItemStack.EMPTY;

                Vec3 hotbarHandPos = holsterItem.holsterTransform.translationPair.key;
                Vec3 hotbarSize = holsterItem.holsterTransform.scalePair.key;
                Quaternionf hotbarRot = holsterItem.holsterTransform.rotationPair.key;
                poseStack.pushPose();

                try {
                    this.getParentModel().body.translateAndRotate(poseStack);

                    poseStack.translate(hotbarHandPos.x, hotbarHandPos.y, hotbarHandPos.z);
                    poseStack.scale((float) hotbarSize.x, (float) hotbarSize.y, (float) hotbarSize.z);
                    poseStack.mulPose(hotbarRot);

                    if (player.getMainHandItem() != stack && !holsterStack.isEmpty()) {
                        Minecraft.getInstance().getItemRenderer()
                                .renderStatic(
                                        holsterStack,
                                        ItemDisplayContext.NONE,
                                        light,
                                        OverlayTexture.NO_OVERLAY,
                                        poseStack,
                                        buffer,
                                        player.level(),
                                        0
                                );
                    } else if (player.getMainHandItem() == stack && !unholsterStack.isEmpty()) {
                        Minecraft.getInstance().getItemRenderer()
                                .renderStatic(
                                        unholsterStack,
                                        ItemDisplayContext.NONE,
                                        light,
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
    }

    private ItemStack findHolsteredItem(Player player){
        int index = 36 + itemSlotIndex;
            Slot slot = player.inventoryMenu.slots.get(index);
            if(slot.getItem().getItem() instanceof HolsterBaseItem holsterBaseItem){
                if(slot.getItem() != player.getMainHandItem() || holsterBaseItem.unholsteredItem != null) {
                    return slot.getItem();
                }
            }
        return ItemStack.EMPTY;
    }

    private ItemStack findOffHandHolsteredItem(Player player){
        Slot offHandSlot = player.inventoryMenu.slots.get(45);
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
            if (cap instanceof PlayerPatch<?> patch) {
                boolean isValid = patch.isOffhandItemValid();
                ItemStack offHandStack = patch.getValidItemInHand(InteractionHand.OFF_HAND);
                if(isValid && offHandStack.getItem() instanceof HolsterBaseItem){
                    stack.set(offHandStack);
                }
            }
        });

        return stack.get();
    }
}
