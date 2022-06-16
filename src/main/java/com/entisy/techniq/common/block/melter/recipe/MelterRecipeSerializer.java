package com.entisy.techniq.common.block.melter.recipe;

import com.entisy.techniq.common.block.metalPress.recipe.MetalPressRecipe;
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

public class MelterRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<MelterRecipe> {

    private int requiredEnergy = TechniqConfig.DEFAULT_REQUIRED_ENERGY.get();
    private int smeltTime = TechniqConfig.DEFAULT_WORK_TIME.get();

    @Override
    public MelterRecipe fromJson(ResourceLocation id, JsonObject json) {
        Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
        requiredEnergy = json.get("required_energy").getAsInt();
        smeltTime = json.get("smelt_time").getAsInt();
        return new MelterRecipe(id, input, output, requiredEnergy, smeltTime);
    }

    @Override
    public MelterRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
        Ingredient input = Ingredient.fromNetwork(buffer);
        ItemStack output = buffer.readItem();
        requiredEnergy = buffer.readInt();
        smeltTime = buffer.readInt();
        return new MelterRecipe(id, input, output, requiredEnergy, smeltTime);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, MelterRecipe recipe) {
        Ingredient input = recipe.getIngredients().get(0);
        input.toNetwork(buffer);
        buffer.writeItemStack(recipe.getResultItem(), false);
        buffer.writeInt(recipe.getRequiredEnergy());
        buffer.writeInt(recipe.getSmeltTime());
    }
}
