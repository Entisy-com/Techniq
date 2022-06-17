package com.entisy.techniq.common.item.backpack;

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
public class BackpackScreen extends ContainerScreen<BackpackContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Techniq.MOD_ID,
            "textures/item/backpack/powered_gui.png");

    private final BackpackContainer container;

    public BackpackScreen(BackpackContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.container = container;
        leftPos = 0;
        topPos = 0;
        width = 199;
        height = 186;
        imageWidth = 199;
        imageHeight = 186;
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
        AtomicInteger current = new AtomicInteger();

//        container.getCapabilityFromTE().ifPresent(iEnergyStorage -> {
//            current.set(iEnergyStorage.getEnergyStored());
//        });

        int pixel = current.get() != 0 ? current.get() * 50 / MachineTileEntity.maxEnergy : 0;
        blit(stack, getGuiLeft() + 176, getGuiTop() + (50 - pixel) + 18, 199, (50 - pixel), 12, 50);

        // draw progress bar/arrow
//        if (getMenu().tileEntity.getRecipe() != null) blit(stack, leftPos + 80, topPos + 35, 0, 166, getMenu().getSmeltProgressionScaled(), 16);
    }

    @Override
    protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
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

        if (mouseX >= getGuiLeft() + 176 && mouseX < getGuiLeft() + 176 + 12) {
            if (mouseY >= getGuiTop() + 18 && mouseY < getGuiTop() + 18 + 50) {

                // rendering the amount of energy stored e.g. 5000/25000
                AtomicInteger current = new AtomicInteger();

//                container.getCapabilityFromTE().ifPresent(iEnergyStorage -> {
//                    current.set(iEnergyStorage.getEnergyStored());
//                });

                this.renderTooltip(matrixStack,
                        new StringTextComponent(current.get() + "/" + AlloySmelterTileEntity.maxEnergy), mouseX, mouseY);
            }
        }
    }
}
