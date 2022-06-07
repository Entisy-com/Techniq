package com.entisy.techniq.core.util.jei;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterContainer;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterScreen;
import com.entisy.techniq.common.block.refinery.RefineryTileEntity;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModItems;
import com.entisy.techniq.core.init.ModRecipes;
import com.entisy.techniq.core.init.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Techniq.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MetalPressRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlloySmelterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RefineryRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ELECTRICAL_FURNACE.get()),
                VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.METAL_PRESS.get()),
                MetalPressRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_SMELTER.get()),
                AlloySmelterRecipeCategory.UID);
        // Refinery
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.REFINERY.get()),
                RefineryRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IIngredientManager ingredientManager = registration.getIngredientManager();
        // Metal Press
        registration.addRecipes(getAllRecipesFor(ModRecipes.METAL_PRESS_TYPE), MetalPressRecipeCategory.UID);
        // Alloy Smelter
        registration.addRecipes(getAllRecipesFor(ModRecipes.ALLOY_SMELTER_TYPE), AlloySmelterRecipeCategory.UID);
        // Ore Miner
        addInfo(registration, ModBlocks.SIMPLE_ORE_MINER.get(),
                "Place on top af any vanilla ore and provide with energy to mine some ore.");
        addInfo(registration, ModBlocks.ADVANCED_ORE_MINER.get(),
                "Place on top af any vanilla ore and provide with energy to mine some ore.");
        // Refinery
        registration.addRecipes(Collections.singleton(new RefineryTileEntity()), RefineryRecipeCategory.UID);
        // Powder
        for (Item item : ModTags.Items.POWDER.getValues()) {

            registration.addIngredientInfo(
                    new ItemStack(item),
                    ingredientManager.getIngredientType(ItemStack.class),
                    new StringTextComponent("Can be obtained from Advanced Ore Miners as a side product."));
        }
        // Wrench
        addInfo(registration, ModItems.WRENCH.get(),
                "Can be used to change the input/output of cables. (green = input, red = output, none = both)");
    }

    private List<IRecipe<?>> getAllRecipesFor(IRecipeType<?> type) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());
    }

    private void addInfo(IRecipeRegistration registration, Item item, String info) {
        ItemStack stack = new ItemStack(item);
        IIngredientManager ingredientManager = registration.getIngredientManager();
        IIngredientType<ItemStack> ingredientType = ingredientManager.getIngredientType(ItemStack.class);
        registration.addIngredientInfo(stack, ingredientType, new StringTextComponent(info));
    }

    private void addInfo(IRecipeRegistration registration, Block block, String info) {
        ItemStack stack = new ItemStack(block);
        IIngredientManager ingredientManager = registration.getIngredientManager();
        IIngredientType<ItemStack> ingredientType = ingredientManager.getIngredientType(ItemStack.class);
        registration.addIngredientInfo(stack, ingredientType, new StringTextComponent(info));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloySmelterScreen.class,0,0,28,23, AlloySmelterRecipeCategory.UID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AlloySmelterContainer.class, AlloySmelterRecipeCategory.UID, 0, 3, 3, 36);
    }
}
