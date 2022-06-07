package com.entisy.techniq.common.world.event;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.item.backpack.BackpackItem;
import com.entisy.techniq.core.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Techniq.MOD_ID)
public class WorldEvents {

    @SubscribeEvent
    public void onWorldPlayerJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) e.getEntity();
            if (p.inventory.contains(ModItems.BACKPACK_ITEM.get().getDefaultInstance())) {
                int slot = p.inventory.getSlotWithRemainingSpace(ModItems.BACKPACK_ITEM.get().getDefaultInstance());
                ((BackpackItem) p.inventory.getItem(slot).getItem()).load(p.inventory.getItem(slot).getTag());
            }
        }
    }

    @SubscribeEvent
    public void onWorldPlayerLeave(EntityLeaveWorldEvent e) {
        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) e.getEntity();
            if (p.inventory.contains(ModItems.BACKPACK_ITEM.get().getDefaultInstance())) {
                int slot = p.inventory.getSlotWithRemainingSpace(ModItems.BACKPACK_ITEM.get().getDefaultInstance());
                ((BackpackItem) p.inventory.getItem(slot).getItem()).save(p.inventory.getItem(slot).getTag());
            }
        }
    }

}
