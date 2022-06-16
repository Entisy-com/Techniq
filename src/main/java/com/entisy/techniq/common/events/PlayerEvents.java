package com.entisy.techniq.common.events;

import com.entisy.techniq.Techniq;

import com.entisy.techniq.common.item.backpack.BackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Techniq.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public void onPlayerWorldJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) e.getEntity();
            for (ItemStack stack : player.inventory.items) {
                if (stack.getItem() instanceof BackpackItem) {
                    BackpackItem backpack = (BackpackItem) stack.getItem();
                    backpack.load(stack.getTag());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerWorldLeave(EntityLeaveWorldEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) e.getEntity();
            for (ItemStack stack : player.inventory.items) {
                if (stack.getItem() instanceof BackpackItem) {
                    BackpackItem backpack = (BackpackItem) stack.getItem();
                    backpack.save(stack.getTag());
                }
            }
        }
    }
}
