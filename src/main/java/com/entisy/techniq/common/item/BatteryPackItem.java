package com.entisy.techniq.common.item;

import com.entisy.techniq.common.item.energy.EnergyItem;

public class BatteryPackItem extends EnergyItem {

    public BatteryPackItem() {
        super(6000);
    }

    @Override
    public boolean isChargable() {
        return true;
    }
}
