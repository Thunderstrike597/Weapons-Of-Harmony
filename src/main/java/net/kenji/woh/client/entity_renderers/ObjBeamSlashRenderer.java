package net.kenji.woh.client.entity_renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jline.utils.Log;

public class ObjBeamSlashRenderer<T extends Entity> extends EntityRenderer<T> {
    private final BakedModel objModel;

    public ObjBeamSlashRenderer(EntityRendererProvider.Context context, ResourceLocation modelLocation) {
        super(context);
        // Load the parsed OBJ model
        Log.info("ModelLoc: " + modelLocation);
        this.objModel = context.getModelManager().getModel(modelLocation);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity instanceof BeamSlashEntity beamSlashEntity) {
            // Apply entity-specific translations, rotations, and scaling here

            // Render the baked model's quads
            var blockRenderer = net.minecraft.client.Minecraft.getInstance().getBlockRenderer();
            var modelRenderer = blockRenderer.getModelRenderer();

            if (beamSlashEntity.getSlashAngle() == -1)
                return;

            poseStack.pushPose();

            float tiltAngle = beamSlashEntity.getSlashAngle();

            if (!isFacingDiagonally(beamSlashEntity)) {
                // Cardinal directions - use Z-axis tilt
                poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

                poseStack.mulPose(Axis.ZP.rotationDegrees(getCardinalTiltAngle(tiltAngle, entityYaw)));

                float spin = (entity.tickCount + partialTicks) * 450.0F;
                poseStack.mulPose(Axis.YP.rotationDegrees(spin));

                poseStack.scale(2.8F, 2.4F, 2.8F);
            } else {
                // Diagonal directions - use X-axis tilt
                poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

                poseStack.mulPose(Axis.XP.rotationDegrees(getDiagonalTiltAngle(tiltAngle, entityYaw)));

                float spin = (entity.tickCount + partialTicks) * 450.0F;
                poseStack.mulPose(Axis.YP.rotationDegrees(spin));

                poseStack.scale(2.8F, 2.4F, 2.8F);

            }
            modelRenderer.renderModel(
                    poseStack.last(),
                    // Draw using the unified block sheet context
                    buffer.getBuffer(RenderType.entityTranslucentEmissive(getTextureLocation(entity))),
                    null,
                    this.objModel,
                    1f, 1f, 1f,
                    packedLight,
                    OverlayTexture.NO_OVERLAY
            );

            poseStack.popPose();
        }
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
    public ResourceLocation getTextureLocation(T entity) {
        // Return your entity's material texture
        return ResourceLocation.fromNamespaceAndPath(WeaponsOfHarmony.MODID, "textures/entity/beam_slash.png");
    }
}
