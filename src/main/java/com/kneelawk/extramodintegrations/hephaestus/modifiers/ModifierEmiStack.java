package com.kneelawk.extramodintegrations.hephaestus.modifiers;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.config.EmiConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

import java.util.ArrayList;
import java.util.List;

public class ModifierEmiStack extends EmiStack {
  private final ModifierBookmarkRenderer renderer;
  private final ModifierEntry entry;
  private final Component name;
  public ModifierEmiStack(ModifierEntry entry) {
    this.entry = entry;
    this.renderer = new ModifierBookmarkRenderer(entry);
    this.name = Component.translatable("jei.tconstruct.modifier_ingredient",
      Component.translatable(entry.getModifier().getTranslationKey()))
      .withStyle(style -> style.withColor(entry.getModifier().getTextColor()));
  }

  @Override
  public EmiStack copy() {
    return new ModifierEmiStack(entry);
  }

  @Override
  public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
    renderer.render(draw, x, y, delta);
  }

  @Override
  public boolean isEmpty() {
    return entry == null;
  }

  @Override
  public CompoundTag getNbt() {
    return null;
  }

  @Override
  public Object getKey() {
    return entry.getModifier();
  }

  @Override
  public ResourceLocation getId() {
    return entry.getId();
  }

  @Override
  public List<Component> getTooltipText() {
    List<Component> tooltip = new ArrayList<>();

    tooltip.add(name);
    tooltip.addAll(entry.getModifier().getDescriptionList());

    if (Minecraft.getInstance().options.advancedItemTooltips) {
      tooltip.add(Component.literal(entry.getId().toString()).withStyle(ChatFormatting.DARK_GRAY));
    }

    if (EmiConfig.appendModId) {
      String mod = EmiUtil.getModName(entry.getId().getNamespace());
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
    return name;
  }
}
