package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.util.entisy.betterLists.SimpleList;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModFluids {
    public static FlowingFluid FLOWING_OIL;
    public static FlowingFluid OIL;
    public static FlowingFluid IRON;
    public static FlowingFluid DIAMOND;
    public static FlowingFluid GOLD;
    public static FlowingFluid LAPIS;
    public static FlowingFluid COAL;
    public static FlowingFluid REDSTONE;
    public static FlowingFluid EMERALD;

    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        ForgeFlowingFluid.Properties oilProps = properties("oil", () -> OIL, () -> FLOWING_OIL)
                .block(() -> ModBlocks.OIL.get())
                .bucket(() -> ModItems.OIL_BUCKET.get());
        FLOWING_OIL = register("flowing_oil", new ForgeFlowingFluid.Flowing(oilProps));
        OIL = register("oil", new ForgeFlowingFluid.Source(oilProps));

        ForgeFlowingFluid.Properties ironProps = properties("molten_iron", () -> IRON, () -> IRON);
        IRON = register("molten_iron", new ForgeFlowingFluid.Source(ironProps));

        ForgeFlowingFluid.Properties diamondProps = properties("molten_diamond", () -> DIAMOND, () -> DIAMOND);
        DIAMOND = register("molten_diamond", new ForgeFlowingFluid.Source(diamondProps));

        ForgeFlowingFluid.Properties goldProps = properties("molten_gold", () -> GOLD, () -> GOLD);
        GOLD = register("molten_gold", new ForgeFlowingFluid.Source(goldProps));

        ForgeFlowingFluid.Properties lapisProps = properties("molten_lapis", () -> LAPIS, () -> LAPIS);
        LAPIS = register("molten_lapis", new ForgeFlowingFluid.Source(lapisProps));

        ForgeFlowingFluid.Properties coalProps = properties("molten_coal", () -> COAL, () -> COAL);
        COAL = register("molten_coal", new ForgeFlowingFluid.Source(coalProps));

        ForgeFlowingFluid.Properties redstoneProps = properties("molten_redstone", () -> REDSTONE, () -> REDSTONE);
        REDSTONE = register("molten_redstone", new ForgeFlowingFluid.Source(redstoneProps));

        ForgeFlowingFluid.Properties emeraldProps = properties("molten_emerald", () -> EMERALD, () -> EMERALD);
        EMERALD = register("molten_emerald", new ForgeFlowingFluid.Source(emeraldProps));
    }

    private static <T extends Fluid> T register(String name, T fluid) {
        ResourceLocation id = new ResourceLocation(Techniq.MOD_ID, name);
        fluid.setRegistryName(id);
        ForgeRegistries.FLUIDS.register(fluid);
        return fluid;
    }

    private static ForgeFlowingFluid.Properties properties(String name, Supplier<Fluid> still, Supplier<Fluid> flowing) {
        String tex = "block/" + name;
        return new ForgeFlowingFluid.Properties(still, flowing, FluidAttributes.builder(new ResourceLocation(Techniq.MOD_ID, tex + "_still"), new ResourceLocation(Techniq.MOD_ID, tex + "_flowing")));
    }

    public static final SimpleList<FlowingFluid> getFluids() {
        SimpleList<FlowingFluid> ret = new SimpleList<>();
        ret.append(OIL);
        return ret;
    }
}
