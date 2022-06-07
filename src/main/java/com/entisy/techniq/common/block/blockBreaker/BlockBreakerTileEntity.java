package com.entisy.techniq.common.block.blockBreaker;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.entisy.betterLists.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class BlockBreakerTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    private int cooldown = 0;
    private int workTime = 20;
    private Direction direction;

    public BlockBreakerTileEntity() {
        super(9, 500, 0, ModTileEntityTypes.BLOCK_BREAKER_TILE_ENTITY_TYPE.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new BlockBreakerContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = iEnergyStorage.getEnergyStored();
            });
            if (currentEnergy >= getRequiredEnergy()) {
                if (getBlock() != Blocks.AIR) {
                    if (cooldown != workTime) {
                        Pair<Boolean, Integer> pair = tryMoveStack(9);
                        if (pair.getKey()) {
                            cooldown++;
                            dirty = true;
                        }
                    } else {
                        Pair<Boolean, Integer> pair = tryMoveStack(9);
                        if (pair.getKey()) {
                            energy.ifPresent(iEnergyStorage -> {
                                energyStorage.setEnergyDirectly(currentEnergy - getRequiredEnergy());
                                currentEnergy = energyStorage.getEnergyStored();
                            });
                            direction = getBlockState().getValue(BlockBreakerBlock.FACING);
                            inventory.insertItem(pair.getValue(), getResultItem(getBlock()), false);
                            level.destroyBlock(getBlockPos().relative(direction), false);
                        }
                        cooldown = 0;
                        dirty = true;
                    }
                }
            }
        }
        if (dirty) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private ItemStack getResultItem(Block block) {
        ItemStack output = block.asItem().getDefaultInstance();
        return output;
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
        direction = getBlockState().getValue(BlockBreakerBlock.FACING);
        return level.getBlockState(getBlockPos().relative(direction)).getBlock();
    }

    private Pair<Boolean, Integer> tryMoveStack(int slots) {
        for (int i = 0; i < slots; i++) {
            if ((inventory.getItem(i).sameItem(getResultItem(getBlock())) && inventory.getItem(i).getCount() < 64) || (inventory.getItem(i).getItem() == Items.AIR) || inventory.getItem(i).getStack() == ItemStack.EMPTY) {
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
