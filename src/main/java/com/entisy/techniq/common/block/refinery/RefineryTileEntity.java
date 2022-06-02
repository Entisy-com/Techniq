package com.entisy.techniq.common.block.refinery;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterBlock;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.capabilities.fluid.CapabilityFluid;
import com.entisy.techniq.core.capabilities.fluid.FluidStorageImpl;
import com.entisy.techniq.core.capabilities.fluid.IFluidHandler;
import com.entisy.techniq.core.init.ModItems;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;

public class RefineryTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler, IFluidHandler {

    public int currentFluid = 0;
    public int bucketProgress = 0;
    public FluidStack type = FluidStack.EMPTY; // TODO: FluidType to make recipes for multiple fluids

    public RefineryTileEntity() {
        super(3, 200, 0, ModTileEntityTypes.REFINERY_TILE_ENTITY.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new RefineryContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = iEnergyStorage.getEnergyStored();
            });
            fluid.ifPresent(iFluidStorage -> {
                currentFluid = iFluidStorage.getFluidStored();
            });
            // Filling up stuff
            if (inventory.getItem(0).sameItem(ModItems.OIL_BUCKET.get().getDefaultInstance())) {
                if ((inventory.getItem(1) == ItemStack.EMPTY || inventory.getItem(1).sameItem(Items.AIR.getDefaultInstance())) || inventory.getItem(1).getCount() < 16) {
                    if ((currentFluid + 1000) <= maxFluid) {
                        if (bucketProgress < getMaxBucket()) {
                            level.setBlockAndUpdate(getBlockPos(), getBlockState());
                            bucketProgress++;
                            dirty = true;
                        } else {
                            inventory.setStackInSlot(0, ItemStack.EMPTY);
                            inventory.insertItem(1, Items.BUCKET.getDefaultInstance(), false);
                            fluid.ifPresent(iFluidStorage -> {
                                ((FluidStorageImpl) iFluidStorage).setFluidDirectly(iFluidStorage.getFluidStored() + 1000);
                                currentFluid = iFluidStorage.getFluidStored();
                            });
                            level.setBlockAndUpdate(getBlockPos(), getBlockState());
                            bucketProgress = 0;
                            dirty = true;
                        }
                    }
                }
            }
            // recipe stuff
            if (currentEnergy >= getRequiredEnergy()) {
                if (currentFluid >= getRequiredFluid()) {
                    if (tryMoveStack()) {
                        if (currentSmeltTime != getMaxWorkTime()) {
                            energy.ifPresent(iEnergyStorage -> {
                                currentEnergy = iEnergyStorage.getEnergyStored();
                            });
                            level.setBlockAndUpdate(getBlockPos(), getBlockState());
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(RefineryBlock.LIT, true));
                            currentSmeltTime++;
                            dirty = true;
                        } else {
                            fluid.ifPresent(iFluidStorage -> {
                                ((FluidStorageImpl) iFluidStorage).setFluidDirectly(iFluidStorage.getFluidStored() - getRequiredFluid());
                                currentFluid = iFluidStorage.getFluidStored();
                            });
                            energy.ifPresent(iEnergyStorage -> {
                                ((EnergyStorageImpl) iEnergyStorage).setEnergyDirectly(iEnergyStorage.getEnergyStored() - getRequiredEnergy());
                                currentEnergy = iEnergyStorage.getEnergyStored();
                            });
                            level.setBlockAndUpdate(getBlockPos(), getBlockState());
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(AlloySmelterBlock.LIT, false));
                            currentSmeltTime = 0;
                            ItemStack output = ModItems.PLASTIC_PIECE.get().getDefaultInstance();
                            inventory.insertItem(2, output.copy(), false);
                            dirty = true;
                        }
                    }
                }
            }
        }
        if (dirty) {
            setChanged();
            level.setBlockAndUpdate(getBlockPos(), getBlockState());
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
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".refinery");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);

        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);

    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    public int getMaxWorkTime() {
        return 100;
    }

    public int getMaxBucket() {
        return 5;
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }

    public int getWorkTimeInSeconds() {
        return getMaxWorkTime() / 20;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == CapabilityFluid.FLUID) {
            return fluid.cast();
        } else if (capability == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return null;
    }

    private boolean tryMoveStack() {
        if (inventory.getItem(2).getCount() < 64 || (inventory.getItem(2).getItem() == Items.AIR) || inventory.getItem(2).getStack() == ItemStack.EMPTY) {
            return true;
        }
        return false;
    }

    public int getRequiredEnergy() {
        return 500;
    }

    public int getRequiredFluid() {
        return 500;
    }

    private FluidStorageImpl createFluid(int capacity) {
        return new FluidStorageImpl(capacity, 1000, 1000, this);
    }

    @Override
    public FluidStorageImpl getFluidImpl() {
        return fluidStorage;
    }


}
