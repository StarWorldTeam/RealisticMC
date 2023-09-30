package team.starworld.realisticmc.api.item.armor.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.Lazy;

import static net.minecraft.client.model.geom.LayerDefinitions.OUTER_ARMOR_DEFORMATION;
import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class ArmorFullModel <T extends LivingEntity> extends HumanoidArmorModel <T> {

    public static final ModelLayerLocation FULL_ARMOR_LAYER = new ModelLayerLocation(rl("full_armor"), "outer_armor");
    public static final Lazy <HumanoidModel <LivingEntity>> INSTANCE = Lazy.of(() -> new HumanoidModel <> (Minecraft.getInstance().getEntityModels().bakeLayer(FULL_ARMOR_LAYER)));

    public ArmorFullModel (ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {

        var scale = OUTER_ARMOR_DEFORMATION;
        var mesh = HumanoidModel.createMesh(scale, 0f);
        var part = mesh.getRoot();

        part.addOrReplaceChild(
            "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -8f, -4f, 8f, 8f, 8f, scale.extend(0.5f)), PartPose.offset(0, 0, 0)
        );
        part.addOrReplaceChild(
            "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4f, -8f, -4f, 8f, 8f, 8f, scale.extend(0.5F)), PartPose.offset(0, 0, 0)
        );
        part.addOrReplaceChild(
            "right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3f, -2f, -2f, 4f, 12f, 4f, scale.extend(-0.5f)), PartPose.offset(-5f, 2f, 0)
        );
        part.addOrReplaceChild(
            "left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1f, -2f, -2f, 4f, 12f, 4f, scale.extend(-0.5f)), PartPose.offset(5f, 2f, 0)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }

}
