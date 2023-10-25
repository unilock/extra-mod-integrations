package com.kneelawk.extramodintegrations.spirit;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.codexadrian.spirit.recipe.PedestalRecipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulTransmutationEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public SoulTransmutationEmiRecipe(PedestalRecipe recipe) {

    }

    @Override
    public EmiRecipeCategory getCategory() {
        return null;
    }

    @Override
    public @Nullable Identifier getId() {
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return null;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return null;
    }

    @Override
    public int getDisplayWidth() {
        return 0;
    }

    @Override
    public int getDisplayHeight() {
        return 0;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {

    }
}
