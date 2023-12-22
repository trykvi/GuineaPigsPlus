// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class guinea_pig<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "guinea_pig"), "main");
	private final ModelPart guineapig;

	public guinea_pig(ModelPart root) {
		this.guineapig = root.getChild("guineapig");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition guineapig = partdefinition.addOrReplaceChild("guineapig", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = guineapig.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 35).addBox(-2.0F, -10.0F, -9.25F, 10.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 0.6F, -0.5F));

		PartDefinition head = guineapig.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 46).addBox(-3.25F, -2.9F, -5.5F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-1.25F, -0.15F, -5.7F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -8.0F));

		PartDefinition ear2_r1 = head.addOrReplaceChild("ear2_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6014F, -2.4659F, -4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition ear1_r1 = head.addOrReplaceChild("ear1_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1161F, -2.4659F, -4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition front_left_leg = guineapig.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.35F, -2.0F, -7.0F));

		PartDefinition front_right_leg = guineapig.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.85F, -2.0F, -7.0F));

		PartDefinition back_left_leg = guineapig.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.35F, -2.0F, 9.55F));

		PartDefinition back_right_leg = guineapig.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(6, 4).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.85F, -2.0F, 9.55F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		guineapig.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}