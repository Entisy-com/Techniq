package com.entisy.techniq.common.item.upgrades;

public class CapacityUpgradeItem extends UpgradeItem {

    @Override
    public UpgradeType getUpgradeType() {
        return UpgradeType.CAPACITY;
    }

    public int getUpgradeStage() {
        return 2;
    }
}
