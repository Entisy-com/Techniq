package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.util.entisy.ModFlowingFluidProperties;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class ModFluids {
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");

    public static FlowingFluid FLOWING_OIL;
    public static FlowingFluid OIL;
    public static FlowingFluid IRON;
    public static FlowingFluid DIAMOND;
    public static FlowingFluid GOLD;
    public static FlowingFluid LAPIS;
    public static FlowingFluid COAL;
    public static FlowingFluid REDSTONE;
    public static FlowingFluid EMERALD;

    public static ForgeFlowingFluid.Properties oilProps;

    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        oilProps = properties("oil", () -> OIL, () -> FLOWING_OIL, 0xFF822900)
                .block(ModBlocks.OIL)
                .bucket(ModItems.OIL_BUCKET);
        FLOWING_OIL = register("flowing_oil", new ForgeFlowingFluid.Flowing(oilProps));
        OIL = register("oil", new ForgeFlowingFluid.Source(oilProps));

        ForgeFlowingFluid.Properties ironProps = properties("molten_iron", () -> IRON, () -> IRON, 0xbf822900);
        IRON = register("molten_iron", new ForgeFlowingFluid.Source(ironProps));

        ForgeFlowingFluid.Properties diamondProps = properties("molten_diamond", () -> DIAMOND, () -> DIAMOND, 0xbf822900);
        DIAMOND = register("molten_diamond", new ForgeFlowingFluid.Source(diamondProps));

        ForgeFlowingFluid.Properties goldProps = properties("molten_gold", () -> GOLD, () -> GOLD, 0xbf822900);
        GOLD = register("molten_gold", new ForgeFlowingFluid.Source(goldProps));

        ForgeFlowingFluid.Properties lapisProps = properties("molten_lapis", () -> LAPIS, () -> LAPIS, 0xbf822900);
        LAPIS = register("molten_lapis", new ForgeFlowingFluid.Source(lapisProps));

        ForgeFlowingFluid.Properties coalProps = properties("molten_coal", () -> COAL, () -> COAL, 0xbf822900);
        COAL = register("molten_coal", new ForgeFlowingFluid.Source(coalProps));

        ForgeFlowingFluid.Properties redstoneProps = properties("molten_redstone", () -> REDSTONE, () -> REDSTONE, 0xbf822900);
        REDSTONE = register("molten_redstone", new ForgeFlowingFluid.Source(redstoneProps));

        ForgeFlowingFluid.Properties emeraldProps = properties("molten_emerald", () -> EMERALD, () -> EMERALD, 0xbf822900);
        EMERALD = register("molten_emerald", new ForgeFlowingFluid.Source(emeraldProps));
    }

    private static <T extends Fluid> T register(String name, T fluid) {
        ResourceLocation id = new ResourceLocation(Techniq.MOD_ID, name);
        fluid.setRegistryName(id);
        ForgeRegistries.FLUIDS.register(fluid);
        return fluid;
    }

    private static ModFlowingFluidProperties properties(String name, Supplier<Fluid> still, Supplier<Fluid> flowing, int color) {
        String tex = "block/" + name;
        return new ModFlowingFluidProperties (
                still,
                flowing,
                FluidAttributes.builder(
                        WATER_STILL,
                        WATER_FLOWING
                ).overlay(WATER_OVERLAY).color(color),
                color);
    }

    public static SimpleList<FlowingFluid> getFluids() {
        SimpleList<FlowingFluid> ret = new SimpleList<>();
        ret.append(OIL);
        return ret;
    }
}
