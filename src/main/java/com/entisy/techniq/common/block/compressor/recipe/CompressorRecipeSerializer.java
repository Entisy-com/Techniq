package com.entisy.techniq.common.block.compressor.recipe;

import com.entisy.techniq.core.init.TechniqConfig;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CompressorRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<CompressorRecipe> {

	private int requiredEnergy = TechniqConfig.DEFAULT_REQUIRED_ENERGY.get();
	private int smeltTime = TechniqConfig.DEFAULT_WORK_TIME.get();

	@Override
	public CompressorRecipe fromJson(ResourceLocation id, JsonObject json) {
		Ingredient input1 = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredients").get(0));
		int count1 = JSONUtils.getAsJsonArray(json, "ingredients").get(0).getAsJsonObject().get("count").getAsInt();
		input1.getItems()[0].setCount(count1);
		Ingredient input2 = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredients").get(1));
		int count2 = JSONUtils.getAsJsonArray(json, "ingredients").get(1).getAsJsonObject().get("count").getAsInt();
		input2.getItems()[0].setCount(count2);
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
		requiredEnergy = json.get("required_energy").getAsInt();
		smeltTime = json.get("smelt_time").getAsInt();
		return new CompressorRecipe(id, input1, input2, output, requiredEnergy, smeltTime);
	}

	@Override
	public CompressorRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
		Ingredient input1 = Ingredient.fromNetwork(buffer);
		int count1 = buffer.readInt();
		input1.getItems()[0].setCount(count1);
		Ingredient input2 = Ingredient.fromNetwork(buffer);
		int count2 = buffer.readInt();
		input2.getItems()[0].setCount(count2);
		ItemStack output = buffer.readItem();
		requiredEnergy = buffer.readInt();
		smeltTime = buffer.readInt();
		return new CompressorRecipe(id, input1, input2, output, requiredEnergy, smeltTime);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, CompressorRecipe recipe) {
		Ingredient input1 = recipe.getIngredients().get(0);
		Ingredient input2 = recipe.getIngredients().get(1);
		int count1 = recipe.getIngredients().get(0).getItems()[0].getCount();
		int count2 = recipe.getIngredients().get(0).getItems()[0].getCount();
		input1.toNetwork(buffer);
		buffer.writeInt(count1);
		buffer.writeInt(count2);
		input2.toNetwork(buffer);
		buffer.writeItemStack(recipe.getResultItem(), false);
		buffer.writeInt(recipe.getRequiredEnergy());
		buffer.writeInt(recipe.getSmeltTime());
	}
}
