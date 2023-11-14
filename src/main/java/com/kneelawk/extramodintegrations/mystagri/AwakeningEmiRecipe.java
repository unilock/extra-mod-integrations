package com.kneelawk.extramodintegrations.mystagri;

import com.alex.mysticalagriculture.api.crafting.IAwakeningRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AwakeningEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public AwakeningEmiRecipe(IAwakeningRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MystAgriIntegration.AWAKENING;
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
        return 152;
    }

    @Override
    public int getDisplayHeight() {
        return 89;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 28 + 4 + 5, 28 + 4 + 5);

        widgets.addSlot(inputs.get(1), 6 + 4 + 1, 6 + 4 + 1);
        widgets.addSlot(inputs.get(2), 32 + 4 + 1, 0 + 4 + 1);
        widgets.addSlot(inputs.get(3), 58 + 4 + 1, 6 + 4 + 1);
        widgets.addSlot(inputs.get(4), 64 + 4 + 1, 32 + 4 + 1);
        widgets.addSlot(inputs.get(5), 58 + 4 + 1, 58 + 4 + 1);
        widgets.addSlot(inputs.get(6), 32 + 4 + 1, 63 + 4 + 1);
        widgets.addSlot(inputs.get(7), 6 + 4 + 1, 58 + 4 + 1);
        widgets.addSlot(inputs.get(8), 0 + 4 + 1, 32 + 4 + 1);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 88 + 4, 32 + 4);

        widgets.addSlot(output, 118 + 4 + 5, 28 + 4 + 5).recipeContext(this);
    }
}
