package com.entisy.techniq.common.block.melter.recipe;

import com.entisy.techniq.common.block.metalPress.recipe.MetalPressRecipe;
import com.entisy.techniq.core.init.TechniqConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import static net.minecraft.util.datafix.fixes.SignStrictJSON.GSON;

public class MelterRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<MelterRecipe> {

    private int requiredEnergy = TechniqConfig.DEFAULT_REQUIRED_ENERGY.get();
    private int smeltTime = TechniqConfig.DEFAULT_WORK_TIME.get();

    @Override
    public MelterRecipe fromJson(ResourceLocation id, JsonObject json) {
        Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));
        FluidStack output = getFluidStack(JSONUtils.getAsJsonObject(json, "output"), true);
        requiredEnergy = json.get("required_energy").getAsInt();
        smeltTime = json.get("smelt_time").getAsInt();
        return new MelterRecipe(id, input, output, requiredEnergy, smeltTime);
    }

    @Override
    public MelterRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
        Ingredient input = Ingredient.fromNetwork(buffer);
        FluidStack output = FluidStack.readFromPacket(buffer);
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

    public static FluidStack getFluidStack(JsonObject json, boolean readNBT)
    {
        String fluidName = JSONUtils.getAsString(json, "fluid");

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));

        if (fluid == null)
            throw new JsonSyntaxException("Unknown fluid '" + fluidName + "'");

        if (readNBT && json.has("nbt"))
        {
            // Lets hope this works? Needs test
            try
            {
                JsonElement element = json.get("nbt");
                CompoundNBT nbt;
                if(element.isJsonObject())
                    nbt = JsonToNBT.parseTag(GSON.toJson(element));
                else
                    nbt = JsonToNBT.parseTag(JSONUtils.convertToString(element, "nbt"));

                CompoundNBT tmp = new CompoundNBT();
                if (nbt.contains("ForgeCaps"))
                {
                    tmp.put("ForgeCaps", nbt.get("ForgeCaps"));
                    nbt.remove("ForgeCaps");
                }

                tmp.put("tag", nbt);
                tmp.putString("id", fluidName);
                tmp.putInt("Amount", JSONUtils.getAsInt(json, "amount", 1000));

                return FluidStack.loadFluidStackFromNBT(tmp);
            }
            catch (CommandSyntaxException e)
            {
                throw new JsonSyntaxException("Invalid NBT Entry: " + e.toString());
            }
        }

        return new FluidStack(fluid, JSONUtils.getAsInt(json, "amount", 1000));
    }
}
