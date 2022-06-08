package com.entisy.techniq.common.block.solarPanel;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.PanelBlock;
import com.entisy.techniq.core.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class SolarPanelBlock extends PanelBlock {
    final VoxelShape ALL = Stream.of(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 2, 0, 1, 3, 16),
            Block.box(15, 2, 0, 16, 3, 16),
            Block.box(1, 2, 0, 15, 3, 1),
            Block.box(1, 2, 15, 15, 3, 16),
            Block.box(1, 2, 12, 15, 3, 13),
            Block.box(1, 2, 9, 15, 3, 10),
            Block.box(1, 2, 6, 15, 3, 7),
            Block.box(1, 2, 3, 15, 3, 4),
            Block.box(3, 2, 1, 4, 3, 3),
            Block.box(6, 2, 1, 7, 3, 3),
            Block.box(9, 2, 1, 10, 3, 3),
            Block.box(12, 2, 1, 13, 3, 3),
            Block.box(3, 2, 4, 4, 3, 6),
            Block.box(12, 2, 7, 13, 3, 9),
            Block.box(9, 2, 7, 10, 3, 9),
            Block.box(6, 2, 7, 7, 3, 9),
            Block.box(3, 2, 7, 4, 3, 9),
            Block.box(3, 2, 10, 4, 3, 12),
            Block.box(6, 2, 10, 7, 3, 12),
            Block.box(9, 2, 10, 10, 3, 12),
            Block.box(12, 2, 10, 13, 3, 12),
            Block.box(3, 2, 13, 4, 3, 15),
            Block.box(6, 2, 13, 7, 3, 15),
            Block.box(9, 2, 13, 10, 3, 15),
            Block.box(12, 2, 13, 13, 3, 15),
            Block.box(6, 2, 4, 7, 3, 6),
            Block.box(9, 2, 4, 10, 3, 6),
            Block.box(12, 2, 4, 13, 3, 6)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.SOLAR_PANEL_TILE_ENTITY.get().create();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> lore, ITooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".solar_panel"));
        } else {
            lore.add(new TranslationTextComponent("tooltip." + Techniq.MOD_ID + ".hidden"));
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return ALL;
    }
}