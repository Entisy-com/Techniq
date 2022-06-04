package com.entisy.techniq.common.block.refinery.recipe;

import com.entisy.techniq.core.init.TechniqConfig;
import com.entisy.techniq.core.util.silentchaos512.FluidIngredient;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RefineryRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<RefineryRecipe> {

    private int requiredEnergy = TechniqConfig.DEFAULT_REQUIRED_ENERGY.get();
    private int smeltTime = TechniqConfig.DEFAULT_WORK_TIME.get();

	@Override
	public RefineryRecipe fromJson(ResourceLocation id, JsonObject json) {
		FluidIngredient input1 = FluidIngredient.deserialize(JSONUtils.getAsJsonObject(json, "input"));
        int fluidAmount = JSONUtils.getAsJsonObject(json, "input").get("amount").getAsInt();
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
		requiredEnergy = json.get("required_energy").getAsInt();
		smeltTime = json.get("work_time").getAsInt();
		return new RefineryRecipe(id, input1, fluidAmount, output, requiredEnergy, smeltTime);
	}

	@Override
	public RefineryRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
		FluidIngredient input1 = FluidIngredient.read(buffer);
		ItemStack output = buffer.readItem();
        int fluidAmount = buffer.readInt();
		int requiredEnergy = buffer.readInt();
		int smeltTime = buffer.readInt();
		return new RefineryRecipe(id, input1, fluidAmount, output, requiredEnergy, smeltTime);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, RefineryRecipe recipe) {
		FluidIngredient input = recipe.getFluidIngredient();
        int amount = recipe.getFluidAmount();
        input.setAmount(amount);
		input.write(buffer);
		buffer.writeItemStack(recipe.getResultItem(), false);
		buffer.writeInt(recipe.getRequiredEnergy());
		buffer.writeInt(recipe.getWorkTime());
	}
}
