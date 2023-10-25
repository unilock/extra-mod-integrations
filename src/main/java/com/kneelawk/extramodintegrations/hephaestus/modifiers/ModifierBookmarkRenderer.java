package com.kneelawk.extramodintegrations.hephaestus.modifiers;

import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.gui.DrawContext;
import slimeknights.tconstruct.library.client.modifiers.ModifierIconManager;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

public class ModifierBookmarkRenderer implements EmiRenderable {
  private ModifierEntry entry;
  public ModifierBookmarkRenderer(ModifierEntry entry) {
    this.entry = entry;
  }

  @Override
  public void render(DrawContext draw, int x, int y, float delta) {
    ModifierIconManager.renderIcon(draw, entry.getModifier(), x, y, 100, 16);
  }
}
