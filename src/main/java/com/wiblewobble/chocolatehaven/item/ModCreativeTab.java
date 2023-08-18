package com.wiblewobble.chocolatehaven.item;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import com.wiblewobble.chocolatehaven.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChocolateHaven.MODID);

    public static final RegistryObject<CreativeModeTab> CHOCOLATE_HAVEN_TAB = TABS.register("chocolate_haven_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.COCOA_FERMENTER.get()))
                    .title(Component.translatable("chocolatehaven.creativetab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.FERMENTED_COCOA_BEANS.get());

                        pOutput.accept(ModBlocks.COCOA_FERMENTER.get());
                    })
                    .build());

    public static void registerRegistry(IEventBus bus) {
        TABS.register(bus);
    }
}
