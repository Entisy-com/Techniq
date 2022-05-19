package com.entisy.techniq.common.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.entisy.techniq.common.slots.OutputSlot;
import com.entisy.techniq.common.tileentity.MetalPressTileEntity;
import com.entisy.techniq.core.init.BlockInit;
import com.entisy.techniq.core.init.ContainerTypesInit;
import com.entisy.techniq.core.util.FunctionalIntReferenceHolder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.SlotItemHandler;

public class MetalPressContainer extends Container {

	private MetalPressTileEntity tileEntity;
	private IWorldPosCallable canInteractWithCallable;
	public FunctionalIntReferenceHolder currentSmeltTime;
	public FunctionalIntReferenceHolder currentEnergy;

	public MetalPressContainer(final int id, final PlayerInventory inv,
			final MetalPressTileEntity tileEntity) {
		super(ContainerTypesInit.METAL_PRESS_CONTAINER_TYPE.get(), id);
		this.tileEntity = tileEntity;
		canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());

		final int slotSizePlus2 = 18;
		final int startX = 8;

		// furnace
		addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 56, 35));
		addSlot(new OutputSlot(tileEntity.getInventory(), 1, 116, 35));

		// inventory
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
			}
		}

		// hotbar
		int hotbarY = 142;
		for (int column = 0; column < 9; column++) {
			addSlot(new Slot(inv, column, startX + (column * slotSizePlus2), hotbarY));
		}

		addDataSlot(currentSmeltTime = new FunctionalIntReferenceHolder(() -> tileEntity.currentSmeltTime,
				value -> tileEntity.currentSmeltTime = value));
		addDataSlot(currentEnergy = new FunctionalIntReferenceHolder(() -> tileEntity.currentEnergy,
				value -> tileEntity.currentEnergy = value));
	}

	public MetalPressContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
		this(id, inv, getTileEntity(inv, buffer));
	}

	private static MetalPressTileEntity getTileEntity(PlayerInventory inv, PacketBuffer buffer) {
		Objects.requireNonNull(inv, "Inventory cannot be null");
		Objects.requireNonNull(buffer, "PacketBuffer cannot be null");
		final TileEntity tileEntity = inv.player.level.getBlockEntity(buffer.readBlockPos());
		if (tileEntity instanceof MetalPressTileEntity) {
			return (MetalPressTileEntity) tileEntity;
		}
		throw new IllegalStateException("TileEntity is not correct!");
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return stillValid(canInteractWithCallable, player, BlockInit.METAL_PRESS.get());
	}
	
	@Nonnull
	@Override
	public ItemStack quickMoveStack(final PlayerEntity player, final int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			final ItemStack slotStack = slot.getItem();
			returnStack = slotStack.copy();

			final int containerSlots = this.slots.size() - player.inventory.items.size();
			if (index < containerSlots) {
				if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (slotStack.getCount() == 0) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}

	@OnlyIn(Dist.CLIENT)
	public int getSmeltProgressionScaled() {
		return currentSmeltTime.get() != 0 && tileEntity.maxSmeltTime != 0
				? currentSmeltTime.get() * 24 / tileEntity.maxSmeltTime
				: 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getEnergyUpdate() {
		return currentEnergy.get() != 0 && tileEntity.maxEnergy != 0
				? currentEnergy.get() * 50 / tileEntity.maxEnergy
				: 0;
	}

	public LazyOptional<IEnergyStorage> getCapabilityFromTE(){
		return this.tileEntity.getCapability(CapabilityEnergy.ENERGY);
	}
}
