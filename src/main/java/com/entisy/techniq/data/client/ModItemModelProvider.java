package com.entisy.techniq.data.client;

import com.entisy.techniq.Techniq;

import com.entisy.techniq.common.block.PanelBlock;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Techniq.MOD_ID, existingFileHelper);
	}
	
	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	protected void registerModels() {
		ModBlocks.getSimpleBlocks().forEach(block -> block(block));
		ModBlocks.getComplexBlocks().forEach(block -> block(block));
		ModBlocks.getSemiComplexBlocks().forEach(block -> block(block));
		ModItems.getItems().forEach(item -> item(item));
	}

	private void block(Block block) {
		withExistingParent(block.getRegistryName().toString().replace(Techniq.MOD_ID + ":", ""),
				modLoc("block/" + block.getRegistryName().toString().replace(Techniq.MOD_ID + ":", "")));
	}

	private void item(Item item) {
		builder(item.getRegistryName().toString().replace(Techniq.MOD_ID + ":", ""),
				getExistingFile(mcLoc("item/generated")));
	}
	
	private ItemModelBuilder builder(String name, ModelFile parent) {
		return getBuilder(name).parent(parent).texture("layer0", "item/" + name);
	}
}
