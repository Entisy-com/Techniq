package com.entisy.techniq.common.block.furnaceGenerator;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterBlock;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.EnergyUtils;
import com.entisy.techniq.core.util.entisy.SimpleMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class FurnaceGeneratorTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    //Item - RF in total
    public static final SimpleMap<Item, Integer> AVAILABLE_FUELS = new SimpleMap<>();

    public FurnaceGeneratorTileEntity(TileEntityType<?> type) {
        super(1, 0, 500, type);
        AVAILABLE_FUELS.append(Items.COAL, 200);
        AVAILABLE_FUELS.append(Items.COAL_BLOCK, 200 * 9);
        for (Item i : ItemTags.LOGS.getValues()) {
            AVAILABLE_FUELS.append(i, 100);
        }
        for (Item i : ItemTags.PLANKS.getValues()) {
            AVAILABLE_FUELS.append(i, 50);
        }
    }

    public FurnaceGeneratorTileEntity() {
        this(ModTileEntityTypes.FURNACE_GENERATOR_TILE_ENTITY.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new FurnaceGeneratorContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            if (maxEnergyExtract > 0) {
                EnergyUtils.trySendToNeighbors(level, worldPosition, this, maxEnergyExtract);
            }
            if (AVAILABLE_FUELS.getKeys().contains(inventory.getStackInSlot(0).getItem())) {
                Item fuel = inventory.getStackInSlot(0).getItem();
                if (currentEnergy + AVAILABLE_FUELS.getValue(fuel) < maxEnergy) {
                    if (currentSmeltTime != getMaxSmeltTime()) {
                        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(AlloySmelterBlock.LIT, true));
                        currentSmeltTime++;
                        dirty = true;

                    } else {
                        energy.ifPresent(iEnergyStorage -> {
                            ((EnergyStorageImpl) iEnergyStorage).setEnergyDirectly(iEnergyStorage.getEnergyStored() + AVAILABLE_FUELS.getValue(fuel));
                            currentEnergy = iEnergyStorage.getEnergyStored();
                        });

                        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(FurnaceGeneratorBlock.LIT, false));
                        currentSmeltTime = 0;

                        //Recipe Handling
                        inventory.shrink(0, 1);
                        dirty = true;
                    }
                }
            } else {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(FurnaceGeneratorBlock.LIT, false));
                currentSmeltTime = 0;
                dirty = true;
            }
        }
        if (dirty) {
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
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".furnace_generator");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    public int getMaxSmeltTime() {
        return 200;
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}