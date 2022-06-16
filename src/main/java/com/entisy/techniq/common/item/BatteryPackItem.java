package com.entisy.techniq.common.item;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.item.energy.EnergyItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.List;

public class BatteryPackItem extends EnergyItem {

    private static int currentEnergy;
    private static int maxEnergy;

    public BatteryPackItem() {
        super(6000);
    }

    @Override
    public boolean isChargable() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> lore, ITooltipFlag ttflag) {
        if (Screen.hasShiftDown()) {
            getDefaultInstance().getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
                currentEnergy = e.getEnergyStored();
                maxEnergy = e.getMaxEnergyStored();
            });
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".battery_pack"));
            lore.add(new StringTextComponent(currentEnergy + "/" + maxEnergy + "TE"));
        } else {
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".hidden"));
        }
    }
}
