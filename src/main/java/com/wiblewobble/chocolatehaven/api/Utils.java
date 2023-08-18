package com.wiblewobble.chocolatehaven.api;

import com.wiblewobble.chocolatehaven.api.blockentity.AbstractModBlockEntity;
import net.minecraft.world.SimpleContainer;

public class Utils {
    public static <T extends AbstractModBlockEntity> SimpleContainer containerate(T entity) {
        SimpleContainer inventory = new SimpleContainer(entity.getStackHandler().getSlots());
        for (int i = 0; i < entity.getStackHandler().getSlots(); i++) {
            inventory.setItem(i, entity.getStackHandler().getStackInSlot(i));
        }
        return inventory;
    }
}
