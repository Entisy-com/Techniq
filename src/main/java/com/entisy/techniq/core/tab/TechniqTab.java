package com.entisy.techniq.core.tab;

import com.entisy.techniq.core.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TechniqTab extends ItemGroup {

    public static final TechniqTab TECHNIQ_TAB = new TechniqTab(ItemGroup.getGroupCountSafe(), "techniq_tab");

    public TechniqTab(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.COPPER_INGOT.get());
    }
}
