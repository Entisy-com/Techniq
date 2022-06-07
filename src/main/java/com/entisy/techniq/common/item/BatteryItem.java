package com.entisy.techniq.common.item;

import com.entisy.techniq.common.item.energy.EnergyItem;

public class BatteryItem extends EnergyItem {

    public BatteryItem() {
        super(1000);
    }

    @Override
    public boolean isChargable() {
        return true;
    }
}
