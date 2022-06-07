package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.*;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterBlock;
import com.entisy.techniq.common.block.battery.BatteryBlock;
import com.entisy.techniq.common.block.blockBreaker.BlockBreakerBlock;
import com.entisy.techniq.common.block.blockPlacer.BlockPlacerBlock;
import com.entisy.techniq.common.block.cable.energyCable.EnergyCableBlock;
import com.entisy.techniq.common.block.cable.fluidCable.FluidCableBlock;
import com.entisy.techniq.common.block.cable.itemCable.ItemCableBlock;
import com.entisy.techniq.common.block.charger.ChargerBlock;
import com.entisy.techniq.common.block.crusher.CrusherBlock;
import com.entisy.techniq.common.block.electricalFurnace.ElectricalFurnaceBlock;
import com.entisy.techniq.common.block.furnaceGenerator.FurnaceGeneratorBlock;
import com.entisy.techniq.common.block.harvester.HarvesterBlock;
import com.entisy.techniq.common.block.metalPress.MetalPressBlock;
import com.entisy.techniq.common.block.oreMiner.advancedOreMiner.AdvancedOreMinerBlock;
import com.entisy.techniq.common.block.oreMiner.simpleOreMiner.SimpleOreMinerBlock;
import com.entisy.techniq.common.block.refinery.RefineryBlock;
import com.entisy.techniq.common.block.solarPanel.SolarPanelBlock;
import com.entisy.techniq.common.block.waterSource.WaterSourceBlock;
import com.entisy.techniq.core.tab.TechniqTab;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Techniq.MOD_ID);

    /**
     * TEMP
     */
    public static final RegistryObject<SimpleBlock> INFINITE_WATER_SOURCE = register("infinite_water_source", WaterSourceBlock::new);

    /**
     * METAL
     */
    public static final RegistryObject<SimpleBlock> COPPER_BLOCK = register("copper_block",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<SimpleBlock> STEEL_BLOCK = register("steel_block",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)));

    /**
     * ORE
     */
    public static final RegistryObject<SimpleBlock> COPPER_ORE = register("copper_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<SimpleBlock> END_COPPER_ORE = register("end_copper_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(ModBlocks.COPPER_ORE.get())));
    public static final RegistryObject<SimpleBlock> NETHER_COPPER_ORE = register("nether_copper_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(ModBlocks.COPPER_ORE.get())));
    public static final RegistryObject<SimpleBlock> QUARTZ_ORE = register("quartz_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));
    /**
     * END
     */
    public static final RegistryObject<SimpleBlock> END_COAL_ORE = register("end_coal_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<SimpleBlock> END_DIAMOND_ORE = register("end_diamond_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<SimpleBlock> END_EMERALD_ORE = register("end_emerald_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.EMERALD_ORE)));
    public static final RegistryObject<SimpleBlock> END_GOLD_ORE = register("end_gold_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<SimpleBlock> END_IRON_ORE = register("end_iron_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<SimpleBlock> END_LAPIS_ORE = register("end_lapis_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.LAPIS_ORE)));
    public static final RegistryObject<SimpleBlock> END_QUARTZ_ORE = register("end_quartz_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<SimpleBlock> END_REDSTONE_ORE = register("end_redstone_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.LAPIS_ORE)));
    /**
     * NETHER
     */
    public static final RegistryObject<SimpleBlock> NETHER_COAL_ORE = register("nether_coal_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<SimpleBlock> NETHER_DIAMOND_ORE = register("nether_diamond_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.DIAMOND_ORE)));

    public static final RegistryObject<SimpleBlock> NETHER_EMERALD_ORE = register("nether_emerald_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.EMERALD_ORE)));

    public static final RegistryObject<SimpleBlock> NETHER_GOLD_ORE = register("nether_gold_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.GOLD_ORE)));

    public static final RegistryObject<SimpleBlock> NETHER_IRON_ORE = register("nether_iron_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryObject<SimpleBlock> NETHER_LAPIS_ORE = register("nether_lapis_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.LAPIS_ORE)));

    public static final RegistryObject<SimpleBlock> NETHER_REDSTONE_ORE = register("nether_redstone_ore",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.LAPIS_ORE)));

    /*
     * FLUIDS
     */
    public static final RegistryObject<FlowingFluidBlock> OIL = registerFluid(
            "oil", () -> ModFluids.OIL);

    /*
     * MACHINE
     */
    public static final RegistryObject<ComplexMachineBlock> BLOCK_BREAKER = register("block_breaker",
            BlockBreakerBlock::new);

    public static final RegistryObject<SemiComplexMachineBlock> CRUSHER = register("crusher",
            CrusherBlock::new);

    public static final RegistryObject<SemiComplexMachineBlock> CHARGER = register("charger",
            ChargerBlock::new);

    public static final RegistryObject<ComplexMachineBlock> BLOCK_PLACER = register("block_placer",
            BlockPlacerBlock::new);

    public static final RegistryObject<SimpleMachineBlock> HARVESTER = register("harvester",
            HarvesterBlock::new);

    public static final RegistryObject<ComplexMachineBlock> METAL_PRESS = register("metal_press",
            MetalPressBlock::new);

    public static final RegistryObject<ComplexMachineBlock> REFINERY = register("refinery",
            RefineryBlock::new);

    public static final RegistryObject<ComplexMachineBlock> ELECTRICAL_FURNACE = register("electrical_furnace",
            ElectricalFurnaceBlock::new);

    public static final RegistryObject<ComplexMachineBlock> ALLOY_SMELTER = register("alloy_smelter",
            AlloySmelterBlock::new);

    public static final RegistryObject<SemiComplexMachineBlock> BATTERY = register("battery_block",
            BatteryBlock::new);

    public static final RegistryObject<ComplexMachineBlock> FURNACE_GENERATOR = register("furnace_generator",
            FurnaceGeneratorBlock::new);

    public static final RegistryObject<SemiComplexMachineBlock> SIMPLE_ORE_MINER = register("simple_ore_miner",
            SimpleOreMinerBlock::new);

    public static final RegistryObject<SemiComplexMachineBlock> ADVANCED_ORE_MINER = register("advanced_ore_miner",
            AdvancedOreMinerBlock::new);

    public static final RegistryObject<SimpleMachineBlock> SOLAR_PANEL = register("solar_panel",
            SolarPanelBlock::new);


    public static final RegistryObject<SimpleBlock> RAINBOW_WOOL = register("rainbow_wool",
            () -> new SimpleBlock(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL)));

    /*
     * CABLES
     */
    public static final RegistryObject<SixWayMachineBlock> CABLE = register("energy_cable",
            () -> new EnergyCableBlock(AbstractBlock.Properties.of(Material.DECORATION)
                    .strength(1, 5)));
    public static final RegistryObject<SixWayMachineBlock> ITEM_CABLE = register("item_cable",
            () -> new ItemCableBlock(AbstractBlock.Properties.of(Material.DECORATION)
                    .strength(1, 5)));
    public static final RegistryObject<SixWayMachineBlock> FLUID_CABLE = register("fluid_cable",
            () -> new FluidCableBlock(AbstractBlock.Properties.of(Material.DECORATION)
                    .strength(1, 5)));

    private static RegistryObject<FlowingFluidBlock> registerFluid(String name, Supplier<FlowingFluid> fluid) {
        return registerNoItem(name, () ->
                new FlowingFluidBlock(fluid, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static SimpleList<SimpleBlock> getSimpleBlocks() {
        SimpleList<SimpleBlock> ret = new SimpleList<>();
        BLOCKS.getEntries().forEach(b -> {
            if (b.get() instanceof SimpleBlock) {
                ret.append((SimpleBlock) b.get());
            }
        });
        return ret;
    }

    public static SimpleList<ComplexBlock> getComplexBlocks() {
        SimpleList<ComplexBlock> ret = new SimpleList<>();
        BLOCKS.getEntries().forEach(b -> {
            if (b.get() instanceof ComplexBlock) {
                ret.append((ComplexBlock) b.get());
            }
        });
        return ret;
    }

    public static SimpleList<SemiComplexBlock> getSemiComplexBlocks() {
        SimpleList<SemiComplexBlock> ret = new SimpleList<>();
        BLOCKS.getEntries().forEach(b -> {
            if (b.get() instanceof SemiComplexBlock) {
                ret.append((SemiComplexBlock) b.get());
            }
        });
        return ret;
    }

    public static SimpleList<Block> getBlocks() {
        SimpleList<Block> ret = new SimpleList<>();
        BLOCKS.getEntries().forEach(b -> {
            if (!(b.get() instanceof FlowingFluidBlock)) {
                ret.append(b.get());
            }
        });
        return ret;
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block) {
        RegistryObject<T> obj = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(
                obj.get(),
                new Item.Properties().tab(TechniqTab.TECHNIQ_TAB)));
        return obj;
    }
}
