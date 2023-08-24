package com.wiblewobble.chocolatehaven.api.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class AbstractModBlockEntity extends BlockEntity implements MenuProvider {

    private final BlockEntityType<?> type;
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3) { //TODO: UNHARDCODE
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == Items.COCOA_BEANS;
                case 1 -> stack.getItem() == Items.COAL;
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private final Map<Direction, LazyOptional<WrappedHandler>> sidedInventoryConfigs;
    private LazyOptional<IItemHandler> lazyItemHandler;
    protected final ContainerData data;
    private final String name;

    public AbstractModBlockEntity(ModBlockEntitySettings settings) {
        super(settings.type, settings.position, settings.state);
        this.type = settings.type;
        this.sidedInventoryConfigs = settings.sidedInventoryConfigs;
        this.lazyItemHandler = settings.lazyItemHandler;
        this.data = settings.data;
        this.name = settings.name;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(name);
    }

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
            if (sidedInventoryConfigs != null) {
                if (sidedInventoryConfigs.containsKey(side)) {
                    Direction localDir = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

                    if (side == Direction.UP || side == Direction.DOWN) {
                        return sidedInventoryConfigs.get(side).cast();
                    }

                    return switch (localDir) {
                        default -> sidedInventoryConfigs.get(side.getOpposite()).cast();
                        case EAST -> sidedInventoryConfigs.get(side.getClockWise()).cast();
                        case SOUTH -> sidedInventoryConfigs.get(side).cast();
                        case WEST -> sidedInventoryConfigs.get(side.getCounterClockWise()).cast();
                    };
                }
            }
        }

        return super.getCapability(cap,side);
    }

    public ItemStackHandler getStackHandler() {
        return this.itemStackHandler;
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemStackHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
