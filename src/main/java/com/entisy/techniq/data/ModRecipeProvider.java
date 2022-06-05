package com.entisy.techniq.data;

import com.entisy.techniq.common.block.alloySmelter.recipe.AlloySmelterRecipeBuilder;
import com.entisy.techniq.common.block.crusher.recipe.CrusherRecipeBuilder;
import com.entisy.techniq.common.block.electricalFurnace.recipe.ElectricalFurnaceRecipeBuilder;
import com.entisy.techniq.common.block.metalPress.recipe.MetalPressRecipeBuilder;
import com.entisy.techniq.common.block.refinery.recipe.RefineryRecipeBuilder;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModFluids;
import com.entisy.techniq.core.init.ModItems;
import com.entisy.techniq.core.init.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        final String mp = "_from_metal_press";
        final String ef = "_from_electrical_furnace";
        final String as = "_from_alloy_smelter";
        final String p = "_from_powder";
        final String r = "_from_refinery";
        final String c = "_from_crusher";

        ShapelessRecipeBuilder
                .shapeless(ModItems.COPPER_INGOT.get(), 9)
                .requires(ModBlocks.COPPER_BLOCK.get())
                .unlockedBy("has_item", has(ModBlocks.COPPER_BLOCK.get()))
                .save(consumer);


        ShapelessRecipeBuilder
                .shapeless(ModItems.STEEL_INGOT.get(), 9)
                .requires(ModBlocks.STEEL_BLOCK.get())
                .unlockedBy("has_item", has(ModBlocks.STEEL_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.COPPER_PLATE.get())
                .requires(ModTags.Items.INGOTS_COPPER)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.STEEL_PLATE.get())
                .requires(ModTags.Items.INGOTS_STEEL)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.IRON_PLATE.get())
                .requires(Items.IRON_INGOT)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.GOLD_PLATE.get())
                .requires(Items.GOLD_INGOT)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.DIAMOND_PLATE.get())
                .requires(Items.DIAMOND)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.IRON_ROD.get())
                .requires(ModTags.Items.PLATES_IRON)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.STEEL_ROD.get())
                .requires(ModTags.Items.PLATES_STEEL)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.COPPER_ROD.get())
                .requires(ModTags.Items.PLATES_COPPER)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.GOLD_ROD.get())
                .requires(ModTags.Items.PLATES_GOLD)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        ShapelessRecipeBuilder
                .shapeless(ModItems.DIAMOND_ROD.get())
                .requires(ModTags.Items.PLATES_DIAMOND)
                .requires(ModItems.HAMMER.get())
                .unlockedBy("has_item", has(ModItems.HAMMER.get()))
                .save(consumer);

        // METAL PRESS
        // PLATES
        MetalPressRecipeBuilder
                .metalPress(ModItems.COPPER_PLATE.get())
                .requires(ModTags.Items.INGOTS_COPPER)
                .requiredEnergy(400)
                .unlockedBy("has_item", has(ModTags.Items.INGOTS_COPPER))
                .save(consumer, "copper_plate" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.IRON_PLATE.get())
                .requires(Items.IRON_INGOT)
                .requiredEnergy(400)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(consumer, "iron_plate" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.GOLD_PLATE.get())
                .requires(Items.GOLD_INGOT)
                .requiredEnergy(400)
                .unlockedBy("has_item", has(Items.GOLD_INGOT))
                .save(consumer, "gold_plate" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.DIAMOND_PLATE.get())
                .requires(Items.DIAMOND)
                .requiredEnergy(400)
                .unlockedBy("has_item", has(Items.DIAMOND))
                .save(consumer, "diamond_plate" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.STEEL_PLATE.get())
                .requires(ModTags.Items.INGOTS_STEEL)
                .requiredEnergy(400)
                .unlockedBy("has_item", has(ModTags.Items.INGOTS_STEEL))
                .save(consumer, "steel_plate" + mp);

        // RODS
        MetalPressRecipeBuilder
                .metalPress(ModItems.COPPER_ROD.get())
                .requires(ModTags.Items.PLATES_COPPER)
                .requiredEnergy(300)
                .unlockedBy("has_item", has(ModItems.COPPER_PLATE.get()))
                .save(consumer, "copper_rod" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.IRON_ROD.get())
                .requires(ModTags.Items.PLATES_IRON)
                .requiredEnergy(300)
                .unlockedBy("has_item", has(ModItems.IRON_PLATE.get()))
                .save(consumer, "iron_rod" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.STEEL_ROD.get())
                .requires(ModTags.Items.PLATES_STEEL)
                .requiredEnergy(300)
                .unlockedBy("has_item", has(ModItems.STEEL_PLATE.get()))
                .save(consumer, "steel_rod" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.GOLD_ROD.get())
                .requires(ModTags.Items.PLATES_GOLD)
                .requiredEnergy(300)
                .unlockedBy("has_item", has(ModItems.GOLD_PLATE.get()))
                .save(consumer, "gold_rod" + mp);

        MetalPressRecipeBuilder
                .metalPress(ModItems.DIAMOND_ROD.get())
                .requires(ModTags.Items.PLATES_DIAMOND)
                .requiredEnergy(300)
                .unlockedBy("has_item", has(ModItems.DIAMOND_PLATE.get()))
                .save(consumer, "diamond_rod" + mp);

        // ELECTRICAL FURNACE
        ElectricalFurnaceRecipeBuilder
                .smelting(ModItems.COPPER_INGOT.get())
                .requires(ModTags.Items.ORES_COPPER)
                .unlockedBy("has_item", has(ModTags.Items.ORES_COPPER))
                .save(consumer, "copper_ingot" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.IRON_INGOT)
                .requires(ModTags.Items.ORES_IRON)
                .unlockedBy("has_item", has(ModTags.Items.ORES_IRON))
                .save(consumer, "iron_ingot" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.GOLD_INGOT)
                .requires(ModTags.Items.ORES_GOLD)
                .unlockedBy("has_item", has(ModTags.Items.ORES_GOLD))
                .save(consumer, "gold_ingot" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.COAL)
                .requires(ModTags.Items.ORES_COAL)
                .unlockedBy("has_item", has(ModTags.Items.ORES_COAL))
                .save(consumer, "coal" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.QUARTZ)
                .requires(ModTags.Items.ORES_QUARTZ)
                .unlockedBy("has_item", has(ModTags.Items.ORES_QUARTZ))
                .save(consumer, "quartz" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.REDSTONE)
                .requires(ModTags.Items.ORES_REDSTONE)
                .unlockedBy("has_item", has(ModTags.Items.ORES_REDSTONE))
                .save(consumer, "redstone" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.EMERALD)
                .requires(ModTags.Items.ORES_EMERALD)
                .unlockedBy("has_item", has(ModTags.Items.ORES_EMERALD))
                .save(consumer, "emerald" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.DIAMOND)
                .requires(ModTags.Items.ORES_DIAMOND)
                .unlockedBy("has_item", has(ModTags.Items.ORES_DIAMOND))
                .save(consumer, "diamond" + ef);

        ElectricalFurnaceRecipeBuilder
                .smelting(Items.LAPIS_LAZULI)
                .requires(ModTags.Items.ORES_LAPIS)
                .unlockedBy("has_item", has(ModTags.Items.ORES_LAPIS))
                .save(consumer, "lapis" + ef);

        // HAMMER
        ShapedRecipeBuilder.shaped(ModItems.HAMMER.get()).define('I', Items.IRON_INGOT).define('S', Items.STICK)
                .pattern(" I ")
                .pattern(" SI")
                .pattern("S  ").unlockedBy("has_item", has(ModItems.HAMMER.get())).save(consumer);

        // ALLOY SMELTER
        AlloySmelterRecipeBuilder
                .smelting(ModItems.REDSTONE_ALLOY_INGOT.get())
                .requires(Items.REDSTONE, 3, Items.IRON_INGOT, 1)
                .unlockedBy("has_item", has(Items.REDSTONE))
                .save(consumer, "redstone_alloy_ingot" + as);

        AlloySmelterRecipeBuilder
                .smelting(ModItems.STEEL_INGOT.get())
                .requires(Items.IRON_INGOT, 2, Items.COAL, 1)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(consumer, "steel_ingot" + as);

