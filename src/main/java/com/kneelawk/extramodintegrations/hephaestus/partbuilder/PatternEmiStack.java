package com.kneelawk.extramodintegrations.hephaestus.partbuilder;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.config.EmiConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import slimeknights.tconstruct.library.client.RenderUtils;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;

import java.util.ArrayList;
import java.util.List;

public class PatternEmiStack extends EmiStack {
  private final Pattern pattern;

  public PatternEmiStack(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public EmiStack copy() {
    return new PatternEmiStack(pattern);
  }

  @Override
  public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
    if (pattern != null) {
      TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(pattern.getTexture());
      RenderUtils.setup(InventoryMenu.BLOCK_ATLAS);
      draw.blit(x, y, 100, 16, 16, sprite);
    }
  }

  @Override
  public boolean isEmpty() {
    return pattern == null;
  }

  @Override
  public CompoundTag getNbt() {
    return null;
  }

  @Override
  public Object getKey() {
    return pattern.hashCode();
  }

  public Pattern getPattern() {
    return pattern;
  }

  @Override
  public ResourceLocation getId() {
    return new ResourceLocation(pattern.getNamespace(), pattern.getPath());
  }

  @Override
  public List<Component> getTooltipText() {
    List<Component> tooltip = new ArrayList<>();

    tooltip.add(pattern.getDisplayName());

    if (Minecraft.getInstance().options.advancedItemTooltips) {
      tooltip.add(Component.literal(pattern.toString()).withStyle(ChatFormatting.DARK_GRAY));
    }

    if (EmiConfig.appendModId) {
      String mod = EmiUtil.getModName(pattern.getNamespace());
      tooltip.add(Component.literal(mod).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
    }

    return tooltip;
  }

  @Override
  public List<ClientTooltipComponent> getTooltip() {
    return getTooltipText().stream()
      .map(Component::getVisualOrderText)
      .map(ClientTooltipComponent::create)
      .toList();
  }

  @Override
  public Component getName() {
    return pattern.getDisplayName();
  }
}
