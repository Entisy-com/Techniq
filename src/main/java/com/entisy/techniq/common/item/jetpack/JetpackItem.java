package com.entisy.techniq.common.item.jetpack;

import com.entisy.techniq.common.item.energy.EnergyItem;
import com.entisy.techniq.core.init.ModKeybinds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;

import static com.entisy.techniq.common.item.jetpack.ToggleMode.*;

public class JetpackItem extends EnergyItem implements ITickable {

    public static ToggleMode MODE = OFF;

    public JetpackItem() {
        super(100_000);
    }

    @Override
    public boolean isChargable() {
        return true;
    }

    public ToggleMode toggleMode() {
        ToggleMode ret = null;
        switch (MODE) {
            case OFF:
                ret = HOVER;
                break;
            case HOVER:
                ret = NORMAL;
                break;
            case NORMAL:
                ret = OFF;
                break;
        }
        MODE = ret;
        System.out.println(MODE.getId());
        return ret;
    }

    public void save() {
        CompoundNBT nbt = getDefaultInstance().getTag();
        assert nbt != null;
        if (!nbt.contains("ToggleMode")) nbt.putString("ToggleMode", MODE.getId());
    }

    public void load() {
        CompoundNBT nbt = getDefaultInstance().getTag();
        assert nbt != null;
        if (nbt.contains("ToggleMode")) MODE = ToggleMode.getMode(nbt.getString("ToggleMode"));
    }

    private ToggleMode getMode() {
        return MODE;
    }

    private void setMode() {
        MODE = ToggleMode.getMode(Objects.requireNonNull(Objects.requireNonNull(getDefaultInstance().getTag()).get("ToggleMode")).getAsString());
    }

    @Override
    public void tick() {
        boolean dirty = false;

        if (ModKeybinds.JETPACK_TOGGLE.isDown()) {
            toggleMode();
            dirty = true;
        }

        if (dirty) {
            save();
        }
    }
}
