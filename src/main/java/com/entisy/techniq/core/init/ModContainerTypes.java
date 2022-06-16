package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterContainer;
import com.entisy.techniq.common.block.battery.BatteryContainer;
//import com.entisy.techniq.common.block.displayCase.DisplayCaseContainer;
import com.entisy.techniq.common.block.blockBreaker.BlockBreakerContainer;
import com.entisy.techniq.common.block.blockPlacer.BlockPlacerContainer;
import com.entisy.techniq.common.block.charger.ChargerContainer;
import com.entisy.techniq.common.block.compressor.CompressorContainer;
import com.entisy.techniq.common.block.crusher.CrusherContainer;
import com.entisy.techniq.common.block.electricalFurnace.ElectricalFurnaceContainer;
import com.entisy.techniq.common.block.furnaceGenerator.FurnaceGeneratorContainer;
import com.entisy.techniq.common.block.harvester.HarvesterContainer;
import com.entisy.techniq.common.block.melter.MelterContainer;
import com.entisy.techniq.common.block.metalPress.MetalPressContainer;

import com.entisy.techniq.common.block.oreMiner.advancedOreMiner.AdvancedOreMinerContainer;
import com.entisy.techniq.common.block.oreMiner.simpleOreMiner.SimpleOreMinerContainer;
import com.entisy.techniq.common.block.refinery.RefineryContainer;
import com.entisy.techniq.common.item.backpack.BackpackContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, Techniq.MOD_ID);

//	public static final RegistryObject<ContainerType<DisplayCaseContainer>> DISPLAY_CASE_CONTAINER_TYPE = CONTAINER_TYPES
//			.register("display_case", () -> IForgeContainerType.create(DisplayCaseContainer::new));

	public static final RegistryObject<ContainerType<MetalPressContainer>> METAL_PRESS_CONTAINER_TYPE = CONTAINER_TYPES
			.register("metal_press", () -> IForgeContainerType.create(MetalPressContainer::new));

	public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("crusher", () -> IForgeContainerType.create(CrusherContainer::new));

	public static final RegistryObject<ContainerType<ChargerContainer>> CHARGER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("charger", () -> IForgeContainerType.create(ChargerContainer::new));

	public static final RegistryObject<ContainerType<RefineryContainer>> REFINERY_CONTAINER_TYPE = CONTAINER_TYPES
			.register("refinery", () -> IForgeContainerType.create(RefineryContainer::new));

	public static final RegistryObject<ContainerType<ElectricalFurnaceContainer>> ELECTRICAL_FURNACE_CONTAINER_TYPE = CONTAINER_TYPES
			.register("electrical_furnace", () -> IForgeContainerType.create(ElectricalFurnaceContainer::new));

	public static final RegistryObject<ContainerType<AlloySmelterContainer>> ALLOY_SMELTER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("alloy_smelter", () -> IForgeContainerType.create(AlloySmelterContainer::new));

	public static final RegistryObject<ContainerType<BatteryContainer>> BATTERY_CONTAINER_TYPE = CONTAINER_TYPES
			.register("battery", () -> IForgeContainerType.create(BatteryContainer::new));

	public static final RegistryObject<ContainerType<FurnaceGeneratorContainer>> FURNACE_GENERATOR_CONTAINER_TYPE = CONTAINER_TYPES
			.register("furnace_generator", () -> IForgeContainerType.create(FurnaceGeneratorContainer::new));

	public static final RegistryObject<ContainerType<SimpleOreMinerContainer>> SIMPLE_ORE_MINER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("simple_ore_miner", () -> IForgeContainerType.create(SimpleOreMinerContainer::new));

	public static final RegistryObject<ContainerType<AdvancedOreMinerContainer>> ADVANCED_ORE_MINER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("advanced_ore_miner", () -> IForgeContainerType.create(AdvancedOreMinerContainer::new));

	public static final RegistryObject<ContainerType<BlockBreakerContainer>> BLOCK_BREAKER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("block_breaker", () -> IForgeContainerType.create(BlockBreakerContainer::new));

	public static final RegistryObject<ContainerType<BlockPlacerContainer>> BLOCK_PLACER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("block_placer", () -> IForgeContainerType.create(BlockPlacerContainer::new));

	public static final RegistryObject<ContainerType<HarvesterContainer>> HARVESTER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("harvester", () -> IForgeContainerType.create(HarvesterContainer::new));

	public static final RegistryObject<ContainerType<BackpackContainer>> BACKPACK_CONTAINER_TYPE = CONTAINER_TYPES
			.register("backpack", () -> IForgeContainerType.create(BackpackContainer::new));

	public static final RegistryObject<ContainerType<CompressorContainer>> COMPRESSOR_CONTAINER_TYPE = CONTAINER_TYPES
			.register("compressor", () -> IForgeContainerType.create(CompressorContainer::new));

	public static final RegistryObject<ContainerType<MelterContainer>> MELTER_CONTAINER_TYPE = CONTAINER_TYPES
			.register("melter", () -> IForgeContainerType.create(MelterContainer::new));
}
