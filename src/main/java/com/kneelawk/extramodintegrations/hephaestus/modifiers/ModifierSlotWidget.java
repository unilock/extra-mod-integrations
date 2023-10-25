package com.kneelawk.extramodintegrations.hephaestus.modifiers;

import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.runtime.EmiDrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

public class ModifierSlotWidget extends SlotWidget {
    private final ModifierEntry entry;
    private final int width;
    private final int height;
    public ModifierSlotWidget(ModifierEntry entry, int x, int y, int width, int height) {
        super(new ModifierEmiStack(entry), x, y);
        this.entry = entry;
        this.width = width;
        this.height = height;
        this.bounds = new Bounds(x, y, width, height);
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void render(DrawContext draw, int mouseX, int mouseY, float delta) {
        if (entry != null) {
            Text name = entry.getModifier().getDisplayName(entry.getLevel());
            TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
            int xOffset = (width - fontRenderer.getWidth(name)) / 2;
            draw.drawTextWithShadow(fontRenderer, name, x + xOffset, y+1, -1);
        }

        if (EmiConfig.showHoverOverlay && bounds.contains(mouseX, mouseY)) {
            EmiRenderHelper.drawSlotHightlight(EmiDrawContext.wrap(draw), bounds.x(), bounds.y(), bounds.width(),
                    bounds.height());
        }
    }
}
