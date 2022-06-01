package com.entisy.techniq.common.block.waterSource;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class WaterSourceBlock extends Block {
    public WaterSourceBlock() {
        super(AbstractBlock.Properties.of(Material.STONE));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if (player.getItemInHand(hand).sameItem(Items.BUCKET.getDefaultInstance())) {
            if (player.getItemInHand(hand).getCount() == 1) {
                player.setItemInHand(hand, Items.WATER_BUCKET.getDefaultInstance());
            }
        }
        return ActionResultType.CONSUME;
    }
}
