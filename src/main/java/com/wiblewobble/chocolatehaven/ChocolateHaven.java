package com.wiblewobble.chocolatehaven;

import com.mojang.logging.LogUtils;
import com.wiblewobble.chocolatehaven.block.ModBlocks;
import com.wiblewobble.chocolatehaven.block.entity.ModBlockEntities;
import com.wiblewobble.chocolatehaven.item.ModCreativeTab;
import com.wiblewobble.chocolatehaven.item.ModItems;
import com.wiblewobble.chocolatehaven.recipe.ModRecipes;
import com.wiblewobble.chocolatehaven.screen.CocoaFermenterMenu;
import com.wiblewobble.chocolatehaven.screen.CocoaFermenterScreen;
import com.wiblewobble.chocolatehaven.screen.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChocolateHaven.MODID)
public class ChocolateHaven
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "chocolatehaven";
    // Directly reference a slf4j logge
    private static final Logger LOGGER = LogUtils.getLogger();

    public ChocolateHaven()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        ModItems.registerRegistry(modEventBus);
        ModBlocks.registerRegistry(modEventBus);
        ModBlockEntities.registerRegistry(modEventBus);
        ModCreativeTab.registerRegistry(modEventBus);
        ModMenuTypes.registerRegistry(modEventBus);
        ModRecipes.registerRegistry(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            MenuScreens.register(ModMenuTypes.COCOA_FERMENTER_MENU.get(), CocoaFermenterScreen::new);
        }
    }
}
