package net.tbkiron.guineapigsplus.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tbkiron.guineapigsplus.entity.custom.GuineaPigEntity;
import net.tbkiron.guineapigsplus.GuineaPigsPlus;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GuineaPigsPlus.MOD_ID);

    public static final RegistryObject<EntityType<GuineaPigEntity>> GUINEA_PIG =
            ENTITY_TYPES.register("guinea_pig", () -> EntityType.Builder.of(GuineaPigEntity::new,
                    MobCategory.CREATURE)
                    .sized(0.35f, 0.35f).build("guinea_pig"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
