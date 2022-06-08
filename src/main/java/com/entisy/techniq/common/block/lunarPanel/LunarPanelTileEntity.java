package com.entisy.techniq.common.block.lunarPanel;

import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.init.TechniqConfig;
import com.entisy.techniq.core.util.EnergyUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

public class LunarPanelTileEntity extends MachineTileEntity implements ITickableTileEntity, IEnergyHandler {

    private int cap = TechniqConfig.LUNAR_PANEL.get();

    public LunarPanelTileEntity() {
        super(0, 0, 0, ModTileEntityTypes.LUNAR_PANEL_TILE_ENTITY.get());
        maxEnergyExtract = cap;
        maxEnergyReceive = cap;
        energyStorage = createEnergy(cap);
        energy = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide()) {
            if (maxEnergyExtract >= cap) {
                EnergyUtils.trySendToNeighbors(level, worldPosition, this, maxEnergyExtract);
            }
            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = iEnergyStorage.getEnergyStored();
            });

            if (level.isNight()) {
                if (currentEnergy + cap <= maxEnergy) {
                        energy.ifPresent(iEnergyStorage -> {
                            energyStorage.setEnergyDirectly(energyStorage.getEnergyStored() + cap);
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

    private EnergyStorageImpl createEnergy(int capacity) {
        return new EnergyStorageImpl(capacity, maxEnergyReceive, maxEnergyExtract, this);
    }
}