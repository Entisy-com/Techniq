package com.entisy.techniq.common.block.refinery;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.entisy.techniq.common.block.alloySmelter.AlloySmelterTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class RefineryScreen extends ContainerScreen<RefineryContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Techniq.MOD_ID,
            "textures/block/refinery/gui.png");

    private final RefineryContainer container;

    public RefineryScreen(RefineryContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.container = container;
        leftPos = 0;
        topPos = 0;
        width = 176;
        height = 165;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.getTextureManager().bind(TEXTURE);

        int x = (this.width - this.getXSize()) / 2;
        int y = (this.height - this.getYSize()) / 2;
        this.blit(stack, x, y, 0, 0, getXSize(), getYSize());

        // draw energy bar
        int currentEnergy = getMenu().currentEnergy.get();
        int pixel = currentEnergy != 0 ? currentEnergy * 50 / MachineTileEntity.maxEnergy : 0;
        blit(stack, getGuiLeft() + 154, getGuiTop() + (50 - pixel) + 18, 176, (50 - pixel), 12, 50);

        // draw fluid bar
        int currentFluid = getMenu().currentFluid.get();
        int pi = 0;
        if (currentFluid != 0) pi = currentFluid * 50 / getMenu().tileEntity.getMaxFluidStored();
        blit(stack, getGuiLeft() + 10, getGuiTop() + (50 - pi) + 18, 188, (50 - pi), 12, 50);

        // draw progress bar/arrow
        int p = 0;
        if (currentEnergy != 0) p = getMenu().getProgressionScaled();
        blit(stack, getGuiLeft() + 73, getGuiTop() + (16 - p) + 35, 200, (16 - p), 4, 16);

        // draw bucket progress bar/arrow
        int pix = 0;
        if (getMenu().tileEntity.bucketProgress != 0) pix = getMenu().getBucketProgressionScaled();
        blit(stack, getGuiLeft() + 39, getGuiTop() + (pix) + 36, 204, (14 + pix), 8, pix - 14);
    }

    @Override
    protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
        font.draw(stack, getMenu().tileEntity.getDisplayName().getString().replace("[", "").replace("]", ""), 8.0f, 8.0f, 4210752); // hover text
        font.draw(stack, inventory.getDisplayName().getContents(), 8.0f, 69.0f, Color.BLUE.getBlue());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderTooltip(matrixStack, mouseX, mouseY);

        if (mouseX >= getGuiLeft() + 154 && mouseX < getGuiLeft() + 154 + 12) {
            if (mouseY >= getGuiTop() + 18 && mouseY < getGuiTop() + 18 + 50) {

                // rendering the amount of energy stored e.g. 5000/25000
                int current = getMenu().tileEntity.currentEnergy;
                this.renderTooltip(matrixStack,
                        new StringTextComponent(current + "/" + MachineTileEntity.maxEnergy), mouseX, mouseY);
            }
        }
        if (mouseX >= getGuiLeft() + 10 && mouseX < getGuiLeft() + 10 + 12) {
            if (mouseY >= getGuiTop() + 18 && mouseY < getGuiTop() + 18 + 50) {

                // rendering the amount of fluid stored e.g. 5000/25000
                int current = 0;
                int currentFluid = getMenu().currentFluid.get();
                if (getMenu().tileEntity.getMaxFluidStored() != 0) current = getMenu().tileEntity.currentFluid;
                if (currentFluid != 0) this.renderTooltip(matrixStack,
                        new StringTextComponent(current + "/" + getMenu().tileEntity.getMaxFluidStored()), mouseX, mouseY);
            }
        }
        if (mouseX >= getGuiLeft() + 73 && mouseX < getGuiLeft() + 73 + 4) {
            if (mouseY >= getGuiTop() + 35 && mouseY < getGuiTop() + 35 + 16) {
                // rendering the refining progression e.g. 15%
                int current = 0;
                current = getMenu().tileEntity.currentSmeltTime * 100 / getMenu().tileEntity.getMaxWorkTime();
                this.renderTooltip(matrixStack, new StringTextComponent(current + "%"), mouseX, mouseY);
            }
        }
    }
}
