package com.kneelawk.extramodintegrations.hephaestus.modifiers;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.config.EmiConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

import java.util.ArrayList;
import java.util.List;

public class ModifierEmiStack extends EmiStack {
  private final ModifierBookmarkRenderer renderer;
  private final ModifierEntry entry;
  private final Text name;
  public ModifierEmiStack(ModifierEntry entry) {
    this.entry = entry;
    this.renderer = new ModifierBookmarkRenderer(entry);
    this.name = Text.translatable("jei.tconstruct.modifier_ingredient",
      Text.translatable(entry.getModifier().getTranslationKey()))
      .styled(style -> style.withColor(entry.getModifier().getTextColor()));
  }

  @Override
  public EmiStack copy() {
    return new ModifierEmiStack(entry);
  }

  @Override
  public void render(DrawContext draw, int x, int y, float delta, int flags) {
    renderer.render(draw, x, y, delta);
  }

  @Override
  public boolean isEmpty() {
    return entry == null;
  }

  @Override
  public NbtCompound getNbt() {
    return null;
  }

  @Override
  public Object getKey() {
    return entry.getModifier();
  }

  @Override
  public Identifier getId() {
    return entry.getId();
  }

  @Override
  public List<Text> getTooltipText() {
    List<Text> tooltip = new ArrayList<>();

    tooltip.add(name);
    tooltip.addAll(entry.getModifier().getDescriptionList());

    if (MinecraftClient.getInstance().options.advancedItemTooltips) {
      tooltip.add(Text.literal(entry.getId().toString()).formatted(Formatting.DARK_GRAY));
    }

    if (EmiConfig.appendModId) {
      String mod = EmiUtil.getModName(entry.getId().getNamespace());
      tooltip.add(Text.literal(mod).formatted(Formatting.BLUE, Formatting.ITALIC));
    }

    return tooltip;
  }

  @Override
  public List<TooltipComponent> getTooltip() {
    return getTooltipText().stream()
      .map(Text::asOrderedText)
      .map(TooltipComponent::of)
      .toList();
  }

  @Override
  public Text getName() {
    return name;
  }
}
