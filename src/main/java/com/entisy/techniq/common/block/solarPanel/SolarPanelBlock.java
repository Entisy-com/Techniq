package com.entisy.techniq.common.block.solarPanel;

import com.entisy.techniq.common.block.SimpleMachineBlock;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SolarPanelBlock extends SimpleMachineBlock {

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.SOLAR_PANEL_TILE_ENTITY.get().create();
    }
}