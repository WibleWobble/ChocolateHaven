package com.wiblewobble.chocolatehaven.block.entity;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import com.wiblewobble.chocolatehaven.block.ModBlocks;
import com.wiblewobble.chocolatehaven.block.entity.cocoafermenter.CocoaFermenterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChocolateHaven.MODID);

    public static final RegistryObject<BlockEntityType<CocoaFermenterBlockEntity>> COCOA_FERMENTER =
            BLOCK_ENTITIES.register("cocoa_fermenter", () ->
                    BlockEntityType.Builder.of(CocoaFermenterBlockEntity::new,
                            ModBlocks.COCOA_FERMENTER.get()).build(null));

    public static void registerRegistry(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
