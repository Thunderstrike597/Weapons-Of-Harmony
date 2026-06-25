package net.kenji.woh.client.patched_renderers.p_renderers;

import net.kenji.woh.entities.custom.alt_entities.RoninSkeletonEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class PRoninSkeletonRenderer extends PHumanoidRenderer<RoninSkeletonEntity, HumanoidMobPatch<RoninSkeletonEntity>, HumanoidModel<RoninSkeletonEntity>, HumanoidMobRenderer<RoninSkeletonEntity, HumanoidModel<RoninSkeletonEntity>>, HumanoidMesh> {
    public PRoninSkeletonRenderer(EntityRendererProvider.Context context, EntityType<?> entityType) {
        super(Meshes.SKELETON, context, entityType);
    }
}
