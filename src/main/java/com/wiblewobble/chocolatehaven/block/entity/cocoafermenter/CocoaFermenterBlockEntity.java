package com.wiblewobble.chocolatehaven.block.entity.cocoafermenter;

import com.wiblewobble.chocolatehaven.api.Utils;
import com.wiblewobble.chocolatehaven.api.blockentity.AbstractModBlockEntity;
import com.wiblewobble.chocolatehaven.block.entity.ModBlockEntities;
import com.wiblewobble.chocolatehaven.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CocoaFermenterBlockEntity extends AbstractModBlockEntity implements MenuProvider {
    private int progress = 0;
    private int maxProgress = 100; //remove with recipes?


    public CocoaFermenterBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntities.COCOA_FERMENTER.get(), position, state, "Cocoa Fermenter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CocoaFermenterMenu(id, inventory, this, this.data);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CocoaFermenterBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        if (hasRecipe(entity)) {
            entity.progress++;
            entity.data.set(0, entity.progress);
            setChanged(level,pos,state);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level,pos,state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.data.set(0, 0);
    }

    private static void craftItem(CocoaFermenterBlockEntity entity) {
        if (hasRecipe(entity)) {
            entity.getStackHandler().extractItem(0, 1, false);
            entity.getStackHandler().setStackInSlot(2, new ItemStack(ModItems.FERMENTED_COCOA_BEANS.get(),
                    entity.getStackHandler().getStackInSlot(2).getCount() + 1));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(CocoaFermenterBlockEntity entity) {
        SimpleContainer inventory = Utils.containerate(entity);

        boolean hasCocoaBeansInFirstSlot = entity.getStackHandler().getStackInSlot(0).getItem() == Items.COCOA_BEANS;

        return hasCocoaBeansInFirstSlot && canOutput(inventory, new ItemStack(ModItems.FERMENTED_COCOA_BEANS.get(), 1));
    }

    private static boolean canOutput(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount() &&
                inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }
}
