package com.kneelawk.extramodintegrations.mystagri;

import com.alex.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulExtractionEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public SoulExtractionEmiRecipe(ISoulExtractionRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MystAgriIntegration.SOUL_EXTRACTION;
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
        return 90;
    }

    @Override
    public int getDisplayHeight() {
        return 34;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 0 + 4 + 1, 4 + 4 + 1);

        widgets.addFillingArrow(24 + 4, 4 + 4, 10000);

        widgets.addSlot(output, 56 + 4 + 5, 0 + 4 + 5).recipeContext(this);
    }
}
