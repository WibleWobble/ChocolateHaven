package com.wiblewobble.chocolatehaven.block.entity.cocoafermenter;

import com.wiblewobble.chocolatehaven.api.blockentity.AbstractModContainerMenu;
import com.wiblewobble.chocolatehaven.block.entity.slots.CocoaFermenterFuelSlot;
import com.wiblewobble.chocolatehaven.block.entity.slots.ModGenericOutputSlot;
import com.wiblewobble.chocolatehaven.block.ModBlocks;
import com.wiblewobble.chocolatehaven.block.entity.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class CocoaFermenterMenu extends AbstractModContainerMenu {
    public final CocoaFermenterBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public CocoaFermenterMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }
    public CocoaFermenterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.COCOA_FERMENTER_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (CocoaFermenterBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 56, 17));
            this.addSlot(new CocoaFermenterFuelSlot(handler, this, 1, 56, 53));
            this.addSlot(new ModGenericOutputSlot(handler, 2, 116, 35));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressBeanSize = 24;

        return maxProgress != 0 && progress != 0 ?
                (progress / 2 * progressBeanSize / maxProgress) * 2 : 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.COCOA_FERMENTER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public boolean isFuel(ItemStack itemStack) {
        return itemStack.getItem() == Items.COAL;
    }
}
