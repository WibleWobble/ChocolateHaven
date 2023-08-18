package com.wiblewobble.chocolatehaven.block.entity.slots;

import com.wiblewobble.chocolatehaven.screen.CocoaFermenterMenu;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CocoaFermenterFuelSlot extends SlotItemHandler {

    private final CocoaFermenterMenu menu;
    public CocoaFermenterFuelSlot(IItemHandler handler, CocoaFermenterMenu menu, int index, int xPos, int yPos) {
        super(handler, index, xPos, yPos);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.menu.isFuel(itemStack);
    }
}
