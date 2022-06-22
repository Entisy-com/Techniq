package com.entisy.techniq.core.util.entisy;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

@SuppressWarnings("ALL")
public class FluidType {
    public static final FluidType NONE = new FluidType(new CompoundNBT());
    private final CompoundNBT nbt;

    private FluidType(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static FluidType getType(FluidStack stack) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("FluidType", Objects.requireNonNull(stack.getFluid().getRegistryName()).getNamespace());
        return new FluidType(nbt);
    }

    public static FluidType getType(BucketItem item) {
        CompoundNBT nbt = new CompoundNBT();
        Fluid fluid = item.getFluid();
        nbt.putString("FluidType", Objects.requireNonNull(fluid.getFluid().getRegistryName()).getNamespace());
        return new FluidType(nbt);
    }

    public CompoundNBT getTag() {
        return nbt;
    }
}
