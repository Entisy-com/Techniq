package com.entisy.techniq.core.util.entisy;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

public class ModFlowingFluidProperties extends ForgeFlowingFluid.Properties {

    private final int color;

    public ModFlowingFluidProperties(Supplier<? extends Fluid> still, Supplier<? extends Fluid> flowing, FluidAttributes.Builder attributes, int color) {
        super(still, flowing, attributes);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
