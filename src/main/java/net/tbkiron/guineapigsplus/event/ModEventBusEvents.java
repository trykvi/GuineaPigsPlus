package net.tbkiron.guineapigsplus.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tbkiron.guineapigsplus.entity.ModEntities;
import net.tbkiron.guineapigsplus.entity.custom.GuineaPigEntity;
import net.tbkiron.guineapigsplus.GuineaPigsPlus;

@Mod.EventBusSubscriber(modid = GuineaPigsPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.GUINEA_PIG.get(), GuineaPigEntity.createAttributes().build());
    }
}
