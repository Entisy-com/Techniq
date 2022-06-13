package com.entisy.techniq.common.item.upgrades;

import com.entisy.techniq.core.tab.TechniqTab;
import net.minecraft.item.Item;

public class UpgradeItem extends Item {

    private int stage;

    public UpgradeItem() {
        super(new Item.Properties().stacksTo(1).tab(TechniqTab.TECHNIQ_TAB));
    }

    public UpgradeType getUpgradeType() {
        return UpgradeType.NONE;
    }
}
