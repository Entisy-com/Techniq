package com.entisy.techniq.common.block.compressor.recipe;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.init.ModRecipes;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import com.google.gson.JsonArray;
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
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CompressorRecipeBuilder {

	private final Item result;
	private final int count;
	private final SimpleList<Ingredient> ingredients = new SimpleList<>();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private String group;
	private static int requiredEnergy = 200;
	private static int smeltTime = 200;

	public CompressorRecipeBuilder(IItemProvider provider, int count) {
		this.result = provider.asItem();
		this.count = count;
	}

	public static CompressorRecipeBuilder smelting(IItemProvider provider) {
		return new CompressorRecipeBuilder(provider, 1);
	}

	public static CompressorRecipeBuilder smelting(IItemProvider provider, int count) {
		return new CompressorRecipeBuilder(provider, count);
	}

	public CompressorRecipeBuilder requiredEnergy(int requiredEnergy) {
		this.requiredEnergy = requiredEnergy;
		return this;
	}

	public CompressorRecipeBuilder smeltTime(int smeltTime) {
		this.smeltTime = smeltTime;
		return this;
	}

//	public AlloySmelterRecipeBuilder requires(ITag<Item> tag) {
//		return this.requires(Ingredient.of(tag));
//	}

	public CompressorRecipeBuilder requires(IItemProvider item1, IItemProvider item2) {
		return this.requires(item1, 1, item2, 1);
	}

	public CompressorRecipeBuilder requires(IItemProvider item1, int count1, IItemProvider item2, int count2) {
		this.requires(Ingredient.of(item1), count1, Ingredient.of(item2), count2);
		return this;
	}

	public CompressorRecipeBuilder requires(Ingredient item1, Ingredient item2) {
		return this.requires(item1, 1, item2, 1);
	}

	public CompressorRecipeBuilder requires(Ingredient item1, int count1, Ingredient item2, int count2) {
		item1.getItems()[0].setCount(count1);
		item2.getItems()[0].setCount(count2);
		ingredients.append(item1);
		ingredients.append(item2);

		return this;
	}

	public CompressorRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
		this.advancement.addCriterion(p_200483_1_, p_200483_2_);
		return this;
	}

	public CompressorRecipeBuilder group(String p_200490_1_) {
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
			throw new IllegalStateException("Compressor Recipe " + id + " should remove its 'save' argument");
		} else {
			this.save(consumer, new ResourceLocation(Techniq.MOD_ID, "compressor/" + id));
		}
	}

	public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
		consumer.accept(new Result(id, this.result, this.count,
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
		private final SimpleList<Ingredient> ingredients;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;

		public Result(ResourceLocation id, Item result, int count, String group, SimpleList<Ingredient> ingredients,
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
			
			JsonArray jsonArray = new JsonArray();

	         for(int i = 0; i < ingredients.size(); i++) {
				 JsonObject jsonObject = new JsonObject();
				  if (ingredients.get(i) != null) {
					  jsonObject.addProperty("item", ingredients.get(i).toJson().toString().replace("{\"item\":\"", "").replace("\"}", ""));
				  } else {
					  jsonObject.addProperty("item", "null");
				  }
				 jsonObject.addProperty("count", ingredients.get(i).getItems()[0].getCount() | 1);
				 jsonArray.add(jsonObject);

	         }

			json.add("ingredients", jsonArray);
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
			if (this.count > 1) {
				jsonobject.addProperty("count", this.count);
			}

			json.add("output", jsonobject);
			json.addProperty("required_energy", requiredEnergy);
			json.addProperty("smelt_time", smeltTime);
		}

		public IRecipeSerializer<?> getType() {
			return ModRecipes.COMPRESSOR_RECIPE_SERIALIZER;
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
