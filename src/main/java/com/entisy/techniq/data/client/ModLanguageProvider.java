package com.entisy.techniq.data.client;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.core.init.ModBlocks;
import com.entisy.techniq.core.init.ModFluids;
import com.entisy.techniq.core.init.ModItems;
import com.entisy.techniq.core.init.TechniqConfig;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen) {
        super(gen, Techniq.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + Techniq.MOD_ID + "_tab", "Techniq");
        add("tooltip." + Techniq.MOD_ID + ".hidden", "hold \u00A7eSHIFT\u00A7r for more Information!");
        add("tooltip." + Techniq.MOD_ID + ".lunar_panel", TechniqConfig.LUNAR_PANEL.get() + "TE/Tick");
        add("tooltip." + Techniq.MOD_ID + ".solar_panel", TechniqConfig.SOLAR_PANEL.get() + "TE/Tick");
        add("tooltip." + Techniq.MOD_ID + ".battery", "holds " + TechniqConfig.BATTERY_CAPACITY.get() + "TE");
        add("tooltip." + Techniq.MOD_ID + ".battery_pack", "holds " + TechniqConfig.BATTERY_PACK_CAPACITY.get() + "TE");

        ModItems.getAllItems().forEach(item -> addItem(item));
        ModBlocks.getBlocks().forEach(block -> addBlock(block));
        ModBlocks.getPanels().forEach(block -> addBlock(block));
        ModFluids.getFluids().forEach(fluid -> addFluid(fluid));
    }

    private void addBlock(Block block) {
        add(block, getName(block));
    }

    private void addFluid(FlowingFluid fluid) {
        add(fluid, getName(fluid));
    }

    private void addItem(Item item) {
        add(item, getName(item));
    }

    private String getName(Item item) {
        String itemId = item.getRegistryName().toString().replace(Techniq.MOD_ID + ":", "");
        String[] args = itemId.split("_");
        String ret = "";
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                ret += upperCaseWord(args[i]) + " ";
            } else {
                ret += upperCaseWord(args[i]);
            }
        }
        return ret;
    }

    private String getName(FlowingFluid fluid) {
        String itemId = fluid.getRegistryName().toString().replace(Techniq.MOD_ID + ":", "");
        String[] args = itemId.split("_");
        String ret = "";
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                ret += upperCaseWord(args[i]) + " ";
            } else {
                ret += upperCaseWord(args[i]);
            }
        }
        return ret;
    }

    private String getName(Block block) {
        String itemId = block.getRegistryName().toString().replace(Techniq.MOD_ID + ":", "");
        String[] args = itemId.split("_");
        String ret = "";
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                ret += upperCaseWord(args[i]) + " ";
            } else {
                ret += upperCaseWord(args[i]);
            }
        }
        return ret;
    }

    private String upperCaseWord(String word) {
        String ret = word.substring(0, 1).toUpperCase() + word.substring(1);
        return ret;
    }

    public void add(FlowingFluid key, String name) {
        add("fluid." + Techniq.MOD_ID + "." + key.getRegistryName().toString().replace(Techniq.MOD_ID + ":", ""), name);
    }
}
