package com.entisy.techniq.common.slots;

import com.entisy.techniq.common.item.upgrades.UpgradeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class BackpackSlot extends Slot {

    private static IInventory emptyInventory = new Inventory(0);
    private final IItemHandler itemHandler;
    private final int index;

    public BackpackSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getItem() {
        return this.getItemHandler().getStackInSlot(index);
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        ((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
        this.setChanged();
    }

    @Override
    public void onQuickCraft(@Nonnull ItemStack oldStackIn, @Nonnull ItemStack newStackIn) {

    }

    @Override
    public int getMaxStackSize() {
        return 256;
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);

        IItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(index);
        if (handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

            handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);

            ItemStack remainder = handlerModifiable.insertItem(index, maxAdd, true);

            handlerModifiable.setStackInSlot(index, currentStack);

            return maxInput - remainder.getCount();
        } else {
            ItemStack remainder = handler.insertItem(index, maxAdd, true);

            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }

    @Override
    public boolean mayPickup(PlayerEntity playerIn) {
        final int stackCount = getItemHandler().getStackInSlot(index).getCount();
        final ItemStack stack = getItemHandler().getStackInSlot(index);

        if (stackCount >= stack.getMaxStackSize()) {
            return !this.getItemHandler().extractItem(index, stack.getMaxStackSize(), true).isEmpty();
        }
        return !this.getItemHandler().extractItem(index, stackCount, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        return this.getItemHandler().extractItem(index, amount, false);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }
}
