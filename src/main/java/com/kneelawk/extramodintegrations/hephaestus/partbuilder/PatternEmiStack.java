package com.kneelawk.extramodintegrations.hephaestus.partbuilder;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.config.EmiConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.texture.Sprite;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
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
  public void render(DrawContext draw, int x, int y, float delta, int flags) {
    if (pattern != null) {
      Sprite sprite = MinecraftClient.getInstance().getBakedModelManager().getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).getSprite(pattern.getTexture());
      RenderUtils.setup(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
      draw.drawSprite(x, y, 100, 16, 16, sprite);
    }
  }

  @Override
  public boolean isEmpty() {
    return pattern == null;
  }

  @Override
  public NbtCompound getNbt() {
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
  public Identifier getId() {
    return new Identifier(pattern.getNamespace(), pattern.getPath());
  }

  @Override
  public List<Text> getTooltipText() {
    List<Text> tooltip = new ArrayList<>();

    tooltip.add(pattern.getDisplayName());

    if (MinecraftClient.getInstance().options.advancedItemTooltips) {
      tooltip.add(Text.literal(pattern.toString()).formatted(Formatting.DARK_GRAY));
    }

    if (EmiConfig.appendModId) {
      String mod = EmiUtil.getModName(pattern.getNamespace());
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
    return pattern.getDisplayName();
  }
}
