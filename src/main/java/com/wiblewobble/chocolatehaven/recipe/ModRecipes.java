package com.wiblewobble.chocolatehaven.recipe;

import com.wiblewobble.chocolatehaven.ChocolateHaven;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChocolateHaven.MODID);

    public static final RegistryObject<RecipeSerializer<CocoaFermenterRecipe>> COCOA_FERMENTER_SERIALIZER =
            SERIALIZERS.register("cocoa_fermenting", () -> CocoaFermenterRecipe.Serializer.INSTANCE);

    public static void registerRegistry(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
