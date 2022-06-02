//package com.entisy.techniq.common.block.refinery.recipe;
//
//import com.google.gson.JsonObject;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipeSerializer;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.util.JSONUtils;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.crafting.CraftingHelper;
//import net.minecraftforge.registries.ForgeRegistryEntry;
//
//public class RefineryRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
//		implements IRecipeSerializer<RefineryRecipe> {
//
//	@Override
//	public RefineryRecipe fromJson(ResourceLocation id, JsonObject json) {
//		Ingredient input1 = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredients").get(0));
//		int count1 = JSONUtils.getAsJsonArray(json, "ingredients").get(0).getAsJsonObject().get("count").getAsInt();
//		input1.getItems()[0].setCount(count1);
//		Ingredient input2 = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredients").get(1));
//		int count2 = JSONUtils.getAsJsonArray(json, "ingredients").get(1).getAsJsonObject().get("count").getAsInt();
//		input2.getItems()[0].setCount(count2);
//		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
//		int requiredEnergy = json.get("required_energy").getAsInt();
//		int smeltTime = json.get("smelt_time").getAsInt();
//		return new RefineryRecipe(id, input1, input2, output, requiredEnergy, smeltTime);
//	}
//
//	@Override
//	public RefineryRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
//		Ingredient input1 = Ingredient.fromNetwork(buffer);
//		int count1 = buffer.readInt();
//		input1.getItems()[0].setCount(count1);
//		Ingredient input2 = Ingredient.fromNetwork(buffer);
//		int count2 = buffer.readInt();
//		input2.getItems()[0].setCount(count2);
//		ItemStack output = buffer.readItem();
//		int requiredEnergy = buffer.readInt();
//		int smeltTime = buffer.readInt();
//		return new RefineryRecipe(id, input1, input2, output, requiredEnergy, smeltTime);
//	}
//
//	@Override
//	public void toNetwork(PacketBuffer buffer, RefineryRecipe recipe) {
//		Ingredient input1 = recipe.getIngredients().get(0);
//		Ingredient input2 = recipe.getIngredients().get(1);
//		int count1 = recipe.getIngredients().get(0).getItems()[0].getCount();
//		int count2 = recipe.getIngredients().get(0).getItems()[0].getCount();
//		input1.toNetwork(buffer);
//		buffer.writeInt(count1);
//		buffer.writeInt(count2);
//		input2.toNetwork(buffer);
//		buffer.writeItemStack(recipe.getResultItem(), false);
//		buffer.writeInt(recipe.getRequiredEnergy());
//		buffer.writeInt(recipe.getSmeltTime());
//	}
//}
