package com.kneelawk.extramodintegrations.dimdoors;

import com.kneelawk.extramodintegrations.util.NamedEmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.dimdev.dimdoors.block.ModBlocks;

public class DimDoorsCategories {
    public static final EmiRecipeCategory TESSELATING = new NamedEmiRecipeCategory(
            new Identifier("dimdoors", "tesselating"),
            EmiStack.of(ModBlocks.TESSELATING_LOOM.get()),
            Text.translatable("category.dimdoors.tesselating")
    );
    public static final EmiRecipeCategory DECAYS_INTO = new NamedEmiRecipeCategory(
            new Identifier("dimdoors", "decays_into"),
            EmiStack.of(ModBlocks.UNRAVELED_FENCE.get()),
            Text.translatable("category.dimdoors.decays_into")
    );
}
