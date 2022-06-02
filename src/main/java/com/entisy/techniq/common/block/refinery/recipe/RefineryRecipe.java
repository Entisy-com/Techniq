package com.entisy.techniq.common.block.refinery.recipe;

import com.entisy.techniq.common.block.MachineBlockItemHandler;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.core.init.ModRecipes;
import com.entisy.techniq.core.util.silentchaos512.FluidIngredient;
import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class RefineryRecipe implements IRefineryRecipe {

    private final ResourceLocation id;
    private final FluidIngredient fluidIngredient;
    private final ItemStack output;
    private int requiredEnergy = 500;
    private int requiredFluid = 500;
    private int workTime = 200;

    public RefineryRecipe(ResourceLocation id, FluidIngredient fluidIngredient, ItemStack output, int requiredEnergy, int requiredFluid, int workTime) {
        this.id = id;
        this.fluidIngredient = fluidIngredient;
        this.output = output;
        this.requiredEnergy = requiredEnergy;
        this.workTime = workTime;
        this.requiredFluid = requiredFluid;
    }

    public double getWorkTimeInSeconds() {
        return (float) getWorkTime() / 20;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int value) {
        workTime = value;
    }

    @Override
    public ItemStack assemble(RecipeWrapper wrapper) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOY_SMELTER_SERIALIZER.get();
    }

    @Override
    public Ingredient getInput() {
        return null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return null;
    }

    @Override
    public boolean matches(RecipeWrapper wrapper, World level) {
        return false;
    }

    public boolean match(MachineTileEntity tileEntity) {
        return false; // TODO: fluid type e.g. oil, water, lava
    }
}
