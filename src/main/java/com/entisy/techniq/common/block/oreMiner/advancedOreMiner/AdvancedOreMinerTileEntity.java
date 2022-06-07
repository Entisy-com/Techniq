package com.entisy.techniq.common.block.oreMiner.advancedOreMiner;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModItems;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.entisy.betterLists.Pair;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleMap;
import com.entisy.techniq.core.util.entisy.betterLists.Triplet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class AdvancedOreMinerTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    //             block, Uses, MiningTime
    private Triplet<Block, Integer, Float> triple = new Triplet<>();
    private SimpleList<Block> minableBlocks = new SimpleList<>();
    private Triplet<Block, Pair<Item, Integer>, Pair<Item, Integer>> outputs = new Triplet<>();
    private int uses = 0;

    public AdvancedOreMinerTileEntity() {
        super(2, 500, 0, ModTileEntityTypes.ADVANCED_ORE_MINER_TILE_ENTITY.get());
        registerMinableBlocks();
        registerTriple();
        registerOutputs();
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new AdvancedOreMinerContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            if (isMinableBlock(getBlock())) {
                if (uses < getUses(getBlock())) {
                    energy.ifPresent(iEnergyStorage -> {
                        currentEnergy = iEnergyStorage.getEnergyStored();
                    });
                    if (currentEnergy >= getRequiredEnergy()) {
                        if (currentSmeltTime < getMiningTime()) {
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(AdvancedOreMinerBlock.LIT, true));
                            if (tryMoveStack(2).getKey()) {
                                currentSmeltTime++;
                                dirty = true;
                            }
                        } else {
                            Pair<Boolean, Integer> duo = tryMoveStack(2);
                            if (duo.getKey()) {
                                inventory.insertItem(0, getResultItem(getBlock()).getKey(), false);
                                inventory.insertItem(1, getResultItem(getBlock()).getValue(), false);
                                energy.ifPresent(iEnergyStorage -> {
                                    ((EnergyStorageImpl) iEnergyStorage).setEnergyDirectly(iEnergyStorage.getEnergyStored() - getRequiredEnergy());
                                    currentEnergy = iEnergyStorage.getEnergyStored();
                                });
                                uses++;
                                dirty = true;
                            }
                            currentSmeltTime = 0;
                            dirty = true;
                        }
                    }
                } else if (uses >= getUses(getBlock())) {
                    getLevel().destroyBlock(getBlockPos().below(), false);
                    uses = 0;
                    dirty = true;
                }
            } else {
                uses = 0;
                dirty = true;
            }
        }
        if (dirty) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private void tryInsertItem(int slots, ItemStack item1, ItemStack item2) {
        for (int i = 0; i < slots; i++) {
            if ((inventory.getItem(i).sameItem(item1) && inventory.getItem(i).getCount() < 64) || inventory.getItem(i).getItem() == Items.AIR || inventory.getItem(i).getStack() == ItemStack.EMPTY) {
                inventory.insertItem(i, item1, false);
            } else {
                inventory.insertItem(i, item2, false);
            }
        }
    }

    private Pair<ItemStack, ItemStack> getResultItem(Block block) {
        ItemStack first = getOutput(block).getKeys().get(0).getKey().getDefaultInstance();
        ItemStack second = getOutput(block).getValues().get(0).getKey().getDefaultInstance();
        first.setCount(getOutput(block).getKeys().get(0).getValue());
        second.setCount(getOutput(block).getValues().get(0).getValue());
        return new Pair<>(first, second);
    }

    private int getUses(Block block) {
        return triple.getBFromA(block);
    }

    public float getMiningTime() {
        return triple.getCFromB(getUses());
    }

    private boolean isMinableBlock(Block block) {
        return minableBlocks.contains(block);
    }

    private Block getBlock() {
        return getLevel().getBlockState(getBlockPos().below()).getBlock();
    }

    public boolean isBlockNull() {
        return !isMinableBlock(getBlock());
    }

    private int getUses() {
        return getUses(getLevel().getBlockState(getBlockPos().below()).getBlock());
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("Uses", uses);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        uses = nbt.getInt("Uses");
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".advanced_ore_miner");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    private int getRequiredEnergy() {
        return 1000;
    }

    private void registerMinableBlocks() {
        minableBlocks.append(
                Blocks.COAL_ORE,
                Blocks.IRON_ORE,
                Blocks.DIAMOND_ORE,
                Blocks.LAPIS_ORE,
                Blocks.EMERALD_ORE,
                Blocks.GOLD_ORE,
                Blocks.NETHER_QUARTZ_ORE,
                Blocks.REDSTONE_ORE
        );
    }

    private void registerOutputs() {
        outputs.append(Blocks.COAL_ORE, new Pair<>(Items.COAL, 6), new Pair<>(ModItems.COAL_POWDER.get(), 3));
        outputs.append(Blocks.IRON_ORE, new Pair<>(Items.IRON_INGOT, 3), new Pair<>(ModItems.IRON_POWDER.get(), 1));
        outputs.append(Blocks.DIAMOND_ORE, new Pair<>(Items.DIAMOND, 3), new Pair<>(ModItems.DIAMOND_POWDER.get(), 1));
        outputs.append(Blocks.LAPIS_ORE, new Pair<>(Items.LAPIS_LAZULI, 6), new Pair<>(ModItems.LAPIS_POWDER.get(), 3));
        outputs.append(Blocks.EMERALD_ORE, new Pair<>(Items.EMERALD, 3), new Pair<>(ModItems.EMERALD_POWDER.get(), 1));
        outputs.append(Blocks.GOLD_ORE, new Pair<>(Items.GOLD_INGOT, 3), new Pair<>(ModItems.GOLD_POWDER.get(), 1));
        outputs.append(Blocks.NETHER_QUARTZ_ORE, new Pair<>(Items.QUARTZ, 6), new Pair<>(ModItems.QUARTZ_POWDER.get(), 3));
        outputs.append(Blocks.REDSTONE_ORE, new Pair<>(Items.REDSTONE, 6), new Pair<>(Items.REDSTONE, 3));
    }

    public SimpleMap<Pair<Item, Integer>, Pair<Item, Integer>> getOutput(Block minableBlock) {
        Random random = new Random();
        int firstValue = random.nextInt(outputs.getBFromA(minableBlock).getValue()) + 1;
        int secondValue = random.nextInt(outputs.getCFromA(minableBlock).getValue()) + 1;
        SimpleMap<Pair<Item, Integer>, Pair<Item, Integer>> ret = new SimpleMap<>();
        ret.append(new Pair<>(outputs.getBFromA(minableBlock).getKey(), firstValue), new Pair<>(outputs.getCFromA(minableBlock).getKey(), secondValue));
        return ret;
    }

    private void registerTriple() {
        triple.append(Blocks.COAL_ORE, 50, seconds(30));
        triple.append(Blocks.IRON_ORE, 40, seconds(45));
        triple.append(Blocks.DIAMOND_ORE, 10, seconds(145));
        triple.append(Blocks.LAPIS_ORE, 14, minutes(1.0f));
        triple.append(Blocks.EMERALD_ORE, 6, seconds(135));
        triple.append(Blocks.GOLD_ORE, 20, minutes(1.0f));
        triple.append(Blocks.NETHER_QUARTZ_ORE, 20, minutes(1.0f));
        triple.append(Blocks.REDSTONE_ORE, 14, minutes(1.0f));
    }

    private float seconds(float number) {
        return number * 20;
    }

    private float minutes(float number) {
        return number * 60 * 20;
    }

    private Pair<Boolean, Integer> tryMoveStack(int slots) {
        for (int i = 0; i < slots; i++) {
            if ((inventory.getItem(i).sameItem(getResultItem(getBlock()).getKey()) && inventory.getItem(i).getCount() < 64) || (inventory.getItem(i).getItem() == Items.AIR) || inventory.getItem(i).getStack() == ItemStack.EMPTY) {
                return new Pair<>(true, i);
            } else if ((inventory.getItem(i).sameItem(getResultItem(getBlock()).getValue()) && inventory.getItem(i).getCount() < 64) || (inventory.getItem(i).getItem() == Items.AIR) || inventory.getItem(i).getStack() == ItemStack.EMPTY) {
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
