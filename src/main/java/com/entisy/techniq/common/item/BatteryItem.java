package com.entisy.techniq.common.item;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.item.energy.EnergyItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BatteryItem extends EnergyItem {

    public BatteryItem() {
        super(1000);
    }

    @Override
    public boolean isChargable() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> lore, ITooltipFlag ttflag) {
        if (Screen.hasShiftDown()) {
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".battery"));
        } else {
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".hidden"));
        }
    }
}
