package net.tbkiron.guineapigsplus.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tbkiron.guineapigsplus.GuineaPigsPlus;
import net.tbkiron.guineapigsplus.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GuineaPigsPlus.MOD_ID);

    public static final RegistryObject<Item> GUINEA_PIG_SPAWN_EGG = ITEMS.register("guinea_pig_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GUINEA_PIG, 0xc29e5b, 0x6b5732,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
