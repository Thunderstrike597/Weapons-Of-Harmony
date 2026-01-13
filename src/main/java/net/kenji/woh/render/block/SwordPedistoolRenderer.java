package net.kenji.woh.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.block.custom.entity.SwordPedistoolBlockEntity;
import net.kenji.woh.item.custom.weapon.ArbitersBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jline.utils.Log;

public class SwordPedistoolRenderer implements BlockEntityRenderer<SwordPedistoolBlockEntity> {

    private final ItemRenderer itemRenderer;

    public SwordPedistoolRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(
            SwordPedistoolBlockEntity pedestal,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay
    ) {
        ItemStack stack = pedestal.getDisplayedItem();

        if (stack.isEmpty() || pedestal.getLevel() == null) return;

        poseStack.pushPose();

        // Center + above block
        poseStack.translate(0.59D, 1.1D, 0.51D);

        // Rotate to point downward (sword in stone pose)
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(180));  // Flip upside down
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90));   // Optional: slight angle for visual interest

        poseStack.scale(0.7F, 0.7F, 0.7F);

        int light = net.minecraft.client.renderer.LevelRenderer.getLightColor(
                pedestal.getLevel(),
                pedestal.getBlockPos().above()
        );

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                light,  // Use the calculated light instead of hardcoded 15
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                pedestal.getLevel(),
                0
        );
        poseStack.popPose();
    }
}
