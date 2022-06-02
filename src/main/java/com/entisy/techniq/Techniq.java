package com.entisy.techniq;

import com.entisy.techniq.core.init.*;
import com.entisy.techniq.core.util.entisy.SimpleConfig;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("techniq")
@Mod.EventBusSubscriber(modid = Techniq.MOD_ID, bus = Bus.MOD)
public class Techniq {

    public static final String MOD_ID = "techniq";

    public Techniq() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(bus);
        ModContainerTypes.CONTAINER_TYPES.register(bus);
        ModRecipes.RECIPE_SERIALIZERS.register(bus);

        bus.addGenericListener(Fluid.class, ModFluids::registerFluids);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
