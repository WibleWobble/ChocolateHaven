package com.wiblewobble.chocolatehaven.block.entity;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import com.wiblewobble.chocolatehaven.block.entity.cocoafermenter.CocoaFermenterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ChocolateHaven.MODID);

    public static final RegistryObject<MenuType<CocoaFermenterMenu>> COCOA_FERMENTER_MENU =
            registerMenuType(CocoaFermenterMenu::new, "cocoa_fermenter_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void registerRegistry(IEventBus bus) {
        MENUS.register(bus);
    }

}
