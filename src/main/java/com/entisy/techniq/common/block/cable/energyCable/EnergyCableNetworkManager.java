package com.entisy.techniq.common.block.cable.energyCable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.entisy.techniq.Techniq;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Techniq.MOD_ID)
public class EnergyCableNetworkManager {
	
	private static final Collection<LazyOptional<EnergyCableNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    private EnergyCableNetworkManager() {throw new IllegalAccessError("Utility class");}

    @Nullable
    public static EnergyCableNetwork get(IWorldReader world, BlockPos pos) {
        return getLazy(world, pos).orElse(null);
    }

    public static LazyOptional<EnergyCableNetwork> getLazy(IWorldReader world, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<EnergyCableNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    EnergyCableNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(world, pos)) {
                        return network;
                    }
                }
            }

            // Create new
            EnergyCableNetwork network = EnergyCableNetwork.buildNetwork(world, pos);
            LazyOptional<EnergyCableNetwork> lazy = LazyOptional.of(() -> network);
            NETWORK_LIST.add(lazy);
            return lazy;
        }
    }

    public static void invalidateNetwork(IWorldReader world, BlockPos pos) {
        Collection<LazyOptional<EnergyCableNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(world, pos))
                .collect(Collectors.toList());
        toRemove.forEach(EnergyCableNetworkManager::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<EnergyCableNetwork> network) {
        synchronized (NETWORK_LIST) {
            NETWORK_LIST.removeIf(n -> n.isPresent() && n.equals(network));
            network.ifPresent(EnergyCableNetwork::invalidate);
            network.invalidate();
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // Send energy from wire networks to connected blocks
        synchronized (NETWORK_LIST) {
            NETWORK_LIST.stream()
                    .filter(n -> n != null && n.isPresent())
                    .forEach(n -> n.ifPresent(EnergyCableNetwork::sendEnergy));
        }
    }
}
