package com.wiblewobble.chocolatehaven.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wiblewobble.chocolatehaven.ChocolateHaven;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CocoaFermenterScreen extends AbstractContainerScreen<CocoaFermenterMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ChocolateHaven.MODID, "textures/gui/cocoa_fermenter_gui.png");

    public CocoaFermenterScreen(CocoaFermenterMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressBean(graphics, x, y);
    }

    private void renderProgressBean(GuiGraphics graphics, int x, int y) {
        if (menu.isCrafting()) {
            graphics.blit(TEXTURE, x + 80, y + 33, 177, 15, 22, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
