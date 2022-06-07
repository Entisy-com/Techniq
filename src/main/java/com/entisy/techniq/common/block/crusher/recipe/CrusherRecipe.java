package com.entisy.techniq.common.block.crusher.recipe;

import com.entisy.techniq.core.init.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CrusherRecipe implements ICrusherRecipe {

	private int requiredEnergy = 200;
	private int smeltTime = 200;

	private final ResourceLocation id;
	private final Ingredient input;
	private final ItemStack output;
	
	public CrusherRecipe(ResourceLocation id, Ingredient input, ItemStack output, int requiredEnergy, int smeltTime) {
		this.id = id;
		this.input = input;
		this.output = output;
		this.requiredEnergy = requiredEnergy;
		this.smeltTime = smeltTime;
	}

	public double getSmeltTimeInSeconds() {
		return (float) getWorkTime() / 20;
	}

	public int getRequiredEnergy() {
		return requiredEnergy;
	}

	public int getWorkTime() {
		return smeltTime;
	}

	public int getCount(ItemStack item) {
		return input.getItems()[0].getCount() | 1;
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
		return ModRecipes.CRUSHER_SERIALIZER.get();
	}

	@Override
	public Ingredient getInput() {
		return input;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(null, input);
	}

	@Override
	public boolean matches(RecipeWrapper wrapper, World world) {
		return input.test(wrapper.getItem(0));
	}
}

