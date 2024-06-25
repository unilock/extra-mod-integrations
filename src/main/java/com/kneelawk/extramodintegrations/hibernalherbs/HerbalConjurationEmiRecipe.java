package com.kneelawk.extramodintegrations.hibernalherbs;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.dakotapride.hibernalHerbs.common.recipe.HerbalConjurationRecipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HerbalConjurationEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public HerbalConjurationEmiRecipe(HerbalConjurationRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getOutput(null));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return HibernalHerbsIntegration.HERBAL_CONJURATION;
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
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int startX = getDisplayWidth() / 2 - 64;
        int startY = getDisplayHeight() / 2 - 16;

        widgets.addTexture(EmiTexture.EMPTY_ARROW, startX + 78, startY + 8);

        widgets.addSlot(inputs.get(0), startX + 34, startY - 8);
        widgets.addSlot(inputs.get(1), startX + 56, startY - 4);
        widgets.addSlot(inputs.get(2), startX + 12, startY + 18);
        widgets.addSlot(inputs.get(3), startX + 34, startY + 22);
        widgets.addSlot(inputs.get(4), startX + 56, startY + 18);
        widgets.addSlot(inputs.get(5), startX + 12, startY - 4);

        widgets.addSlot(output, startX + 108, startY + 8).recipeContext(this);
    }
}
