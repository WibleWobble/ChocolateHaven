package com.wiblewobble.chocolatehaven.item;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.SharedLibrary;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChocolateHaven.MODID);

    public static final RegistryObject<Item> FERMENTED_COCOA_BEANS = registerItem("fermented_cocoa_beans",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .fast()
                            .nutrition(2)
                            .saturationMod(1)
                            .build())));

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void registerRegistry(IEventBus bus) {
        ITEMS.register(bus);
    }
}
