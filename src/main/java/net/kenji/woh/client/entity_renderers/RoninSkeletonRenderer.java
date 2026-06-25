package net.kenji.woh.client.entity_renderers;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class RoninSkeletonRenderer extends SkeletonRenderer {
    private static final ResourceLocation TEX_LOCATION = new ResourceLocation(WeaponsOfHarmony.MODID,"textures/entity/ronin_skeleton.png");

    public RoninSkeletonRenderer(EntityRendererProvider.Context p_174380_) {
        super(p_174380_);
    }
    public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
        return TEX_LOCATION;
    }

}
