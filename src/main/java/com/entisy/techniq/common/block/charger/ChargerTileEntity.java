package com.entisy.techniq.common.block.charger;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.common.item.energy.EnergyItem;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class ChargerTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    private static final int MAX_ENERGY_WORKING_TICK = 20;

    public ChargerTileEntity(TileEntityType<?> type) {
        super(1, 500, 500, type);
    }

    public ChargerTileEntity() {
        this(ModTileEntityTypes.CHARGER_TILE_ENTITY.get());
    }

    @Nullable
    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new ChargerContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide) {
            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = energyStorage.getEnergyStored();
            });
            if (currentEnergy > 7) {
                if (isValidItem(inventory.getItem(0))) {
                    if (((EnergyItem) inventory.getItem(0).getItem()).isChargable()) {
                        IEnergyStorage itemEnergy = inventory.getItem(0).getCapability(CapabilityEnergy.ENERGY).orElseThrow(IllegalStateException::new);
                        if (itemEnergy.getEnergyStored() < itemEnergy.getMaxEnergyStored()) {
                            inventory.getItem(0).getCapability(CapabilityEnergy.ENERGY)
                                    .ifPresent(e -> e.receiveEnergy(10, false));
                            energy.ifPresent(iEnergyStorage -> {
                                energyStorage.setEnergyDirectly(energyStorage.getEnergyStored() - 10);
                                currentEnergy = energyStorage.getEnergyStored();
                            });
                            level.setBlockAndUpdate(getBlockPos(), getBlockState());
                            dirty = true;
                        }
                    }
                }
            } else {
                level.setBlockAndUpdate(getBlockPos(), getBlockState());
                dirty = true;
            }

        }
        if (dirty) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private boolean isValidItem(ItemStack stack){
        return stack.getItem() != Items.AIR && !inventory.isEmpty();
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".battery");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}
