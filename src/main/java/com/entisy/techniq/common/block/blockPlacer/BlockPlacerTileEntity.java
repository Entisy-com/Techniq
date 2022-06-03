package com.entisy.techniq.common.block.blockPlacer;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.entisy.betterLists.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class BlockPlacerTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    private Direction direction;

    public BlockPlacerTileEntity() {
        super(6, 500, 0, ModTileEntityTypes.BLOCK_PLACER_TILE_ENTITY_TYPE.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new BlockPlacerContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = iEnergyStorage.getEnergyStored();
            });
            if (currentEnergy >= getRequiredEnergy()) {
                if (getBlock() == Blocks.AIR) {
                    Pair<Boolean, Integer> pair = findItem(6);
                    if (pair.getKey()) {
                        energy.ifPresent(iEnergyStorage -> {
                            energyStorage.setEnergyDirectly(currentEnergy - getRequiredEnergy());
                            currentEnergy = energyStorage.getEnergyStored();
                        });
                        direction = getBlockState().getValue(BlockPlacerBlock.FACING);

                        level.setBlockAndUpdate(getBlockPos(), getBlockState());
                        level.setBlockAndUpdate(getBlockPos().relative(direction), getResultBlock(inventory.getItem(pair.getValue()).getItem()));

                        inventory.shrink(pair.getValue(), 1);
                    }
                    dirty = true;
                }
            }
        }
        if (dirty) {
            setChanged();
            level.setBlockAndUpdate(getBlockPos(), getBlockState());
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private BlockState getResultBlock(Item item) {
        BlockState ret = Block.byItem(item).defaultBlockState();
        return ret;
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".block_breaker");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    private int getRequiredEnergy() {
        return 100;
    }

    private Block getBlock() {
        direction = getBlockState().getValue(BlockPlacerBlock.FACING);
        return level.getBlockState(getBlockPos().relative(direction)).getBlock();
    }

    private Pair<Boolean, Integer> findItem(int slots) {
        for (int i = 0; i < slots; i++) {
            if (!inventory.getItem(i).isEmpty()) {
                return new Pair<>(true, i);
            }
        }
        return new Pair<>(false, 0);
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}
