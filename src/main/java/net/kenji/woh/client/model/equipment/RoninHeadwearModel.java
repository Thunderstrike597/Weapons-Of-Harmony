package net.kenji.woh.client.model.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class RoninHeadwearModel<T extends LivingEntity> extends HumanoidModel<T> {

    private final ModelPart hat;
    private final ModelPart hatLower;

    public RoninHeadwearModel(ModelPart root) {
        super(root);
        // Get the hat from the root's head part (which was built by createBodyLayer)
        ModelPart head = root.getChild("head");
        this.hat = head.getChild("hat");
        this.hatLower = this.hat.getChild("HatLower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();

        // Get the existing head part
        PartDefinition head = root.getChild("head");

        // Add your custom hat to the head
        PartDefinition hat = head.addOrReplaceChild(
                "hat",
                CubeListBuilder.create(),
                PartPose.offset(0,-5.5F,0)
        );

        PartDefinition cube_r1 = hat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-8, -8).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.55F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition HatLower = hat.addOrReplaceChild("HatLower", CubeListBuilder.create(), PartPose.offset(8.3F, 0, -3.7F));

        PartDefinition corner = HatLower.addOrReplaceChild("corner", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r2 = corner.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-14, -10).addBox(10.5482F, -4.8982F, -14.1249F, 5.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, 1.2524F, 2.168F));

        PartDefinition cube_r3 = corner.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-15, -6).addBox(8.3592F, -6.1155F, -8.7161F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8925F, 0.7437F, 2.7822F));

        PartDefinition cube_r4 = corner.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-15, -6).addBox(4.529F, -5.0892F, -12.2775F, 11.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, 1.2524F, 2.168F));

        PartDefinition cube_r5 = corner.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(-15, -6).addBox(9.9377F, -6.6102F, -6.8033F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0374F, 0.3702F, 2.8603F));

        PartDefinition cube_r6 = corner.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-15, -6).addBox(-0.6449F, -3.7746F, -11.8991F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5697F, 1.1098F, 0.6209F));

        PartDefinition corner2 = HatLower.addOrReplaceChild("corner2", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.0F, 0.0F, -5.0F, -3.1416F, 1.1781F, 3.1416F));

        PartDefinition cube_r7 = corner2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(-14, -10).addBox(10.5482F, -4.8982F, -14.1249F, 5.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, 1.2524F, 2.168F));

        PartDefinition cube_r8 = corner2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(-15, -6).addBox(8.3592F, -6.1155F, -8.7161F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8925F, 0.7437F, 2.7822F));

        PartDefinition cube_r9 = corner2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(-15, -6).addBox(4.529F, -5.0892F, -12.2775F, 11.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, 1.2524F, 2.168F));

        PartDefinition cube_r10 = corner2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(-15, -6).addBox(9.9377F, -6.6102F, -6.8033F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0374F, 0.3702F, 2.8603F));

        PartDefinition corner3 = HatLower.addOrReplaceChild("corner3", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.6F, 0.0F, -5.0F, -3.1416F, -1.1781F, -3.1416F));

        PartDefinition cube_r11 = corner3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(-14, -10).mirror().addBox(-15.5482F, -4.8982F, -14.1249F, 5.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, -1.2524F, -2.168F));

        PartDefinition cube_r12 = corner3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-20.3592F, -6.1155F, -8.7161F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8925F, -0.7437F, -2.7822F));

        PartDefinition cube_r13 = corner3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-15.529F, -5.0892F, -12.2775F, 11.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, -1.2524F, -2.168F));

        PartDefinition cube_r14 = corner3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-21.9377F, -6.6102F, -6.8033F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0374F, -0.3702F, -2.8603F));

        PartDefinition corner4 = HatLower.addOrReplaceChild("corner4", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.6F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r15 = corner4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(-14, -10).mirror().addBox(-15.5482F, -4.8982F, -14.1249F, 5.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, -1.2524F, -2.168F));

        PartDefinition cube_r16 = corner4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-20.3592F, -6.1155F, -8.7161F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8925F, -0.7437F, -2.7822F));

        PartDefinition cube_r17 = corner4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-15.529F, -5.0892F, -12.2775F, 11.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1922F, -1.2524F, -2.168F));

        PartDefinition cube_r18 = corner4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-21.9377F, -6.6102F, -6.8033F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0374F, -0.3702F, -2.8603F));

        PartDefinition cube_r19 = corner4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(-15, -6).mirror().addBox(-11.3551F, -3.7746F, -11.8991F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5697F, -1.1098F, -0.6209F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        // Only render the head with the hat
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}