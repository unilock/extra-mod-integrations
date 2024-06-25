package com.kneelawk.extramodintegrations.hibernalherbs;

import com.kneelawk.extramodintegrations.AbstractHibernalHerbsIntegration;
import com.kneelawk.extramodintegrations.ExMIMod;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.dakotapride.hibernalHerbs.common.init.BlockInit;
import net.dakotapride.hibernalHerbs.common.init.RecipeInit;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HibernalHerbsIntegration extends AbstractHibernalHerbsIntegration {
    public static final EmiRecipeCategory HERBAL_CONJURATION = new EmiRecipeCategory(
            new Identifier("hibernalherbs", "plugins/herbal_conjuration"),
            EmiStack.of(BlockInit.CONJURATION_ALTAR)
    ) {
        @Override
        public Text getName() {
            return Text.translatable("text.hibernalherbs.herbal_conjuration");
        }
    };

    @Override
    protected void registerImpl(EmiRegistry registry) {
        ExMIMod.logLoading("Hibernal Herbs");

        // categories
        registry.addCategory(HERBAL_CONJURATION);

        // workstations
        registry.addWorkstation(HERBAL_CONJURATION, EmiStack.of(BlockInit.CONJURATION_ALTAR));

        // recipes
        RecipeManager manager = registry.getRecipeManager();
        manager.listAllOfType(RecipeInit.HERBAL_CONJURATION_TYPE)
                .stream()
                .map(HerbalConjurationEmiRecipe::new)
                .forEach(registry::addRecipe);
    }
}
