package com.wiblewobble.chocolatehaven.block;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import com.wiblewobble.chocolatehaven.block.custom.CocoaFermenterBlock;
import com.wiblewobble.chocolatehaven.block.entity.cocoafermenter.CocoaFermenterBlockEntity;
import com.wiblewobble.chocolatehaven.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChocolateHaven.MODID);

    public static final RegistryObject<Block> COCOA_FERMENTER = registerBlock("cocoa_fermenter",
            () -> new CocoaFermenterBlock(CocoaFermenterBlockEntity.class, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> reg = BLOCKS.register(name, block);
        registerBlockItem(name, reg);
        return reg;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerRegistry(IEventBus bus) {
        BLOCKS.register(bus);
    }
}