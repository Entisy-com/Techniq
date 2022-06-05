package com.entisy.techniq.common.block.crusher.recipe;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.init.ModRecipes;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CrusherRecipeBuilder {

    private final Item result;
    private final int count;
    private static int requiredEnergy = 200;
    private static int smeltTime = 200;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public CrusherRecipeBuilder(IItemProvider provider, int count) {
        this.result = provider.asItem();
        this.count = count;
    }

    public static CrusherRecipeBuilder crushing(IItemProvider provider) {
        return new CrusherRecipeBuilder(provider, 1);
    }

    public static CrusherRecipeBuilder crushing(IItemProvider provider, int count) {
        return new CrusherRecipeBuilder(provider, count);
    }

    public CrusherRecipeBuilder crusher(int requiredEnergy) {
        this.requiredEnergy = requiredEnergy;
        return this;
    }

    public CrusherRecipeBuilder smeltTime(int smeltTime) {
        this.smeltTime = smeltTime;
        return this;
    }

    public CrusherRecipeBuilder requires(ITag<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public CrusherRecipeBuilder requires(IItemProvider provider) {
        return this.requires(provider, 1);
    }

    public CrusherRecipeBuilder requires(IItemProvider provider, int count) {
        for (int i = 0; i < count; ++i) {
            this.requires(Ingredient.of(provider));
        }

        return this;
    }

    public CrusherRecipeBuilder requiredEnergy(int amount) {
        CrusherRecipeBuilder.requiredEnergy = amount;
        return this;
    }

    public CrusherRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public CrusherRecipeBuilder requires(Ingredient ingredient, int count) {
        for (int i = 0; i < count; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public CrusherRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public CrusherRecipeBuilder group(String p_200490_1_) {
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
            throw new IllegalStateException("Crusher Recipe " + id + " should remove its 'save' argument");
        } else {
            this.save(consumer, new ResourceLocation(Techniq.MOD_ID, "crusher/" + id));
        }
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new CrusherRecipeBuilder.Result(id, this.result, this.count,
                this.group == null ? "" : this.group, this.ingredients, this.advancement,
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
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, String group, List<Ingredient> ingredients,
                      Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.group = group;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @SuppressWarnings("deprecation")
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            json.add("input", ingredients.get(0).toJson());
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            json.add("output", jsonobject);
            json.addProperty("required_energy", requiredEnergy);
            json.addProperty("work_time", smeltTime);
        }

        public IRecipeSerializer<?> getType() {
            return ModRecipes.CRUSHER_RECIPE_SERIALIZER;
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
