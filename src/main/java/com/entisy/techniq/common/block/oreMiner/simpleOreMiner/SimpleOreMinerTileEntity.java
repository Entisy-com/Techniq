package com.entisy.techniq.common.block.oreMiner.simpleOreMiner;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import com.entisy.techniq.core.util.entisy.Pair;
import com.entisy.techniq.core.util.entisy.SimpleList;
import com.entisy.techniq.core.util.entisy.SimpleMap;
import com.entisy.techniq.core.util.entisy.Triple;
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

public class SimpleOreMinerTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    //             block, Uses, MiningTime
    private Triple<Block, Integer, Integer> triple = new Triple<>();
    private SimpleList<Block> minableBlocks = new SimpleList<>();
    private SimpleMap<Block, Pair<Item, Integer>> outputs = new SimpleMap<>();
    private int uses = 0;

    public SimpleOreMinerTileEntity() {
        super(2, 500, 0, ModTileEntityTypes.SIMPLE_ORE_MINER_TILE_ENTITY.get());
        registerMinableBlocks();
        registerTriple();
        registerOutputs();
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new SimpleOreMinerContainer(windowId, inv, this);
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
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(SimpleOreMinerBlock.LIT, true));
                            if (tryMoveStack(2).getKey()) {
                                currentSmeltTime++;
                                dirty = true;
                            }
                        } else {
                            Pair<Boolean, Integer> duo = tryMoveStack(2);
                            if (duo.getKey()) {
                                inventory.insertItem(duo.getValue(), getResultItem(getBlock()).copy(), false);
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

    private ItemStack getResultItem(Block block) {
        ItemStack output = getOutput(block).getKey().getDefaultInstance();
        output.setCount(getOutput(block).getValue());
        return output;
    }

    private int getUses(Block block) {
        return triple.getBFromA(block);
    }

    public int getMiningTime() {
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
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".simple_ore_miner");
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
        outputs.append(Blocks.COAL_ORE, new Pair<>(Items.COAL, 3));
        outputs.append(Blocks.IRON_ORE, new Pair<>(Items.IRON_INGOT, 1));
        outputs.append(Blocks.DIAMOND_ORE, new Pair<>(Items.DIAMOND, 1));
        outputs.append(Blocks.LAPIS_ORE, new Pair<>(Items.LAPIS_LAZULI, 3));
        outputs.append(Blocks.EMERALD_ORE, new Pair<>(Items.EMERALD, 1));
        outputs.append(Blocks.GOLD_ORE, new Pair<>(Items.GOLD_INGOT, 1));
        outputs.append(Blocks.NETHER_QUARTZ_ORE, new Pair<>(Items.QUARTZ, 3));
        outputs.append(Blocks.REDSTONE_ORE, new Pair<>(Items.REDSTONE, 3));
    }

    public Pair<Item, Integer> getOutput(Block minableBlock) {
        Random random = new Random();
        int value = random.nextInt(outputs.getValue(minableBlock).getValue()) + 1;
        return new Pair<>(outputs.getValue(minableBlock).getKey(), value);
    }

    private void registerTriple() {
        triple.append(Blocks.COAL_ORE, 25, minutes(1));
        triple.append(Blocks.IRON_ORE, 20, seconds(90));
        triple.append(Blocks.DIAMOND_ORE, 5, minutes(5));
        triple.append(Blocks.LAPIS_ORE, 7, minutes(2));
        triple.append(Blocks.EMERALD_ORE, 3, seconds(270));
        triple.append(Blocks.GOLD_ORE, 10, minutes(2));
        triple.append(Blocks.NETHER_QUARTZ_ORE, 10, minutes(2));
        triple.append(Blocks.REDSTONE_ORE, 7, minutes(2));
    }

    private int seconds(int number) {
        return number * 20;
    }

    private int minutes(int number) {
        return number * 60 * 20;
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
