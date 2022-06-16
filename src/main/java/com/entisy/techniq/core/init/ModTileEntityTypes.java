package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterTileEntity;
import com.entisy.techniq.common.block.battery.BatteryTileEntity;
import com.entisy.techniq.common.block.blockBreaker.BlockBreakerTileEntity;
import com.entisy.techniq.common.block.blockPlacer.BlockPlacerTileEntity;
import com.entisy.techniq.common.block.cable.energyCable.EnergyCableTileEntity;
import com.entisy.techniq.common.block.cable.fluidCable.FluidCableTileEntity;
import com.entisy.techniq.common.block.cable.itemCable.ItemCableTileEntity;
import com.entisy.techniq.common.block.charger.ChargerTileEntity;
import com.entisy.techniq.common.block.compressor.CompressorTileEntity;
import com.entisy.techniq.common.block.crusher.CrusherTileEntity;
import com.entisy.techniq.common.block.electricalFurnace.ElectricalFurnaceTileEntity;
import com.entisy.techniq.common.block.furnaceGenerator.FurnaceGeneratorTileEntity;
import com.entisy.techniq.common.block.harvester.HarvesterTileEntity;
import com.entisy.techniq.common.block.lunarPanel.LunarPanelTileEntity;
import com.entisy.techniq.common.block.melter.MelterTileEntity;
import com.entisy.techniq.common.block.metalPress.MetalPressTileEntity;
import com.entisy.techniq.common.block.oreMiner.advancedOreMiner.AdvancedOreMinerTileEntity;
import com.entisy.techniq.common.block.oreMiner.simpleOreMiner.SimpleOreMinerTileEntity;
import com.entisy.techniq.common.block.refinery.RefineryTileEntity;
import com.entisy.techniq.common.block.solarPanel.SolarPanelTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, Techniq.MOD_ID);

    public static final RegistryObject<TileEntityType<BlockBreakerTileEntity>> BLOCK_BREAKER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
            .register("block_breaker", () -> TileEntityType.Builder
                    .of(BlockBreakerTileEntity::new, ModBlocks.BLOCK_BREAKER.get()).build(null));

    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("crusher", () -> TileEntityType.Builder
                    .of(CrusherTileEntity::new, ModBlocks.CRUSHER.get()).build(null));

    public static final RegistryObject<TileEntityType<BlockPlacerTileEntity>> BLOCK_PLACER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
            .register("block_placer", () -> TileEntityType.Builder
                    .of(BlockPlacerTileEntity::new, ModBlocks.BLOCK_PLACER.get()).build(null));

    public static final RegistryObject<TileEntityType<HarvesterTileEntity>> HARVESTER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
            .register("harvester", () -> TileEntityType.Builder
                    .of(HarvesterTileEntity::new, ModBlocks.HARVESTER.get()).build(null));

    public static final RegistryObject<TileEntityType<MetalPressTileEntity>> METAL_PRESS_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("metal_press", () -> TileEntityType.Builder
                    .of(MetalPressTileEntity::new, ModBlocks.METAL_PRESS.get()).build(null));

    public static final RegistryObject<TileEntityType<RefineryTileEntity>> REFINERY_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("refinery", () -> TileEntityType.Builder
                    .of(RefineryTileEntity::new, ModBlocks.REFINERY.get()).build(null));

    public static final RegistryObject<TileEntityType<ElectricalFurnaceTileEntity>> ELECTRICAL_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("electrical_furnace", () -> TileEntityType.Builder
                    .of(ElectricalFurnaceTileEntity::new, ModBlocks.ELECTRICAL_FURNACE.get()).build(null));

    public static final RegistryObject<TileEntityType<AlloySmelterTileEntity>> ALLOY_SMELTER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("alloy_smelter", () -> TileEntityType.Builder
                    .of(AlloySmelterTileEntity::new, ModBlocks.ALLOY_SMELTER.get()).build(null));

    public static final RegistryObject<TileEntityType<EnergyCableTileEntity>> CABLE_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("cable", () -> TileEntityType.Builder
                    .of(EnergyCableTileEntity::new, ModBlocks.CABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<ItemCableTileEntity>> ITEM_CABLE_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("item_cable", () -> TileEntityType.Builder
                    .of(ItemCableTileEntity::new, ModBlocks.ITEM_CABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<FluidCableTileEntity>> FLUID_CABLE_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("transfer_node", () -> TileEntityType.Builder
                    .of(FluidCableTileEntity::new, ModBlocks.FLUID_CABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<BatteryTileEntity>> BATTERY_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("battery", () -> TileEntityType.Builder
                    .of(BatteryTileEntity::new, ModBlocks.BATTERY.get()).build(null));

    public static final RegistryObject<TileEntityType<FurnaceGeneratorTileEntity>> FURNACE_GENERATOR_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("furnace_generator", () -> TileEntityType.Builder
                    .of(FurnaceGeneratorTileEntity::new, ModBlocks.FURNACE_GENERATOR.get()).build(null));

    public static final RegistryObject<TileEntityType<SimpleOreMinerTileEntity>> SIMPLE_ORE_MINER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("simple_ore_miner", () -> TileEntityType.Builder
                    .of(SimpleOreMinerTileEntity::new, ModBlocks.SIMPLE_ORE_MINER.get()).build(null));

    public static final RegistryObject<TileEntityType<AdvancedOreMinerTileEntity>> ADVANCED_ORE_MINER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("advanced_ore_miner", () -> TileEntityType.Builder
                    .of(AdvancedOreMinerTileEntity::new, ModBlocks.ADVANCED_ORE_MINER.get()).build(null));

    public static final RegistryObject<TileEntityType<ChargerTileEntity>> CHARGER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("charger", () -> TileEntityType.Builder
                    .of(ChargerTileEntity::new, ModBlocks.CHARGER.get()).build(null));

    public static final RegistryObject<TileEntityType<SolarPanelTileEntity>> SOLAR_PANEL_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("solar_panel", () -> TileEntityType.Builder
                    .of(SolarPanelTileEntity::new, ModBlocks.SOLAR_PANEL.get()).build(null));

    public static final RegistryObject<TileEntityType<LunarPanelTileEntity>> LUNAR_PANEL_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("lunar_panel", () -> TileEntityType.Builder
                    .of(LunarPanelTileEntity::new, ModBlocks.LUNAR_PANEL.get()).build(null));

    public static final RegistryObject<TileEntityType<CompressorTileEntity>> COMPRESSOR_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("compressor", () -> TileEntityType.Builder
                    .of(CompressorTileEntity::new, ModBlocks.COMPRESSOR.get()).build(null));


    public static final RegistryObject<TileEntityType<MelterTileEntity>> MELTER_TILE_ENTITY = TILE_ENTITY_TYPES
            .register("compressor", () -> TileEntityType.Builder
                    .of(MelterTileEntity::new, ModBlocks.MELTER.get()).build(null));
}
