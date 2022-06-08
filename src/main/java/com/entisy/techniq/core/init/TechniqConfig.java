package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.IOException;

public class TechniqConfig {
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_ENERGY;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_FLUID;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_REQUIRED_ENERGY;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_WORK_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> LUNAR_PANEL;
    public static ForgeConfigSpec.ConfigValue<Integer> SOLAR_PANEL;
    public static ForgeConfigSpec.ConfigValue<Integer> BATTERY_CAPACITY;
    public static ForgeConfigSpec.ConfigValue<Integer> BATTERY_PACK_CAPACITY;
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec register() throws IOException {
        builder.push(Techniq.MOD_ID);

        MAX_ENERGY = builder.comment("Set the maximum amount of energy a machine can hold").define("maxEnergy", 100000);
        MAX_FLUID = builder.comment("Set the maximum amount of fluid a machine can hold").define("maxFluid", 100000);
        DEFAULT_REQUIRED_ENERGY = builder.comment("Set the amount of energy a machine will consume per recipe").define("defaultRequiredEnergy", 200);
        DEFAULT_WORK_TIME = builder.comment("Set the time a recipe will take").define("defaultWorkTime", 200);
        LUNAR_PANEL = builder.comment("Set the value of the Lunar Panel production rate").define("lunarPanel", 10);
        SOLAR_PANEL = builder.comment("Set the value of the Solar Panel production rate").define("solarPanel", 15);
        BATTERY_CAPACITY = builder.comment("Set the capacity of the Battery").define("batteryCapacity", 3000);
        BATTERY_PACK_CAPACITY = builder.comment("Set the capacity of the Battery Pack").define("batteryPackCapacity", 6000);

        builder.pop();
        return builder.build();
    }
}
