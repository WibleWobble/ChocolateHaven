package com.wiblewobble.chocolatehaven.datagen;

import com.wiblewobble.chocolatehaven.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COCOA_FERMENTER.get())
                .pattern("III")
                .pattern("B B")
                .pattern("SCS")
                .define('I', Items.IRON_INGOT).define('B', Blocks.IRON_BLOCK)
                .define('S', Blocks.SMOOTH_STONE).define('C', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

    }
}
