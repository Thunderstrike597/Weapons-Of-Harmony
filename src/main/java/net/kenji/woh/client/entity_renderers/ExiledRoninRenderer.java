package net.kenji.woh.client.entity_renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.ModModelLayers;
import net.kenji.woh.client.enitity_models.ExiledRoninModel;
import net.kenji.woh.entities.custom.ExiledRoninEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ExiledRoninRenderer extends MobRenderer<ExiledRoninEntity, ExiledRoninModel<ExiledRoninEntity>> {

    public ExiledRoninRenderer(EntityRendererProvider.Context context) {
        super(context, new ExiledRoninModel<>(context.bakeLayer(ModModelLayers.EXILED_RONIN_LAYER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));

    }

    @Override
    public void render(ExiledRoninEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        // Reset color
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();
    }

    @Override
    protected RenderType getRenderType(ExiledRoninEntity entity, boolean showBody, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(getTextureLocation(entity));
    }
    @Override
    public ResourceLocation getTextureLocation(ExiledRoninEntity entity) {
        return new ResourceLocation(WeaponsOfHarmony.MODID, "textures/entity/exiled_ronin.png");
    }
}
