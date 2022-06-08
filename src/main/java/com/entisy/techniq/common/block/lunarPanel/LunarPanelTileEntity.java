package com.entisy.techniq.common.block.lunarPanel;

import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.EnergyUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;

public class LunarPanelTileEntity extends MachineTileEntity implements ITickableTileEntity, IEnergyHandler {

    public LunarPanelTileEntity() {
        super(0, 0, 15, ModTileEntityTypes.LUNAR_PANEL_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide()) {
            if (maxEnergyExtract > 0) {
                EnergyUtils.trySendToNeighbors(level, worldPosition, this, maxEnergyExtract);
            }

            if (level.isNight()) {
                if (currentEnergy + 1 <= maxEnergy) {
                    energy.ifPresent(iEnergyStorage -> {
                        energyStorage.setEnergyDirectly(energyStorage.getEnergyStored() + 1);
                    });
                }
            }
        }
    }

    public void addEnergy(int amount) {
        if (currentEnergy + amount <= maxEnergy) {
            energy.ifPresent(iEnergyStorage -> {
                energyStorage.setEnergyDirectly(energyStorage.getEnergyStored() + amount);
            });
        }
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}