package net.tbkiron.guineapigsplus.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tbkiron.guineapigsplus.entity.client.GuineaPigModel;
import net.tbkiron.guineapigsplus.entity.client.ModModelLayers;
import net.tbkiron.guineapigsplus.GuineaPigsPlus;

@Mod.EventBusSubscriber(modid = GuineaPigsPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.GUINEA_PIG_LAYER, GuineaPigModel::createBodyLayer);
    }
}
