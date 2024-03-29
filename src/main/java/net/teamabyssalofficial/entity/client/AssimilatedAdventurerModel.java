package net.teamabyssalofficial.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.teamabyssalofficial.entity.custom.AssimilatedAdventurerEntity;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;

public class AssimilatedAdventurerModel<T extends AssimilatedAdventurerEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FightOrDieMutations.MODID, "assimilatedadventurermodel"), "main");
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart Tentacle5;
	private final ModelPart Tentacle7;
	private final ModelPart Tentacle3;
	private final ModelPart Tentacle4;
	private final ModelPart Tentacle2;
	private final ModelPart Tentacle6;
	private final ModelPart Tentacle1;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart hat;

	public AssimilatedAdventurerModel(ModelPart root) {
		super(root);
		this.right_leg = root.getChild("right_leg");
		this.left_leg = root.getChild("left_leg");
		this.right_arm = root.getChild("right_arm");
		this.left_arm = root.getChild("left_arm");
		this.Tentacle5 = root.getChild("Tentacle5");
		this.Tentacle7 = root.getChild("Tentacle7");
		this.Tentacle3 = root.getChild("Tentacle3");
		this.Tentacle4 = root.getChild("Tentacle4");
		this.Tentacle2 = root.getChild("Tentacle2");
		this.Tentacle6 = root.getChild("Tentacle6");
		this.Tentacle1 = root.getChild("Tentacle1");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.hat = root.getChild("hat");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.5F, 7.5F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 46).addBox(1.25F, 10.5F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 42).addBox(1.25F, 3.5F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(48, 44).addBox(1.25F, 9.5F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 40).addBox(-2.25F, 4.5F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.5F, 1.5F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 11.8637F, -1.5492F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 44).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(1.25F, 10.4904F, -2.3398F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 38).addBox(1.25F, 9.644F, 1.2005F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 36).addBox(1.25F, 5.6478F, 1.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(0.5F, 3.4743F, -2.5776F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(52, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.0F, 11.8637F, -1.5492F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 12).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(13, 60).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(51, 43).addBox(1.3F, 4.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 50).addBox(-2.2F, 7.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 48).addBox(-2.5F, 2.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-6.0F, 1.9493F, -2.8545F, -1.3654F, -0.0741F, 0.3414F));

		PartDefinition bone6 = right_arm.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(-2.0F, 9.0F, -1.0F));

		PartDefinition cube_r1 = bone6.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 59).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition cube_r2 = bone6.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 15).addBox(-3.6868F, -1.0746F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition cube_r3 = bone6.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(38, 27).addBox(-4.8939F, -3.4512F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.789F));

		PartDefinition bone5 = right_arm.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-1.9F, 9.25F, 1.05F));

		PartDefinition cube_r4 = bone5.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 22).addBox(-2.985F, -0.2688F, -0.05F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0934F, 0.1974F, -0.1402F));

		PartDefinition cube_r5 = bone5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(56, 13).addBox(-3.394F, -2.4778F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2132F, 0.0469F, -1.0522F));

		PartDefinition cube_r6 = bone5.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(56, 14).addBox(-2.6946F, -4.2365F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1848F, -0.1166F, -1.8218F));

		PartDefinition bone4 = right_arm.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(1.9F, 9.0F, -0.7F));

		PartDefinition cube_r7 = bone4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(33, 18).addBox(1.394F, -2.4778F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2132F, -0.0469F, 1.4013F));

		PartDefinition cube_r8 = bone4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 23).addBox(-0.015F, -0.2688F, -0.05F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0934F, -0.1974F, 0.4892F));

		PartDefinition cube_r9 = bone4.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(32, 27).addBox(0.6946F, -4.2365F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1848F, 0.1166F, 2.1708F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 44).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 28).addBox(0.75F, -1.0F, -2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 54).addBox(1.25F, 2.0F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 41).addBox(1.25F, 3.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 52).addBox(-2.25F, 5.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 41).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(6.0F, 1.9493F, -2.8545F, -1.315F, 0.0393F, -0.303F));

		PartDefinition bone = left_arm.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(1.9537F, 10.0392F, -1.4519F));

		PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(2, 31).addBox(0.9944F, 0.2171F, -4.0257F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.9493F, -1.1462F, -1.2036F));

		PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(42, 21).addBox(1.0672F, -0.4993F, -3.169F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3384F, -1.1462F, -1.2036F));

		PartDefinition cube_r12 = bone.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 31).addBox(0.7281F, -0.3826F, -0.5384F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.0249F, -1.1588F, -0.8729F));

		PartDefinition bone2 = left_arm.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-1.5413F, 8.5376F, 2.3333F));

		PartDefinition cube_r13 = bone2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 3).addBox(2.3138F, 0.4616F, -4.2508F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8988F, 1.2533F, 2.6008F));

		PartDefinition cube_r14 = bone2.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(16, 55).addBox(2.1008F, 0.9148F, -1.8823F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3528F, 1.2714F, 3.0574F));

		PartDefinition cube_r15 = bone2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 15).addBox(1.864F, 0.3637F, -0.1262F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0615F, 1.3945F, 2.8205F));

		PartDefinition bone3 = left_arm.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-1.5413F, 8.5376F, 2.3333F));

		PartDefinition cube_r16 = bone3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(24, 15).addBox(4.3091F, 2.4687F, -2.9207F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.532F, 1.2839F, -2.1615F));

		PartDefinition cube_r17 = bone3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(18, 55).addBox(3.9574F, 0.8974F, -0.5034F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.0741F, 1.2917F, -1.9516F));

		PartDefinition cube_r18 = bone3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(6, 3).addBox(3.8691F, -0.5883F, 0.5232F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.5413F, 1.3929F, -1.5489F));

		PartDefinition Tentacle5 = partdefinition.addOrReplaceChild("Tentacle5", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.3476F, -0.7464F, 1.0738F, 1.1023F, 1.2165F));

		PartDefinition t10 = Tentacle5.addOrReplaceChild("t10", CubeListBuilder.create().texOffs(48, 10).mirror().addBox(-3.0F, -0.3959F, -0.5396F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -0.1041F, 0.0396F, -0.2844F, -0.5386F, 0.1633F));

		PartDefinition t11 = t10.addOrReplaceChild("t11", CubeListBuilder.create().texOffs(32, 25).mirror().addBox(-4.0F, -0.571F, -0.5511F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 0.1751F, 0.0115F, 0.1101F, -1.2044F, 0.3667F));

		PartDefinition Tentacle7 = partdefinition.addOrReplaceChild("Tentacle7", CubeListBuilder.create().texOffs(60, 26).addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0976F, -0.7464F, 0.8532F, -1.0221F, -1.0576F));

		PartDefinition t14 = Tentacle7.addOrReplaceChild("t14", CubeListBuilder.create().texOffs(48, 10).addBox(0.0F, -0.3959F, -0.5396F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.1041F, 0.0396F, 0.1449F, 0.4189F, -0.2121F));

		PartDefinition t15 = t14.addOrReplaceChild("t15", CubeListBuilder.create().texOffs(32, 25).addBox(0.0F, -0.571F, -0.5511F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.1751F, 0.0115F, 1.2039F, 1.1026F, 0.5722F));

		PartDefinition Tentacle3 = partdefinition.addOrReplaceChild("Tentacle3", CubeListBuilder.create().texOffs(60, 26).addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0976F, -0.7464F, 0.2107F, -0.5848F, -0.969F));

		PartDefinition t5 = Tentacle3.addOrReplaceChild("t5", CubeListBuilder.create().texOffs(48, 10).addBox(0.0F, -0.3959F, -0.5396F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.1041F, 0.0396F, 0.3397F, 0.5284F, -0.0477F));

		PartDefinition t6 = t5.addOrReplaceChild("t6", CubeListBuilder.create().texOffs(32, 25).addBox(0.0F, -0.571F, -0.5511F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.1751F, 0.0115F, 0.5828F, 0.7775F, -0.2487F));

		PartDefinition Tentacle4 = partdefinition.addOrReplaceChild("Tentacle4", CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 6.8476F, -0.7464F, -2.883F, -0.8586F, -2.9094F));

		PartDefinition t8 = Tentacle4.addOrReplaceChild("t8", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -0.5264F, -0.531F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0264F, 0.031F, 0.3645F, -0.3586F, -0.0925F));

		PartDefinition t9 = t8.addOrReplaceChild("t9", CubeListBuilder.create().texOffs(22, 60).addBox(0.0F, -1.1269F, -1.0081F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.6006F, 0.477F, -0.3958F, -1.2257F, 0.0935F));

		PartDefinition Tentacle2 = partdefinition.addOrReplaceChild("Tentacle2", CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 9.3476F, -0.7464F, 3.098F, -0.7844F, 2.7233F));

		PartDefinition t3 = Tentacle2.addOrReplaceChild("t3", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -0.5264F, -0.531F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0264F, 0.031F, 0.1207F, -0.5597F, -0.2419F));

		PartDefinition t4 = t3.addOrReplaceChild("t4", CubeListBuilder.create().texOffs(22, 60).addBox(0.0F, -1.1269F, -1.0081F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.6006F, 0.477F, -0.3958F, -1.2257F, 0.0935F));

		PartDefinition Tentacle6 = partdefinition.addOrReplaceChild("Tentacle6", CubeListBuilder.create().texOffs(24, 2).addBox(0.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 8.0976F, 0.0036F, 0.0531F, -1.134F, -0.0816F));

		PartDefinition t12 = Tentacle6.addOrReplaceChild("t12", CubeListBuilder.create().texOffs(33, 16).addBox(0.0F, -0.5396F, -0.5466F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0396F, 0.0466F, 0.4774F, 1.4739F, 0.1126F));

		PartDefinition t13 = t12.addOrReplaceChild("t13", CubeListBuilder.create().texOffs(60, 2).addBox(0.0F, -0.4486F, -0.6017F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -0.091F, 0.0551F, -0.0799F, 0.5259F, -0.2407F));

		PartDefinition Tentacle1 = partdefinition.addOrReplaceChild("Tentacle1", CubeListBuilder.create().texOffs(24, 2).addBox(0.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 10.5976F, 0.0036F, 0.0056F, -0.6549F, 0.262F));

		PartDefinition t1 = Tentacle1.addOrReplaceChild("t1", CubeListBuilder.create().texOffs(33, 16).mirror().addBox(0.0F, -0.5396F, -0.5466F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0396F, 0.0466F, 0.4198F, 1.1687F, 0.0498F));

		PartDefinition t2 = t1.addOrReplaceChild("t2", CubeListBuilder.create().texOffs(60, 2).mirror().addBox(0.0F, -0.4486F, -0.6017F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -0.091F, 0.0551F, 0.1538F, 0.8499F, -0.0822F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(1.0F, 0.2142F, -3.0829F));

		PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(58, 57).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 28).addBox(-5.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.7436F, 0.0979F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(32, 44).addBox(-2.0F, -1.0357F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 6.0157F, -1.3679F, 0.1392F, 0.346F, 0.0475F));

		PartDefinition cube_r22 = body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, -1.0357F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 3.2892F, -1.7268F, 0.2507F, 0.2776F, 0.4074F));

		PartDefinition cube_r23 = body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(36, 18).addBox(-2.0F, -0.9643F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 8.6714F, -1.0183F, 0.0072F, 0.3718F, -0.3246F));

		PartDefinition cube_r24 = body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -1.0357F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 3.2892F, -1.7268F, 0.2507F, -0.2776F, -0.4074F));

		PartDefinition cube_r25 = body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(24, 4).addBox(0.0F, -1.0357F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 6.0157F, -1.3679F, 0.1392F, -0.346F, -0.0475F));

		PartDefinition cube_r26 = body.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(20, 32).addBox(0.0F, -0.9643F, -1.8586F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 8.6714F, -1.0183F, 0.0072F, -0.3718F, 0.3246F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7772F, -3.2134F, 0.4189F, 0.103F, -0.3341F));

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.hat.visible = false;

		float speed = 1.1F;

		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch /  ( 90F / (float) Math.PI);

		this.right_arm.getChild("bone6").zRot = Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;
		this.right_arm.getChild("bone5").zRot = -Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;
		this.right_arm.getChild("bone4").zRot = Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;

		this.left_arm.getChild("bone").zRot = -Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;
		this.left_arm.getChild("bone2").zRot = Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;
		this.left_arm.getChild("bone3").zRot = -Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount * speed;

		if (entity.isAggressive()) {
			this.right_arm.xRot = -90F - (Mth.sin(ageInTicks / 4) / 7);
			this.left_arm.xRot = -90F - (Mth.sin(ageInTicks / 4) / 7);

			speed = 1.45F;
		}
		else if (entity.isDeadOrDying()) {
			this.right_arm.xRot = Mth.cos(limbSwing * 0.8F) * -2.5F * limbSwingAmount * 1000;
			this.right_arm.yRot = Mth.cos(limbSwing * 0.8F) * -2.5F * limbSwingAmount * 1000;

			this.left_arm.xRot = Mth.cos(limbSwing * 0.8F) * 2.5F * limbSwingAmount * 1000;
			this.left_arm.yRot = Mth.cos(limbSwing * 0.8F) * 2.5F * limbSwingAmount * 1000;

			this.head.xRot = Mth.cos(limbSwing * 0.8F) * 2.5F * limbSwingAmount * 1000;
			this.head.yRot = Mth.cos(limbSwing * 0.8F) * 2.5F * limbSwingAmount * 1000;
			this.head.zRot = Mth.cos(limbSwing * 0.8F) * 2.5F * limbSwingAmount * 1000;

			speed = 3.55F;
		}
		else {

			this.right_arm.xRot = Mth.cos(limbSwing * 0.8F) * 0.8F * limbSwingAmount;
			this.left_arm.xRot = Mth.cos(limbSwing * 0.8F) * -0.8F * limbSwingAmount;
		}

		if (!(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F)) {
			this.right_arm.zRot = 0;
			this.left_arm.zRot = 0;

			speed = 1.35F;
		} else {
			this.right_arm.zRot = Mth.sin(ageInTicks/8)/10;
			this.left_arm.zRot = -Mth.sin(ageInTicks/8)/10;

			speed = 1.25F;
		}

		this.left_leg.xRot = Mth.cos(limbSwing * 0.8F) * 0.9F * limbSwingAmount;
		this.right_leg.xRot = Mth.cos(limbSwing * 0.8F) * -0.9F * limbSwingAmount;

	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Tentacle1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}