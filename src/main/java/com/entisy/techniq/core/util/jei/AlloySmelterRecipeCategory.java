package com.entisy.techniq.core.util.jei;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.recipe.AlloySmelterRecipe;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.util.jei.util.DrawHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class AlloySmelterRecipeCategory extends DrawHelper implements IRecipeCategory<AlloySmelterRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Techniq.MOD_ID, "alloy_smelter");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Techniq.MOD_ID, "textures/block/alloy_smelter/jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;
    private final IGuiHelper helper;
    private IDrawableAnimated arrowAnimated;

    public AlloySmelterRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        background = helper.createDrawable(TEXTURE, 0, 0, 146, 74);
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.ALLOY_SMELTER.get()));
        arrow = helper.createDrawable(TEXTURE, 0, 74, 22, 16);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AlloySmelterRecipe> getRecipeClass() {
        return AlloySmelterRecipe.class;
    }

    @Override
    public String getTitle() {
        return ModBlocks.ALLOY_SMELTER.get().getName().getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(AlloySmelterRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredientMap().keySet().stream()
                .map(i -> Arrays.asList(i.getItems())).collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloySmelterRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 11, 14);
        recipeLayout.getItemStacks().init(1, true, 11, 41);
        recipeLayout.getItemStacks().init(2, false, 81, 27);

        int i = 0;
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientMap().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer count = entry.getValue();
            recipeLayout.getItemStacks().set(i++, Arrays.stream(ingredient.getItems())
                    .map(s -> {
                        ItemStack stack = s.copy();
                        stack.setCount(count);
                        return stack;
                    })
                    .collect(Collectors.toList())
            );
        }
        recipeLayout.getItemStacks().set(2, recipe.getResultItem());
    }

    @Override
    public void draw(AlloySmelterRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        // Arrow
        arrowAnimated = helper.createAnimatedDrawable(arrow, 10, IDrawableAnimated.StartDirection.LEFT, false);
        // Energy bar
        int pixel = recipe.getRequiredEnergy() * 50 / 10000;
        blit(matrixStack, 121, (50 - pixel) + 12, 146, (50 - pixel), 12, 50);
        // Energy tooltip
        if (mouseX >= 121 && mouseX < 121 + 12) {
            if (mouseY >= 12 && mouseY < 12 + 50) {
                // Energy bar
                blit(matrixStack, 121, (50 - pixel) + 12, 146, (50 - pixel), 12, 50);
                // Tooltip
                renderTooltip(matrixStack, new StringTextComponent(recipe.getRequiredEnergy() + "RF"), (int) mouseX, (int) mouseY);
            }
        }
        // Work Time
        font.draw(matrixStack, recipe.getSmeltTimeInSeconds() + "s", 44, 18, 4210752);
    }
}
