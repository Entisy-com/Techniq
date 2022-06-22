package com.entisy.techniq.common.block.fluidStorage;

//import com.entisy.techniq.common.block.displayCase.DisplayCaseTileEntity;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModContainerTypes;
import com.entisy.techniq.core.util.FunctionalIntReferenceHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class FluidStorageContainer extends Container {

    public FluidStorageTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder currentFluid;

    public FluidStorageContainer(final int id, final PlayerInventory inv, final FluidStorageTileEntity tileEntity) {
        super(ModContainerTypes.FLUID_STORAGE_CONTAINER_TYPE.get(), id);
        this.tileEntity = tileEntity;
        canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        final int slotSizePlus2 = 18;
        final int startX = 8;

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

        addDataSlot(currentFluid = new FunctionalIntReferenceHolder(() -> tileEntity.currentFluid, value -> tileEntity.currentFluid = value));
    }

    public FluidStorageContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
        this(id, inv, getTileEntity(inv, buffer));
    }

    private static FluidStorageTileEntity getTileEntity(PlayerInventory inv, PacketBuffer buffer) {
        Objects.requireNonNull(inv, "Inventory cannot be null");
        Objects.requireNonNull(buffer, "PacketBuffer cannot be null");
        final TileEntity tileEntity = inv.player.level.getBlockEntity(buffer.readBlockPos());
        if (tileEntity instanceof FluidStorageTileEntity) {
            return (FluidStorageTileEntity) tileEntity;
        }
        throw new IllegalStateException("TileEntity is not correct!");
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(canInteractWithCallable, player, ModBlocks.FLUID_STORAGE.get());
    }
}
