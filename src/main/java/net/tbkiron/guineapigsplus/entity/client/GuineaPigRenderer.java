package net.tbkiron.guineapigsplus.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tbkiron.guineapigsplus.entity.custom.GuineaPigEntity;
import net.tbkiron.guineapigsplus.GuineaPigsPlus;
import net.tbkiron.guineapigsplus.entity.variant.GuineaPigVariant;

import java.util.Map;

public class GuineaPigRenderer extends MobRenderer<GuineaPigEntity, GuineaPigModel<GuineaPigEntity>> {

    public static final Map<GuineaPigVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GuineaPigVariant.class), (variants) -> {
                variants.put(GuineaPigVariant.FREDAG, new ResourceLocation(GuineaPigsPlus.MOD_ID, "textures/entity/fredag_texture.png"));
                variants.put(GuineaPigVariant.PLUTO, new ResourceLocation(GuineaPigsPlus.MOD_ID, "textures/entity/pluto_texture.png"));
            });
    public GuineaPigRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GuineaPigModel<>(pContext.bakeLayer(ModModelLayers.GUINEA_PIG_LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(GuineaPigEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    @Override
    public void render(GuineaPigEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        if(pEntity.isBaby()){
            pMatrixStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
