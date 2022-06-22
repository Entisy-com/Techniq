package com.entisy.techniq.common.block.fluidStorage;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.capabilities.fluid.FluidStorageImpl;
import com.entisy.techniq.core.capabilities.fluid.IFluidHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.EnergyUtils;
import com.entisy.techniq.core.util.FluidUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class FluidStorageTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IFluidHandler {

    private static final int MAX_ENERGY_WORKING_TICK = 200;

    public FluidStorageTileEntity(TileEntityType<?> type) {
        super(0, 500, 500, type);
    }

    public FluidStorageTileEntity() {
        this(ModTileEntityTypes.FLUID_STORAGE_TILE_ENTITY.get());
    }

    @Nullable
    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new FluidStorageContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide) {
            if(currentFluid < maxFluid) {
                if (currentSmeltTime != MAX_ENERGY_WORKING_TICK) {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState());
                    currentSmeltTime++;
                    dirty = true;
                } else {
                    fluid.ifPresent(iFluidStorage -> {
                        currentFluid = iFluidStorage.getFluidStored();
                    });
                    level.setBlockAndUpdate(getBlockPos(), getBlockState());
                    currentSmeltTime = 0;
                    dirty = true;
                }
            } else {
                level.setBlockAndUpdate(getBlockPos(), getBlockState());
                currentSmeltTime = 0;
                dirty = true;
            }
        }
        if (dirty) {
            //TODO: Fix game crash
//            if (maxFluidExtract > 0) {
//                FluidUtils.trySendToNeighbors(level, worldPosition, this, maxEnergyExtract);
//            }
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".fluid_storage");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Override
    public FluidStorageImpl getFluidImpl() {
        return fluidStorage;
    }
}
