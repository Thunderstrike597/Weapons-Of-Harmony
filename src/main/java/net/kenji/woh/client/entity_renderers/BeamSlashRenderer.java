package net.kenji.woh.client.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.enitity_models.BeamSlashModel;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BeamSlashRenderer extends EntityRenderer<BeamSlashEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(WeaponsOfHarmony.MODID, "textures/entity/beam_disc.png");

    private final BeamSlashModel<BeamSlashEntity> model;

    public BeamSlashRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new BeamSlashModel<>(
                ctx.bakeLayer(BeamSlashModel.LAYER_LOCATION)
        );
    }

    @Override
    public void render(
            BeamSlashEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {

        if (entity.getSlashAngle() == -1)
            return;

        poseStack.pushPose();

        float tiltAngle = entity.getSlashAngle();

        if(!isFacingDiagonally(entity)) {
            // Cardinal directions - use Z-axis tilt
            poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

            poseStack.mulPose(Axis.ZP.rotationDegrees(getCardinalTiltAngle(tiltAngle, entityYaw)));

            float spin = (entity.tickCount + partialTicks) * 450.0F;
            poseStack.mulPose(Axis.YP.rotationDegrees(spin));

            poseStack.scale(2.8F, 2.4F, 2.8F);
        }
        else{
            // Diagonal directions - use X-axis tilt
            poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

            poseStack.mulPose(Axis.XP.rotationDegrees(getDiagonalTiltAngle(tiltAngle, entityYaw)));

            float spin = (entity.tickCount + partialTicks) * 450.0F;
            poseStack.mulPose(Axis.YP.rotationDegrees(spin));

            poseStack.scale(2.8F, 2.4F, 2.8F);

        }

        VertexConsumer vc = buffer.getBuffer(
                RenderType.entityTranslucent(getTextureLocation(entity))
        );

        this.model.renderToBuffer(
                poseStack,
                vc,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1F, 1F, 1F, 1F
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    // For cardinal directions (N, E, S, W) using Z-axis rotation
    private static float getCardinalTiltAngle(float tiltAngle, float yaw) {
        float normalizedYaw = ((yaw % 360) + 360) % 360;

        // Flip when facing East or West
        if ((normalizedYaw >= 45 && normalizedYaw < 135) ||   // West region
                (normalizedYaw >= 225 && normalizedYaw < 315)) {  // East region
            return -tiltAngle;
        }

        return tiltAngle;
    }

    // For diagonal directions (NE, SE, SW, NW) using X-axis rotation
    private static float getDiagonalTiltAngle(float tiltAngle, float yaw) {
        float normalizedYaw = ((yaw % 360) + 360) % 360;

        // Flip when facing SE or NW diagonals
        if ((normalizedYaw >= 112.5 && normalizedYaw < 202.5) ||   // SE region
                (normalizedYaw >= 292.5 || normalizedYaw < 22.5)) {  // NW region (wraps around 0°)
            return tiltAngle;
        }

        return -tiltAngle;
    }

    public boolean isFacingDiagonally(BeamSlashEntity entity) {
        float yaw = (entity.getYRot() % 360 + 360) % 360;
        float segment = yaw % 90;
        return segment > 22.5f && segment < 67.5f;
    }
    @Override
    public ResourceLocation getTextureLocation(BeamSlashEntity entity) {
        return TEXTURE;
    }
}