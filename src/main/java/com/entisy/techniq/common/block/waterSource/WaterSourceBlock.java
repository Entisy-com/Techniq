package com.entisy.techniq.common.block.waterSource;

import com.entisy.techniq.common.block.SimpleBlock;
import com.entisy.techniq.common.block.refinery.RefineryTileEntity;
import com.entisy.techniq.core.util.SimpleList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class WaterSourceBlock extends SimpleBlock {
    public WaterSourceBlock() {
        super(AbstractBlock.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        ItemStack stackInHand = player.getItemInHand(hand);
        if (stackInHand.sameItem(Items.BUCKET.getDefaultInstance())) {
                if (stackInHand.getCount() == 1) {
                    player.setItemInHand(hand, Items.WATER_BUCKET.getDefaultInstance());
                } else {
                    if (player.inventory.getFreeSlot() > 0) {
                        ItemStack newItemInHand = stackInHand.copy();
                        newItemInHand.setCount(stackInHand.getCount() - 1);
                        player.setItemInHand(hand, newItemInHand);
                        player.inventory.add(player.inventory.getFreeSlot(), Items.WATER_BUCKET.getDefaultInstance());
                    }
                }
        }
        return ActionResultType.CONSUME;
    }
}
