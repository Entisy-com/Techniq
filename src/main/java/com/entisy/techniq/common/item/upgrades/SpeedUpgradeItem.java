
package com.entisy.techniq.common.item.upgrades;

public class SpeedUpgradeItem extends UpgradeItem {

    public UpgradeType getUpgradeType() {
        return UpgradeType.SPEED;
    }

    public static int getUpgradeStage() {
        return 2;
    }
}
