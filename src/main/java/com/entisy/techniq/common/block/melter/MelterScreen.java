package com.entisy.techniq.common.block.melter;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.block.MachineTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MelterScreen extends ContainerScreen<MelterContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Techniq.MOD_ID,
            "textures/block/melter/gui.png");
    private final MelterContainer container;

    public MelterScreen(MelterContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.container = container;
        leftPos = 0;
        topPos = 0;
        width = 176;
        height = 165;
    }

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
        int pi = currentFluid != 0 ? currentFluid * 50 / MachineTileEntity.maxFluid : 0;
        blit(stack, getGuiLeft() + 126, getGuiTop() + (50 - pi) + 18, 188, (50 - pi), 12, 50);

        // draw progress bar/arrow
        int p = 0;
        if (currentEnergy != 0) p = getMenu().getProgressionScaled();
        blit(stack, getGuiLeft() + 39, getGuiTop() + (16 - p) + 35, 200, (16 - p), 4, 16);
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
        if (mouseX >= getGuiLeft() + 126 && mouseX < getGuiLeft() + 126 + 12) {
            if (mouseY >= getGuiTop() + 18 && mouseY < getGuiTop() + 18 + 50) {

                // rendering the amount of fluid stored e.g. 5000/25000
                int current = getMenu().currentFluid.get();
                this.renderTooltip(matrixStack, new StringTextComponent(current + "/" + MachineTileEntity.maxFluid), mouseX, mouseY);
            }
        }
        if (mouseX >= getGuiLeft() + 39 && mouseX < getGuiLeft() + 39 + 4) {
            if (mouseY >= getGuiTop() + 35 && mouseY < getGuiTop() + 35 + 16) {
                // rendering the refining progression e.g. 15%
                int current = 0;
                current = getMenu().tileEntity.currentSmeltTime * 100 / getMenu().tileEntity.getMaxWorkTime();
                this.renderTooltip(matrixStack, new StringTextComponent(current + "%"), mouseX, mouseY);
            }
        }
    }
}
