package com.kneelawk.extramodintegrations.hephaestus.entity;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.config.EmiConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityEmiStack extends EmiStack {

  public final EntityType<?> type;
  private final int size;
  private final EntityRenderer renderer;

  public EntityEmiStack(EntityType<?> type, int size) {
    this.type = type;
    this.size = size;
    this.renderer = new EntityRenderer(type, size);
  }

  @Override
  public EmiStack copy() {
    return new EntityEmiStack(type, size);
  }

  @Override
  public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
    renderer.render(draw, x, y, delta);
  }

  @Override
  public boolean isEmpty() {
    return type == null;
  }

  @Override
  public CompoundTag getNbt() {
    return null;
  }

  @Override
  public Object getKey() {
    return type;
  }

  @Override
  public ResourceLocation getId() {
    return BuiltInRegistries.ENTITY_TYPE.getKey(type);
  }

  @Override
  public List<Component> getTooltipText() {
    List<Component> tooltip = new ArrayList<>();

    tooltip.add(type.getDescription());

    if (Minecraft.getInstance().options.advancedItemTooltips) {
      tooltip.add((Component.literal(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(type)).toString())).withStyle(ChatFormatting.DARK_GRAY));
    }

    if (EmiConfig.appendModId) {
      String mod = EmiUtil.getModName(BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace());
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
    return type.getDescription();
  }
}
