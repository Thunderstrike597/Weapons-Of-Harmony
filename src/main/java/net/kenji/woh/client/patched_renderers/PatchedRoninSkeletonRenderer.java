package net.kenji.woh.client.patched_renderers;

import net.kenji.woh.client.patched_renderers.p_renderers.PRoninSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import yesman.epicfight.client.renderer.patched.entity.PWitherSkeletonMinionRenderer;

public class PatchedRoninSkeletonRenderer extends PRoninSkeletonRenderer {

    public PatchedRoninSkeletonRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(context, entityType);
    }
}
