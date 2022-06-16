package com.entisy.techniq.common.block.melter;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.capabilities.fluid.FluidStorageImpl;
import com.entisy.techniq.core.capabilities.fluid.IFluidHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class MelterTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler, IFluidHandler {

    private int workTime = 20;

    public MelterTileEntity() {
        super(2, 500, 0, ModTileEntityTypes.MELTER_TILE_ENTITY.get());
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".melter");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new MelterContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide) {
            energy.ifPresent(iEnergyStorage -> currentEnergy = iEnergyStorage.getEnergyStored());
            fluid.ifPresent(iFluidStorage -> currentFluid = iFluidStorage.getFluidStored());
            if (currentEnergy >= getRequiredEnergy()){
                if (currentFluid + 1000 < fluidStorage.getMaxFluidStored()){
                    if (currentSmeltTime != workTime){
                        currentSmeltTime++;
                        dirty = true;
                    } else {
                        currentSmeltTime = 0;
                    }
                }
            }
        }
        if(dirty){
            setChanged();
            level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private int getRequiredEnergy() {
        return 100;
    }

    @Override
    public FluidStorageImpl getFluidImpl() {
        return fluidStorage;
    }
}
