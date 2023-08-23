package com.wiblewobble.chocolatehaven.api.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ModBlockEntitySettings {
    protected BlockEntityType<?> type;
    protected ItemStackHandler itemStackHandler;
    protected Map<Direction, LazyOptional<WrappedHandler>> sidedInventoryConfigs;
    protected LazyOptional<IItemHandler> lazyItemHandler;
    protected ContainerData data;
    protected String name;
    protected AbstractContainerMenu menu;
    protected BlockPos position;
    protected BlockState state;

    private ModBlockEntitySettings(Builder builder) {
        this.type = builder.type;
        this.itemStackHandler = builder.itemStackHandler;
        this.sidedInventoryConfigs = builder.sidedInventoryConfigs;
        this.lazyItemHandler = builder.lazyItemHandler;
        this.data = builder.data;
        this.name = builder.name;
        this.menu = builder.menu;
        this.position = builder.position;
        this.state = builder.state;
    }
    public static class Builder {

        protected BlockEntityType<?> type;
        protected ItemStackHandler itemStackHandler;
        protected Map<Direction, LazyOptional<WrappedHandler>> sidedInventoryConfigs;
        protected LazyOptional<IItemHandler> lazyItemHandler;
        protected ContainerData data;
        protected String name;
        protected ArrayList<DataEntry> dataPoints;
        protected AbstractContainerMenu menu;
        protected BlockPos position;
        protected BlockState state;

        public Builder(String name, BlockPos position, BlockState state) {
            this.name = name;
            this.position = position;
            this.state = state;
        }
        public ModBlockEntitySettings build() {
            this.data = new ContainerData() {
                @Override
                public int get(int pIndex) {
                    if (pIndex >= 0 && pIndex < dataPoints.size()) {
                        return dataPoints.get(pIndex).getValue();
                    }
                    return -1;
                }

                @Override
                public void set(int pIndex, int pValue) {
                    if (pIndex >= 0 && pIndex < dataPoints.size()) {
                        DataEntry dataPoint = dataPoints.get(pIndex);
                        dataPoint.setValue(pValue);
                    }
                }

                @Override
                public int getCount() {
                    return dataPoints.size();
                }
            };
            return new ModBlockEntitySettings(this);

        }

        public Builder addDataPoint(String name, int defaultValue) {
            dataPoints.add(new DataEntry(name, defaultValue));
            return this;
        }
        public Builder addSidedInventory(Direction side,
                                         Predicate<Integer> output,
                                         BiPredicate<Integer, ItemStack> input) {
            sidedInventoryConfigs.put(side, LazyOptional.of(
                    () -> new WrappedHandler(itemStackHandler, output, input)));
            return this;
        }
        public Builder menu(AbstractContainerMenu menu) {
            this.menu = menu;
            return this;
        }

        private class DataEntry {
            private String name;
            private int value;
            public DataEntry(String name, int value) {
                this.name = name;
                this.value = value;
            }

            public String getName() {
                return name;
            }
            public int getValue() {
                return value;
            }
            public void setName(String name) {
                this.name = name;
            }
            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
