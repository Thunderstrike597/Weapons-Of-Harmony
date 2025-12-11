package net.kenji.woh.client.enitity_models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ExiledRoninModel<T extends LivingEntity> extends HumanoidModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    public ExiledRoninModel(ModelPart root) {
        super(root); // assigns all HumanoidModel parts automatically
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4,-8,-4,8,8,8),
                PartPose.offset(0,0,0));

        root.addOrReplaceChild("hat",
                CubeListBuilder.create(),
                PartPose.ZERO);

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0,16).addBox(-4,0,-2,8,12,4),
                PartPose.offset(0,0,0));

        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(32,0).addBox(-3,-2,-2,4,12,4),
                PartPose.offset(-5,2,0));

        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(16,32).addBox(-1,-2,-2,4,12,4),
                PartPose.offset(5,2,0));

        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(24,16).addBox(-2,0,-2,4,12,4),
                PartPose.offset(-2,12,0));

        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0,32).addBox(-2,0,-2,4,12,4),
                PartPose.offset(2,12,0));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
