package net.tbkiron.guineapigsplus.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.tbkiron.guineapigsplus.entity.animations.GuineaPigAnimations;
import net.tbkiron.guineapigsplus.entity.custom.GuineaPigEntity;

public class GuineaPigModel<T extends GuineaPigEntity> extends HierarchicalModel<T> {
	private final ModelPart guineapig;
	private final ModelPart head;

	public GuineaPigModel(ModelPart root) {
		this.guineapig = root.getChild("guineapig");
		this.head = guineapig.getChild("body").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition guineapig = partdefinition.addOrReplaceChild("guineapig", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = guineapig.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition back_right_leg = body.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(6, 4).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.85F, -2.0F, 9.55F));

		PartDefinition back_left_leg = body.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.35F, -2.0F, 9.55F));

		PartDefinition front_right_leg = body.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.85F, -2.0F, -7.0F));

		PartDefinition front_left_leg = body.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.35F, -2.0F, -7.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 46).addBox(-3.25F, -2.9F, -5.5F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 8).addBox(-1.25F, -0.15F, -5.7F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -8.0F));

		PartDefinition ear2_r1 = head.addOrReplaceChild("ear2_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6014F, -2.4659F, -4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition ear1_r1 = head.addOrReplaceChild("ear1_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1161F, -2.4659F, -4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(1, 35).addBox(-2.0F, -10.0F, -9.25F, 10.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 0.6F, -0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(GuineaPigAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.sitToStandAnimationState, GuineaPigAnimations.SIT_TO_STAND, ageInTicks, 1.0F);
		this.animate(entity.standToSitAnimationState, GuineaPigAnimations.STAND_TO_SIT, ageInTicks, 1.0F);
		this.animate(entity.fourToTwoAnimationState, GuineaPigAnimations.STAND_FOUR_TO_TWO, ageInTicks, 1.6F);
		this.animate(entity.twoToFourAnimationState, GuineaPigAnimations.STAND_TWO_TO_FOUR, ageInTicks, 1.6F);

	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25F, 45F);

		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		guineapig.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return guineapig;
	}
}