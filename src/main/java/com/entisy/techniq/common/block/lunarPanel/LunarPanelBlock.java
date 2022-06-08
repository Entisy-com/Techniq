package com.entisy.techniq.common.block.lunarPanel;

import com.entisy.techniq.common.block.SimpleMachineBlock;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LunarPanelBlock extends SimpleMachineBlock {
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.LUNAR_PANEL_TILE_ENTITY.get().create();
    }
}