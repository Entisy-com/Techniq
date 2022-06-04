package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.IOException;

public class TechniqConfig {
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_ENERGY;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_FLUID;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_REQUIRED_ENERGY;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_WORK_TIME;
    private static ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec register() throws IOException {
        builder.push(Techniq.MOD_ID);

        MAX_ENERGY = builder.comment("Set the maximum amount of energy a machine can hold").define("maxEnergy", 100000);
        MAX_FLUID = builder.comment("Set the maximum amount of fluid a machine can hold").define("maxFluid", 100000);
        DEFAULT_REQUIRED_ENERGY = builder.comment("Set the amount of energy a machine will consume per recipe").define("defaultRequiredEnergy", 200);
        DEFAULT_WORK_TIME = builder.comment("Set the time a recipe will take").define("defaultWorkTime", 200);

        builder.pop();
        return builder.build();
    }
}
