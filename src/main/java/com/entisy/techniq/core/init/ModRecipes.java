package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.recipe.AlloySmelterRecipe;
import com.entisy.techniq.common.block.alloySmelter.recipe.AlloySmelterRecipeSerializer;
import com.entisy.techniq.common.block.alloySmelter.recipe.IAlloySmelterRecipe;
import com.entisy.techniq.common.block.crusher.recipe.CrusherRecipe;
import com.entisy.techniq.common.block.crusher.recipe.CrusherRecipeSerializer;
import com.entisy.techniq.common.block.crusher.recipe.ICrusherRecipe;
import com.entisy.techniq.common.block.electricalFurnace.recipe.ElectricalFurnaceRecipe;
import com.entisy.techniq.common.block.electricalFurnace.recipe.ElectricalFurnaceRecipeSerializer;
import com.entisy.techniq.common.block.electricalFurnace.recipe.IElectricalFurnaceRecipe;
import com.entisy.techniq.common.block.metalPress.recipe.IMetalPressRecipe;
import com.entisy.techniq.common.block.metalPress.recipe.MetalPressRecipe;
import com.entisy.techniq.common.block.metalPress.recipe.MetalPressRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    // ELECTRICAL FURNACE
    public static final IRecipeSerializer<ElectricalFurnaceRecipe> ELECTRICAL_FURNACE_RECIPE_SERIALIZER = new ElectricalFurnaceRecipeSerializer();
    public static final IRecipeType<IElectricalFurnaceRecipe> ELECTRICAL_FURNACE_TYPE = registerType(
            IElectricalFurnaceRecipe.RECIPE_TYPE_ID);
    // METAL PRESS
    public static final IRecipeSerializer<MetalPressRecipe> METAL_PRESS_RECIPE_SERIALIZER = new MetalPressRecipeSerializer();
    public static final IRecipeType<IMetalPressRecipe> METAL_PRESS_TYPE = registerType(
            IMetalPressRecipe.RECIPE_TYPE_ID);
    // ALLOY SMELTER
    public static final IRecipeSerializer<AlloySmelterRecipe> ALLOY_SMELTER_RECIPE_SERIALIZER = new AlloySmelterRecipeSerializer();
    public static final IRecipeType<IAlloySmelterRecipe> ALLOY_SMELTER_TYPE = registerType(
            IAlloySmelterRecipe.RECIPE_TYPE_ID);

    // CRUSHER
    public static final IRecipeSerializer<CrusherRecipe> CRUSHER_RECIPE_SERIALIZER = new CrusherRecipeSerializer();
    public static final IRecipeType<ICrusherRecipe> CRUSHER_TYPE = registerType(
            ICrusherRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(ForgeRegistries.RECIPE_SERIALIZERS, Techniq.MOD_ID);

    // SERIALIZERS
    public static final RegistryObject<IRecipeSerializer<?>> ELECTRICAL_FURNACE_SERIALIZER = RECIPE_SERIALIZERS
            .register("electrical_furnace", () -> ELECTRICAL_FURNACE_RECIPE_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> METAL_PRESS_SERIALIZER = RECIPE_SERIALIZERS
            .register("metal_press", () -> METAL_PRESS_RECIPE_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> ALLOY_SMELTER_SERIALIZER = RECIPE_SERIALIZERS
            .register("alloy_smelter", () -> ALLOY_SMELTER_RECIPE_SERIALIZER);

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
    }

    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
