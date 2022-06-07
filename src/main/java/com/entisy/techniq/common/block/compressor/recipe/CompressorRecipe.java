package com.entisy.techniq.common.block.compressor.recipe;

import com.entisy.techniq.core.init.ModRecipes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class CompressorRecipe implements ICompressorRecipe {

    private final ResourceLocation id;
    private final Ingredient input1, input2;
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    private final ItemStack output;
    private int requiredEnergy;
    private int smeltTime;

    public CompressorRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, ItemStack output, int requiredEnergy, int smeltTime) {
        this.id = id;
        this.ingredients.put(input1, input1.getItems()[0].getCount());
        this.ingredients.put(input2, input2.getItems()[0].getCount());
        this.input1 = input1;
        this.input2 = input2;
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

    public void setSmeltTime(int value) {
        smeltTime = value;
    }

    public int getCount(ItemStack item) {
        if (item.getItem() == getIngredients().get(0).getItems()[0].getItem()) return input1.getItems()[0].getCount();
        if (item.getItem() == getIngredients().get(1).getItems()[0].getItem()) return input2.getItems()[0].getCount();
        return 1;
    }

    public Map<Ingredient, Integer> getIngredientMap() {
        return ImmutableMap.copyOf(ingredients);
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
        return input1;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(null, input1, input2);
    }

    @Override
    public boolean matches(RecipeWrapper wrapper, World level) {
        return (input1.test(wrapper.getItem(0)) && input2.test(wrapper.getItem(1)) && wrapper.getItem(0).getCount() >= input1.getItems()[0].getCount() && wrapper.getItem(1).getCount() >= input2.getItems()[0].getCount()) || input1.test(wrapper.getItem(1)) && input2.test(wrapper.getItem(0)) && wrapper.getItem(0).getCount() >= input2.getItems()[0].getCount() && wrapper.getItem(1).getCount() >= input1.getItems()[0].getCount();
    }
}
