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

    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        ForgeFlowingFluid.Properties oilProps = properties("oil", () -> OIL, () -> FLOWING_OIL)
                .block(() -> ModBlocks.OIL.get())
                .bucket(() -> ModItems.OIL_BUCKET.get());
        FLOWING_OIL = register("flowing_oil", new ForgeFlowingFluid.Flowing(oilProps));
        OIL = register("oil", new ForgeFlowingFluid.Source(oilProps));
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
