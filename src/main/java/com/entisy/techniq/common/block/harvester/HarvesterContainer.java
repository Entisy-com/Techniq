package com.entisy.techniq.common.block.harvester;

import com.entisy.techniq.common.slots.OutputSlot;
import com.entisy.techniq.common.slots.UpgradeSlot;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModContainerTypes;
import com.entisy.techniq.core.util.FunctionalIntReferenceHolder;
import com.entisy.techniq.core.util.entisy.SimpleList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Objects;

public class HarvesterContainer extends Container {

    public final HarvesterTileEntity tileEntity;
    private static SimpleList<Slot> slotList = new SimpleList<>();
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder currentSmeltTime;
    public FunctionalIntReferenceHolder currentEnergy;

    public HarvesterContainer(final int id, final PlayerInventory inv, final HarvesterTileEntity tileEntity) {
        super(ModContainerTypes.HARVESTER_CONTAINER_TYPE.get(), id);
        this.tileEntity = tileEntity;
        canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        final int slotSizePlus2 = 18;
        final int startX = 8;

        // furnace
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 0, 62, 27)));
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 1, 80, 27)));
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 2, 98, 27)));
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 3, 62, 45)));
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 4, 80, 45)));
        slotList.append(addSlot(new OutputSlot(tileEntity.getInventory(), 5, 98, 45)));

        slotList.append(addSlot(new UpgradeSlot(tileEntity.getInventory(), 6, 9, 27)));
        slotList.append(addSlot(new UpgradeSlot(tileEntity.getInventory(), 7, 27, 27)));

        // inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * slotSizePlus2, 166 - (4 - row) * slotSizePlus2 - 10));
            }
        }

        // hotbar
        int hotbarY = 142;
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inv, column, startX + (column * slotSizePlus2), hotbarY));
        }

        addDataSlot(currentSmeltTime = new FunctionalIntReferenceHolder(() -> tileEntity.currentSmeltTime, value -> tileEntity.currentSmeltTime = value));
        addDataSlot(currentEnergy = new FunctionalIntReferenceHolder(() -> tileEntity.currentEnergy, value -> tileEntity.currentEnergy = value));
    }

    public HarvesterContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
        this(id, inv, getTileEntity(inv, buffer));
    }

    private static HarvesterTileEntity getTileEntity(PlayerInventory inv, PacketBuffer buffer) {
        Objects.requireNonNull(inv, "Inventory cannot be null");
        Objects.requireNonNull(buffer, "PacketBuffer cannot be null");
        final TileEntity tileEntity = inv.player.level.getBlockEntity(buffer.readBlockPos());
        if (tileEntity instanceof HarvesterTileEntity) {
            return (HarvesterTileEntity) tileEntity;
        }
        throw new IllegalStateException("TileEntity is not correct!");
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(canInteractWithCallable, player, ModBlocks.HARVESTER.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();

            int slots = 6;
            int invSize = slots + 27 + 2;
            int hotbar = invSize + 9;

            itemstack = itemstack1.copy();
            if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4 || index == 5) {
                if (!this.moveItemStackTo(itemstack1, slots, hotbar, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0 && index != 1 && index != 2 && index != 3 && index != 4 && index != 5) {
                if (index >= slots && index < invSize) {
                    if (!this.moveItemStackTo(itemstack1, 0, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= invSize && index < hotbar && !this.moveItemStackTo(itemstack1, 0, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 5, 7, false)) {
                return ItemStack.EMPTY;
            }else if (!this.moveItemStackTo(itemstack1, slots, hotbar, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }

    public LazyOptional<IEnergyStorage> getCapabilityFromTE() {
        return this.tileEntity.getCapability(CapabilityEnergy.ENERGY);
    }

    public static SimpleList<Slot> getSlots() {
        return slotList;
    }
}
