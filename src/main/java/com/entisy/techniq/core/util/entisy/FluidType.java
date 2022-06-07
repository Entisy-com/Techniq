package com.entisy.techniq.core.util.entisy;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class FluidType {
    public static final FluidType NONE = new FluidType(FluidStack.EMPTY);

    private Fluid fluid;

    public FluidType(FluidStack fluid) {
        this.fluid = fluid.getFluid();
    }

    public String getString() {
        return Objects.requireNonNull(fluid.getRegistryName()).toString();
    }
}