//        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ALUMINUM_ORE.get()), ModItems.ALUMINUM_INGOT.get(), 0.7F, 100).unlockedBy("has_item", has(ModBlocks.ALUMINUM_ORE.get())).save(consumer, modid("aluminium_ore_blasting"));

//        CookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.ALUMINUM_ORE.get()), ModItems.ALUMINUM_INGOT.get(), 0.7F, 200).unlockedBy("has_item", has(ModBlocks.ALUMINUM_ORE.get())).save(consumer, modid("aluminium_ore_smelting"));

        // OTHER
        ShapedRecipeBuilder.shaped(ModItems.DRILL.get()).define('P', ModItems.PLASTIC.get()).define('R', Items.REDSTONE).define('H', ModItems.DRILL_HEAD.get())
                .pattern("  P")
                .pattern(" R ")
                .pattern("H  ").unlockedBy("has_item", has(ModItems.PLASTIC.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.DRILL_HEAD.get()).define('I', Items.IRON_INGOT).define('S', ModItems.STEEL_INGOT.get())
                .pattern("ISI")
                .pattern(" I ").unlockedBy("has_item", has(ModItems.IRON_ROD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.WRENCH.get()).define('I', ModItems.COPPER_INGOT.get()).define('R', ModItems.COPPER_ROD.get())
                .pattern("I I")
                .pattern(" R ")
                .pattern(" R ").unlockedBy("has_item", has(ModItems.IRON_ROD.get())).save(consumer);

        // FRAMES
        ShapedRecipeBuilder.shaped(ModItems.MACHINE_FRAME.get()).define('R', ModItems.IRON_ROD.get()).define('P', ModItems.IRON_PLATE.get())
                .pattern("PRP")
                .pattern("R R")
                .pattern("PRP").unlockedBy("has_item", has(ModItems.IRON_ROD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.HEAVY_MACHINE_FRAME.get()).define('R', ModItems.STEEL_ROD.get()).define('P', ModItems.STEEL_PLATE.get())
                .pattern("PRP")
                .pattern("R R")
                .pattern("PRP").unlockedBy("has_item", has(ModItems.STEEL_ROD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.GOLD_MACHINE_FRAME.get()).define('R', ModItems.GOLD_ROD.get()).define('P', ModItems.GOLD_PLATE.get())
                .pattern("PRP")
                .pattern("R R")
                .pattern("PRP").unlockedBy("has_item", has(ModItems.GOLD_ROD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.DIAMOND_MACHINE_FRAME.get()).define('R', ModItems.DIAMOND_ROD.get()).define('P', ModItems.DIAMOND_PLATE.get())
                .pattern("PRP")
                .pattern("R R")
                .pattern("PRP").unlockedBy("has_item", has(ModItems.DIAMOND_ROD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.PLASTIC.get()).define('P', ModItems.PLASTIC_PIECE.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.PLASTIC_PIECE.get())).save(consumer);

        // MACHINES
        ShapedRecipeBuilder.shaped(ModBlocks.ALLOY_SMELTER.get()).define('B', Blocks.IRON_BLOCK).define('I', Items.IRON_INGOT).define('M', ModItems.MACHINE_FRAME.get())
                .pattern("BIB")
                .pattern("IMI")
                .pattern("BIB").unlockedBy("has_item", has(ModItems.MACHINE_FRAME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.REFINERY.get()).define('B', Items.BUCKET).define('I', Items.IRON_INGOT).define('C', Blocks.COBBLESTONE)
                .pattern("III")
                .pattern("IBI")
                .pattern("CCC").unlockedBy("has_item", has(Items.BUCKET)).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ELECTRICAL_FURNACE.get()).define('B', Blocks.IRON_BLOCK).define('I', ModItems.STEEL_INGOT.get()).define('M', ModItems.MACHINE_FRAME.get()).define('F', Blocks.FURNACE)
                .pattern("BIB")
                .pattern("IMI")
                .pattern("BFB").unlockedBy("has_item", has(ModItems.MACHINE_FRAME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.FURNACE_GENERATOR.get()).define('B', Blocks.IRON_BLOCK).define('I', Items.IRON_INGOT).define('M', ModItems.MACHINE_FRAME.get()).define('F', Blocks.FURNACE)
                .pattern("BIB")
                .pattern("IMI")
                .pattern("BFB").unlockedBy("has_item", has(ModItems.MACHINE_FRAME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.METAL_PRESS.get()).define('S', ModBlocks.STEEL_BLOCK.get()).define('B', Blocks.IRON_BLOCK).define('I', ModItems.STEEL_INGOT.get()).define('D', ModItems.DIAMOND_MACHINE_FRAME.get())
                .pattern("SIS")
                .pattern("IDI")
                .pattern("BIB").unlockedBy("has_item", has(ModItems.MACHINE_FRAME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.SIMPLE_ORE_MINER.get()).define('G', ModItems.GOLD_MACHINE_FRAME.get()).define('B', Blocks.IRON_BLOCK).define('I', Items.IRON_INGOT).define('D', ModItems.DRILL.get())
                .pattern("GIG")
                .pattern("IDI")
                .pattern("BIB").unlockedBy("has_item", has(ModItems.DRILL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ADVANCED_ORE_MINER.get()).define('G', ModItems.DIAMOND_MACHINE_FRAME.get()).define('B', ModBlocks.STEEL_BLOCK.get()).define('I', ModItems.STEEL_INGOT.get()).define('D', ModItems.DRILL.get())
                .pattern("GIG")
                .pattern("IDI")
                .pattern("BIB").unlockedBy("has_item", has(ModItems.DRILL.get())).save(consumer);

        // METAL BLOCKS
        ShapedRecipeBuilder.shaped(ModBlocks.COPPER_BLOCK.get()).define('I', ModItems.COPPER_INGOT.get())
                .pattern("III")
                .pattern("III")
                .pattern("III").unlockedBy("has_item", has(ModItems.COPPER_INGOT.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.STEEL_BLOCK.get()).define('I', ModItems.STEEL_INGOT.get())
                .pattern("III")
                .pattern("III")
                .pattern("III").unlockedBy("has_item", has(ModItems.STEEL_INGOT.get())).save(consumer);

        // INGOTS FROM POWDER
        ShapedRecipeBuilder.shaped(Items.COAL).define('P', ModItems.COAL_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.COAL_POWDER.get())).save(consumer, "coal" + p);

        ShapedRecipeBuilder.shaped(Items.IRON_INGOT).define('P', ModItems.IRON_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.IRON_POWDER.get())).save(consumer, "iron_ingot" + p);

        ShapedRecipeBuilder.shaped(Items.DIAMOND).define('P', ModItems.DIAMOND_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.DIAMOND_POWDER.get())).save(consumer, "diamond" + p);

        ShapedRecipeBuilder.shaped(Items.LAPIS_LAZULI).define('P', ModItems.LAPIS_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.LAPIS_POWDER.get())).save(consumer, "lapis" + p);

        ShapedRecipeBuilder.shaped(Items.EMERALD).define('P', ModItems.EMERALD_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.EMERALD_POWDER.get())).save(consumer, "emerald" + p);

        ShapedRecipeBuilder.shaped(Items.GOLD_INGOT).define('P', ModItems.GOLD_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.GOLD_POWDER.get())).save(consumer, "gold_ingot" + p);

        ShapedRecipeBuilder.shaped(Items.QUARTZ).define('P', ModItems.QUARTZ_POWDER.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP").unlockedBy("has_item", has(ModItems.QUARTZ_POWDER.get())).save(consumer, "quartz" + p);

        ShapedRecipeBuilder.shaped(ModBlocks.BATTERY.get())
                .define('I', Items.IRON_INGOT)
                .define('B', ModItems.BATTERY_PACK_ITEM.get())
                .define('C', ModItems.CIRCUIT_BOARD.get())
                .define('S', Blocks.STONE)
                .pattern("III")
                .pattern("BCB")
                .pattern("SSS").unlockedBy("has_item", has(ModItems.CIRCUIT_BOARD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BATTERY_PACK_ITEM.get())
                .define('B', ModItems.BATTERY_ITEM.get())
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_item", has(ModItems.BATTERY_ITEM.get()))
                .save(consumer);

        RefineryRecipeBuilder.refining(ModItems.PLASTIC_PIECE.get())
                .requires(ModFluids.OIL, 500)
                .workTime(200)
                .requiredEnergy(500)
                .unlockedBy("has_item", has(ModItems.OIL_BUCKET.get())).save(consumer, "plastic_piece" + r);


        RefineryRecipeBuilder.refining(ModItems.PLASTIC.get())
                .requires(Fluids.WATER, 500)
                .workTime(200)
                .requiredEnergy(500)
                .unlockedBy("has_item", has(ModItems.OIL_BUCKET.get())).save(consumer, "plastic" + r);

        CrusherRecipeBuilder.crushing(ModItems.COAL_POWDER.get(), 2)
                .requires(Items.COAL_ORE)
                .smeltTime(200)
                .requiredEnergy(500)
                .unlockedBy("has_item", has(Blocks.COAL_ORE))
                .save(consumer, "coal_powder" + c);
    }
}
