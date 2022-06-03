package com.entisy.techniq.common.block.crusher;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterBlock;
import com.entisy.techniq.common.block.crusher.recipe.CrusherRecipe;
import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.energy.IEnergyHandler;
import com.entisy.techniq.core.init.ModRecipes;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Set;

public class CrusherTileEntity extends MachineTileEntity implements ITickableTileEntity, INamedContainerProvider, IEnergyHandler {

    public CrusherTileEntity(TileEntityType<?> type) {
        super(2, 200, 0, type);
    }

    public CrusherTileEntity() {
        this(ModTileEntityTypes.CRUSHER_TILE_ENTITY.get());
    }

    @Override
    public Container createMenu(final int windowId, final PlayerInventory inv, final PlayerEntity player) {
        return new CrusherContainer(windowId, inv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (level != null && !level.isClientSide()) {
            if (getRecipe() != null) {
                if (currentEnergy >= getRecipe().getRequiredEnergy()) {
                    if (currentSmeltTime != getRecipe().getWorkTime()) {
                        if ((inventory.getStackInSlot(1).sameItem(getRecipe().getResultItem()) || inventory.getStackInSlot(1).getItem() == Items.AIR) && inventory.getStackInSlot(1).getCount() < 64) {
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CrusherBlock.LIT, true));
                            currentSmeltTime++;
                            dirty = true;
                        }
                    } else {
                        energy.ifPresent(iEnergyStorage -> {
                            ((EnergyStorageImpl) iEnergyStorage).setEnergyDirectly(iEnergyStorage.getEnergyStored() - getRecipe().getRequiredEnergy());
                            currentEnergy = iEnergyStorage.getEnergyStored();
                        });
                        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(AlloySmelterBlock.LIT, false));
                        currentSmeltTime = 0;
                        ItemStack output = getRecipe().getResultItem();
                        inventory.insertItem(1, output.copy(), false);

                        inventory.shrink(0, getRecipe().getCount(inventory.getItem(0)));
                        dirty = true;
                    }
                }
            } else {
                level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(AlloySmelterBlock.LIT, false));
                currentSmeltTime = 0;
                dirty = true;
            }
        }
        if (dirty) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Nullable
    public CrusherRecipe getRecipe(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        Set<IRecipe<?>> recipes = findRecipesByType(ModRecipes.CRUSHER_TYPE, level);
        for (IRecipe<?> iRecipe : recipes) {
            CrusherRecipe recipe = (CrusherRecipe) iRecipe;
            if (recipe.matches(new RecipeWrapper(inventory), level)) {
                return recipe;
            }
        }
        return null;
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getName() {
        return name != null ? name : getDefaultName();
    }

    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".crusher");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    public int getMaxSmeltTime() {
        return getRecipe().getWorkTime();
    }

    public CrusherRecipe getRecipe() {
        return getRecipe(getInventory().getStackInSlot(0));
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energyStorage;
    }
}
