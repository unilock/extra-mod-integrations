package com.kneelawk.extramodintegrations.spirit;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.codexadrian.spirit.compat.jei.multiblock.SoulEngulfingRecipeWrapper;
import me.codexadrian.spirit.recipe.SoulEngulfingRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulEngulfingEmiRecipe implements EmiRecipe {

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public static final Identifier GUI_BACKGROUND = new Identifier("spirit", "textures/gui/soul_engulfing.png");
    private final SoulEngulfingRecipeWrapper wrapper;
    private static final double OFFSET = Math.sqrt(512) * .5;
    private int scale = 10;
    public long lastTime;
    private final BlockEntityRenderDispatcher dispatcher;
    private int xOffset = 0;
    private int yOffset = 0;

    public SoulEngulfingEmiRecipe(SoulEngulfingRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getOutput());
        this.wrapper = new SoulEngulfingRecipeWrapper(recipe);

        lastTime = System.currentTimeMillis();
        dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return SpiritIntegration.SOUL_ENGULFING;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 158;
    }

    @Override
    public int getDisplayHeight() {
        return 108;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        scale = 10 - (wrapper.getMultiblock().pattern().get(0).size() * 2 - 6);
        var startX = getDisplayWidth() / 2 - 150 / 2;
        var startY = getDisplayHeight() / 2 - 100 / 2;
        widgets.addTexture(GUI_BACKGROUND, startX, startY, 150, 100, 0, 0);
        widgets.addSlot(getInputs().get(0), startX + 2, startY + 2);
        widgets.addSlot(getOutputs().get(0), startX + 133, startY + 83);
        widgets.add(new DelegateWidget(Widgets.noOp()) {
            @Override
            public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
                stack.pushPose();
                stack.translate(startX, startY, 0);
                draw(display.getWrapper(), stack, mouseX, mouseY);
                stack.popPose();
                List<Component> strings = getTooltipStrings(display.getWrapper(), mouseX - startX, mouseY - startY);
                if (!strings.isEmpty()) {
                    Tooltip.create(new Point(mouseX, mouseY), strings).queue();
                }
            }
            public Rectangle getBounds() {
                return new Rectangle(2, 26, 103, 74);
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                return handleInput(display.getWrapper(), keyCode, scanCode);
            }

            @Override
            public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
                if(button == 1) {
                    xOffset += deltaX * .5;
                    yOffset += deltaY * .5;
                }
                return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
            }
        });
        widgets.add(Widgets.wrapVanillaWidget(new PlainTextButton(startX + 140, startY + 2, 20, 20, Component.literal("♺"), button -> {
            xOffset = 0;
            yOffset = 0;
            scale = 10 - (display.getWrapper().getMultiblock().pattern().get(0).size() * 2 - 6);
        }, Minecraft.getInstance().font)));
    }
}
