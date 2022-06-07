package com.entisy.techniq.common.block.harvester;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.common.item.upgrades.RangeUpgradeItem;
import com.entisy.techniq.common.item.upgrades.SpeedUpgradeItem;
import com.entisy.techniq.common.item.upgrades.UpgradeItem;
import com.entisy.techniq.common.item.upgrades.UpgradeType;
import com.entisy.techniq.common.slots.UpgradeSlot;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class HarvesterTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    private final int normalSlots = 6;
    private int workTime = 20;
    private int radius = 6;

    public HarvesterTileEntity() {
        super(8, 500, 0, ModTileEntityTypes.HARVESTER_TILE_ENTITY_TYPE.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new HarvesterContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
            workTime = 20 / (getSpeedUpgrades() * SpeedUpgradeItem.getUpgradeStage() != 0 ? getSpeedUpgrades() * SpeedUpgradeItem.getUpgradeStage() : 1);
            radius = 6 + (getRangeUpgrades() * RangeUpgradeItem.getUpgradeStage());

            energy.ifPresent(iEnergyStorage -> {
                currentEnergy = iEnergyStorage.getEnergyStored();
            });
            if (currentEnergy >= getRequiredEnergy()) {
                if (currentSmeltTime != workTime) {
                    if (tryMoveStack()) {
                        currentSmeltTime++;
                        dirty = true;
                    }
                } else {
                    if (tryMoveStack()) {
                        dirty = harvest();
                    }
                }
            }
        }
        if (dirty) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private boolean harvest() {
        for (int i = 0; i < getHarvestableBlocks().size(); i++) {
            for (BlockPos pos : getHarvestableBlocks().list()) {
                CropsBlock crop = (CropsBlock) level.getBlockState(pos).getBlock();
                insertDropsInInventory();
                energy.ifPresent(iEnergyStorage -> {
                    energyStorage.setEnergyDirectly(getEnergyStored() - getRequiredEnergy());
                    currentEnergy = energyStorage.getEnergyStored();
                });

                currentSmeltTime = 0;
                level.destroyBlock(pos, false);
                if (crop instanceof BeetrootBlock) {
                    level.setBlock(pos, crop.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 0);
                } else {
                    level.setBlock(pos, crop.defaultBlockState().setValue(CropsBlock.AGE, 0), 0);
                }
                return true;

            }
        }
        return false;
    }

    private SimpleList<ItemStack> getResultItems(BlockPos pos) {
        SimpleList<ItemStack> ret = new SimpleList<>();
        CropsBlock.getDrops(level.getBlockState(pos), (ServerWorld) level, pos, this).forEach(i -> ret.append(i));
        return ret;
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".harvester");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    private int getRequiredEnergy() {
        return 100;
    }

    private int getRangeUpgrades() {
        int ret = 0;
        for (UpgradeItem u : getUpgrades().list()) {
            if (u instanceof RangeUpgradeItem) {
                ret++;
            }
        }
        ;
        return ret;
    }

    private int getSpeedUpgrades() {
        int ret = 0;
        for (UpgradeItem u : getUpgrades().list()) {
            if (u instanceof SpeedUpgradeItem) {
                ret++;
            }
        }
        ;
        return ret;
    }

    private SimpleList<BlockPos> getHarvestableBlocks() {
        SimpleList<BlockPos> ret = new SimpleList<>();
        for (BlockPos pos : getBlocksInRange().list()) {
            Block block = getLevel().getBlockState(pos).getBlock();
            if (block instanceof CropsBlock) {
                if (block instanceof BeetrootBlock) {
                    if (((BeetrootBlock) block).isMaxAge(level.getBlockState(pos))) {
                        ret.append(pos);
                    }
                } else {
                    if (((CropsBlock) block).isMaxAge(level.getBlockState(pos))) {
                        ret.append(pos);
                    }
                }
            }

        }
        return ret;
    }

    private SimpleList<BlockPos> getBlocksInRange() {
        SimpleList<BlockPos> ret = new SimpleList<>();
        BlockPos harvester = getBlockPos();
        for (int x = -radius; x < radius + 1; x++) {
            for (int z = -radius; z < radius + 1; z++) {
                ret.append(harvester.offset(x, 0, z));
            }
        }
        return ret;
    }

    private boolean tryMoveStack() {
        boolean ret = false;
        List<ItemStack> test = new ArrayList<>();
        if (!getHarvestableBlocks().isEmpty())
            test = Block.getDrops(level.getBlockState(getHarvestableBlocks().get(0)), (ServerWorld) level, getHarvestableBlocks().get(0), this);
        for (ItemStack stack : test) {
            for (int i = 0; i < normalSlots; i++) {
                if ((inventory.getItem(i).sameItem(stack) && inventory.getItem(i).getCount() < 64) || (inventory.getItem(i).getItem() == Items.AIR) || inventory.getItem(i) == ItemStack.EMPTY) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    private void insertDropsInInventory() {
        for (ItemStack stack : Block.getDrops(level.getBlockState(getHarvestableBlocks().get(0)), (ServerWorld) level, getHarvestableBlocks().get(0), this)) {
            for (int i = 0; i < normalSlots; i++) {
                if ((inventory.getItem(i).sameItem(stack) && inventory.getItem(i).getCount() < 64) || (inventory.getItem(i).getItem() == Items.AIR) || inventory.getItem(i) == ItemStack.EMPTY) {
                    ItemStack result = stack.copy();
                    inventory.insertItem(i, result, false);
                    break;
                }
            }
        }
    }

    private SimpleList<UpgradeType> getAcceptableUpgradeItems() {
        return new SimpleList<>(UpgradeType.RANGE, UpgradeType.SPEED);
    }

    private SimpleList<UpgradeItem> getUpgrades() {
        SimpleList<UpgradeItem> ret = new SimpleList<>();
        for (UpgradeSlot u : getUpgradeSlots().list()) {
            if (u.containsUpgrade()) {
                if (getAcceptableUpgradeItems().contains(u.getUpgradeItem().getUpgradeType())) {
                    ret.append(u.getUpgradeItem());
                }
            }
        }
        ;
        return ret;
    }

    private SimpleList<UpgradeSlot> getUpgradeSlots() {
        SimpleList<UpgradeSlot> ret = new SimpleList<>();
        for (Slot s : HarvesterContainer.getSlots()) {
            if (s instanceof UpgradeSlot) {
                ret.append((UpgradeSlot) s);
            }
        }
        ;
        return ret;
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}
