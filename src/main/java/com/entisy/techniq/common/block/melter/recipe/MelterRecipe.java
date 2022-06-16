package com.entisy.techniq.common.block.melter.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MelterRecipe implements IMelterRecipe{

    private final ResourceLocation id;
    private final Ingredient input;
    private final FluidStack output;
    private int requiredEnergy = 200;
    private int smeltTime = 200;

    public MelterRecipe(ResourceLocation id, Ingredient input, FluidStack output, int requiredEnergy, int smeltTime) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.requiredEnergy = requiredEnergy;
        this.smeltTime = smeltTime;
    }

    public double getSmeltTimeInSeconds() {
        return (float) getSmeltTime() / 20;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    public int getSmeltTime() {
        return smeltTime;
    }

    public int getCount(ItemStack item) {
        return input.getItems()[0].getCount() | 1;
    }

    @Override
    public Ingredient getInput() {
        return null;
    }

    @Override
    public boolean matches(RecipeWrapper p_77569_1_, World p_77569_2_) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper wrapper) {
        return ItemStack.EMPTY;
    }


    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }
}
