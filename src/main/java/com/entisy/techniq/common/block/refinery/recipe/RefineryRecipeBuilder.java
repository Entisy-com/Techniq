package com.entisy.techniq.common.block.refinery.recipe;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.init.ModRecipes;
import com.entisy.techniq.core.util.silentchaos512.FluidIngredient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RefineryRecipeBuilder {

    private static int requiredEnergy = 200;
    private static int workTime = 200;
    private final Item result;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private FluidIngredient fluidIngreadient;
    private String group;

    public RefineryRecipeBuilder(IItemProvider provider, int count) {
        this.result = provider.asItem();
        this.count = count;
    }

    public static RefineryRecipeBuilder refining(IItemProvider provider) {
        return new RefineryRecipeBuilder(provider, 1);
    }

    public static RefineryRecipeBuilder refining(IItemProvider provider, int count) {
        return new RefineryRecipeBuilder(provider, count);
    }

    public RefineryRecipeBuilder requiredEnergy(int requiredEnergy) {
        this.requiredEnergy = requiredEnergy;
        return this;
    }

    public RefineryRecipeBuilder workTime(int workTime) {
        this.workTime = workTime;
        return this;
    }

    public RefineryRecipeBuilder requires(FlowingFluid fluid, int amount) {
        return this.requires(new FluidIngredient(fluid), amount);
    }

    public RefineryRecipeBuilder requires(ITag.INamedTag<Fluid> tag, int amount) {
        return this.requires(new FluidIngredient(tag), amount);
    }

    public RefineryRecipeBuilder requires(FluidIngredient fluid, int amount) {
        fluid.setAmount(amount);
        this.fluidIngreadient = fluid;
        return this;
    }

    public RefineryRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public RefineryRecipeBuilder group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    @SuppressWarnings("deprecation")
    public void save(Consumer<IFinishedRecipe> consumer) {
        this.save(consumer, Registry.ITEM.getKey(this.result));
    }

    @SuppressWarnings("deprecation")
    public void save(Consumer<IFinishedRecipe> consumer, String id) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        if ((new ResourceLocation(id)).equals(resourcelocation)) {
            throw new IllegalStateException("Refinery Recipe " + id + " should remove its 'save' argument");
        } else {
            this.save(consumer, new ResourceLocation(Techniq.MOD_ID, "refinery/" + id));
        }
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new Result(id, this.result, this.count,
                this.group == null ? "" : this.group, this.fluidIngreadient, this.advancement,
                new ResourceLocation(id.getNamespace(),
                        "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation p_200481_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_200481_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final FluidIngredient ingredient;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, String group, FluidIngredient ingredient,
                      Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.group = group;
            this.ingredient = ingredient;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @SuppressWarnings("deprecation")
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fluid", ingredient.getString());
            jsonObject.addProperty("amount", ingredient.getAmount());


            json.add("input", jsonObject);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            json.add("output", jsonobject);
            json.addProperty("required_energy", requiredEnergy);
            json.addProperty("work_time", workTime);
        }

        public IRecipeSerializer<?> getType() {
            return ModRecipes.REFINERY_RECIPE_SERIALIZER;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
