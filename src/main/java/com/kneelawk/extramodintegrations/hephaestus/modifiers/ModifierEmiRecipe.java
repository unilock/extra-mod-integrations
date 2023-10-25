package com.kneelawk.extramodintegrations.hephaestus.modifiers;

import com.kneelawk.extramodintegrations.hephaestus.HephaestusIntegration;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.model.NBTKeyModel;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.GuiUtil;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModifierEmiRecipe implements EmiRecipe {
  protected static final ResourceLocation BACKGROUND_LOC = TConstruct.getResource("textures/gui/jei/tinker_station.png");
  private final List<EmiIngredient> input = new ArrayList<>();
  private final List<EmiIngredient> tools;
  private final ModifierEntry entry;
  private final ResourceLocation id;
  private final Map<SlotType,TextureAtlasSprite> slotTypeSprites = new HashMap<>();
  private final boolean hasRequirements;
  private final boolean isIncremental;
  private final int maxLevel;
  private final SlotType.SlotCount slots;
  private final String requirementsError;

  public ModifierEmiRecipe(IDisplayModifierRecipe recipe) {
    // TODO: this is a bad id. get a better id from somewhere or make one up
//    id = recipe.getModifier().getId();
    if (recipe instanceof Recipe<?> r) {
      id = r.getId();
    } else id = null;

    tools = Stream.of(recipe.getToolWithoutModifier(), recipe.getToolWithModifier()).map(t -> t.stream().map(EmiStack::of).toList()).map(EmiIngredient::of).toList();
    IntStream.range(0, 5).forEachOrdered(i -> input.add(EmiIngredient.of(recipe.getDisplayItems(i).stream().map(EmiStack::of).toList())));
    entry = recipe.getDisplayResult();
    requirementsError = recipe.getRequirementsError();
    hasRequirements = recipe.hasRequirements();
    isIncremental = recipe.isIncremental();
    maxLevel = recipe.getMaxLevel();
    slots = recipe.getSlots();
  }

  @Override
  public EmiRecipeCategory getCategory() {
    return HephaestusIntegration.MODIFIER_CATEGORY;
  }

  @Override
  public @Nullable ResourceLocation getId() {
    return id;
  }

  @Override
  public List<EmiIngredient> getInputs() {
    return input;
  }

  @Override
  public List<EmiStack> getOutputs() {
    return List.of(new ModifierEmiStack(entry));
  }

  @Override
  public List<EmiIngredient> getCatalysts() {
    return tools;
  }

  @Override
  public int getDisplayWidth() {
    return 128;
  }

  @Override
  public int getDisplayHeight() {
    return 77;
  }

  @Override
  public void addWidgets(WidgetHolder widgets) {
    widgets.addTexture(BACKGROUND_LOC, 0, 0, 128, 77, 0, 0);

    // slot outlines
    drawOutline(widgets, 0,  2, 32);
    drawOutline(widgets, 1, 24, 14);
    drawOutline(widgets, 2, 46, 32);
    drawOutline(widgets, 3, 42, 57);
    drawOutline(widgets, 4,  6, 57);

    // info icons
    if (hasRequirements) {
      widgets.addTexture(BACKGROUND_LOC, 66, 58, 16, 16, 128, 17)
        .tooltip((checkX, checkY) -> {
          if (GuiUtil.isHovered(checkX, checkY, 66, 58, 16, 16))
            return List.of(ClientTooltipComponent.create(Component.translatable(requirementsError).getVisualOrderText()));
          return null;
        });
    }
    if (isIncremental) {
      widgets.addTexture(BACKGROUND_LOC, 83, 59, 16, 16, 128, 33)
        .tooltip((checkX, checkY) -> {
          if (GuiUtil.isHovered(checkX, checkY, 83, 59, 16, 16))
            return List.of(ClientTooltipComponent.create(Component.translatable("jei.tconstruct.modifiers.incremental").getVisualOrderText()));
          return null;
        });
    }

    // max count
    if (maxLevel > 0) {
      widgets.addText(Component.translatable("jei.tconstruct.modifiers.max").append(String.valueOf(maxLevel)),
        66, 16, Color.GRAY.getRGB(), false);
    }


    // slot cost
    widgets.add(new SlotCountWidget(110, 58, slots));
    if (slots != null) {
      Component text = Component.literal(Integer.toString(slots.getCount()));
      widgets.addText(text, 111, 63, Color.GRAY.getRGB(), false).horizontalAlign(TextWidget.Alignment.END);
    }

    // inputs
    widgets.addSlot(input.get(0),  2, 32).drawBack(false);
    widgets.addSlot(input.get(1), 24, 14).drawBack(false);
    widgets.addSlot(input.get(2), 46, 32).drawBack(false);
    widgets.addSlot(input.get(3), 42, 57).drawBack(false);
    widgets.addSlot(input.get(4),  6, 57).drawBack(false);
    // modifiers
    widgets.add(new ModifierSlotWidget(entry, 2, 2, 124, 10))
            .recipeContext(this);
    // tool
    widgets.addSlot(tools.get(0),  24, 37).drawBack(false);
    widgets.addSlot(tools.get(1), 100, 29).drawBack(false).recipeContext(this);
  }

  private void drawOutline(WidgetHolder widgets, int slot, int x, int y) {
    if (input.get(slot).isEmpty()) {
      widgets.addTexture(BACKGROUND_LOC, x + 1, y + 1, 16, 16, 128 + slot * 16, 0);
    }
  }

  private class SlotCountWidget extends Widget {
    private final int x, y;
    private final Bounds bounds;
    private final SlotType.SlotCount slots;

    public SlotCountWidget(int x, int y, @Nullable SlotType.SlotCount slots) {
      this.x = x;
      this.y = y;
      this.bounds = new Bounds(x, y, 16, 16);
      this.slots = slots;
    }

    @Override
    public Bounds getBounds() {
      return bounds;
    }

    @Override
    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
      Component tooltipComponent = null;
      if (slots != null) {
        int count = slots.getCount();
        if (count == 1) {
          tooltipComponent = Component.translatable("jei.tconstruct.modifiers.slot", slots.getType().getDisplayName());
        } else if (count > 1) {
          tooltipComponent = Component.translatable("jei.tconstruct.modifiers.slots", slots, slots.getType().getDisplayName());
        }
      } else {
        tooltipComponent = Component.translatable("jei.tconstruct.modifiers.free");
      }
      if (tooltipComponent != null) {
        return List.of(ClientTooltipComponent.create(tooltipComponent.getVisualOrderText()));
      }
      return List.of();
    }

    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
      Minecraft minecraft = Minecraft.getInstance();
      TextureAtlasSprite sprite;
      SlotType slotType = slots == null ? null : slots.getType();
      if (slotTypeSprites.containsKey(slotType)) {
        sprite = slotTypeSprites.get(slotType);
      } else {
        ModelManager modelManager = minecraft.getModelManager();
        // gets the model for the item, its a sepcial one that gives us texture info
        BakedModel model = minecraft.getItemRenderer().getItemModelShaper().getItemModel(TinkerModifiers.creativeSlotItem.get());
        if (model != null && model.getOverrides() instanceof NBTKeyModel.Overrides) {
          Material material = ((NBTKeyModel.Overrides) model.getOverrides()).getTexture(slotType == null ? "slotless" : slotType.getName());
          sprite = modelManager.getAtlas(material.atlasLocation()).getSprite(material.texture());
        } else {
          // failed to use the model, use missing texture
          sprite = modelManager.getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(MissingTextureAtlasSprite.getLocation());
        }
        slotTypeSprites.put(slotType, sprite);
      }
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

      draw.blit(x, y, 0, 16, 16, sprite);
    }
  }

}
