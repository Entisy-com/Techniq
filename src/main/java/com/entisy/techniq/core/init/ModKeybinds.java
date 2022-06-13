package com.entisy.techniq.core.init;

import com.entisy.techniq.Techniq;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class ModKeybinds {
    public static KeyBinding JETPACK_TOGGLE;

    public static void register() {
        JETPACK_TOGGLE = create("toggle_jetpack", KeyEvent.VK_G);

        ClientRegistry.registerKeyBinding(JETPACK_TOGGLE);
    }

    private static KeyBinding create(String name, int key) {
        return new KeyBinding("key." + Techniq.MOD_ID + "." + name, key, "key.category." + Techniq.MOD_ID);
    }
}
