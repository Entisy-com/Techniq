package com.entisy.techniq.core.init;

import com.entisy.techniq.core.util.entisy.SimpleConfig;

public class ModConfigs {
    public static void register() {
        SimpleConfig MAIN = new SimpleConfig("main");

        MAIN.add("modId", "techniq");
        MAIN.add("machine.maxEnergy", 10000);
        MAIN.add("machine.maxFluid", 10000);

        MAIN.add("recipe.defaultRequiredEnergy", 200);
        MAIN.add("recipe.defaultWorkTime", 200);
    }
}
