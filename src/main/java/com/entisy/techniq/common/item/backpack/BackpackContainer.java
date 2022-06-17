package com.entisy.techniq.common.item.backpack;

import com.entisy.techniq.common.slots.BackpackSlot;
import com.entisy.techniq.common.slots.BatterySlot;
import com.entisy.techniq.common.slots.UpgradeSlot;
import com.entisy.techniq.core.init.ModContainerTypes;
import com.entisy.techniq.core.util.FunctionalIntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;

public class BackpackContainer extends Container {

    public BackpackItem backpackItem;
    public FunctionalIntReferenceHolder currentSmeltTime;
    public FunctionalIntReferenceHolder currentEnergy;

    public BackpackContainer(final int id, final PlayerInventory inv, final BackpackItem backpackItem) {
        super(ModContainerTypes.BACKPACK_CONTAINER_TYPE.get(), id);
        this.backpackItem = backpackItem;

        addSlot(new BatterySlot(backpackItem.getInventory(), 0, 174, 72));
        addSlot(new UpgradeSlot(backpackItem.getInventory(), 1, 174, 104));
        addSlot(new UpgradeSlot(backpackItem.getInventory(), 2, 174, 122));
        addSlot(new UpgradeSlot(backpackItem.getInventory(), 3, 174, 140));
        makeSlots(backpackItem, backpackItem.getInventory(), 4, 9, 8, 18, 104, 162);

    }

    public BackpackContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
        this(id, inv, getBackPackItem(inv));
    }

    private static BackpackItem getBackPackItem(PlayerInventory inv) {
        return (BackpackItem) inv.getSelected().getItem();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.slots.get(index);
//        if (slot != null && slot.hasItem()) {
//            ItemStack itemstack1 = slot.getItem();
//            itemstack = itemstack1.copy();
//
//            final int inventorySize = 2;
//            final int playerInventoryEnd = inventorySize + 27;
//            final int playerHotbarEnd = playerInventoryEnd + 9;
//
//            if (index == 1) {
//                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
//                    return ItemStack.EMPTY;
//                }
//                slot.onQuickCraft(itemstack1, itemstack);
//            } else if (index != 0) {
//                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, false)) {
//                return ItemStack.EMPTY;
//            }
//            if (itemstack1.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//            if (itemstack1.getCount() == itemstack.getCount()) {
//                return ItemStack.EMPTY;
//            }
//            slot.onTake(player, itemstack1);
//        }
//        return itemstack;
        return null;
    }

//    public LazyOptional<IEnergyStorage> getCapabilityFromTE() {
//        return this.tileEntity.getCapability(CapabilityEnergy.ENERGY);
//    }

    private void makeSlots(IInventory inventory, IItemHandler inv, int rows, int columns, int startX, int startY, int inventoryY, int hotbarY) {
        final int slotSizePlus2 = 18;
        //backpack
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                addSlot(new BackpackSlot(inv, 4 +(column + row * 9 + 9), startX + column * slotSizePlus2, startY + row * slotSizePlus2));
            }
        }
        //inventory
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, 4 + 36 +(column + row * 9 + 9), startX + column * slotSizePlus2, inventoryY + row * slotSizePlus2));
            }
        }
        //hotbar
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, startX + (column * slotSizePlus2), hotbarY));
        }
    }
}
