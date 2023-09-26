package com.kneelawk.extramodintegrations.mystagri;

import com.alex.mysticalagriculture.init.ModBlocks;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.kneelawk.extramodintegrations.AbstractMystAgriIntegration;
import com.kneelawk.extramodintegrations.ExMIMod;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MystAgriIntegration extends AbstractMystAgriIntegration {
    public static final EmiRecipeCategory INFUSION = new EmiRecipeCategory(
            new Identifier("mysticalagriculture", "infusion"),
            EmiStack.of(ModBlocks.INFUSION_ALTAR)
    ) {
        @Override
        public Text getName() {
            return Text.translatable("rei.category.mysticalagriculture.infusion");
        }
    };

    public static final EmiRecipeCategory AWAKENING = new EmiRecipeCategory(
            new Identifier("mysticalagriculture", "awakening"),
            EmiStack.of(ModBlocks.AWAKENING_ALTAR)
    ) {
        @Override
        public Text getName() {
            return Text.translatable("rei.category.mysticalagriculture.awakening");
        }
    };

    @Override
    protected void registerImpl(EmiRegistry registry) {
        ExMIMod.LOGGER.info("[Extra Mod Integrations] Loading Mystical Agriculture: Refabricated Integration...");

        // categories
        registry.addCategory(INFUSION);
        registry.addCategory(AWAKENING);

        // workstations
        registry.addWorkstation(INFUSION, EmiStack.of(ModBlocks.INFUSION_ALTAR));
        registry.addWorkstation(AWAKENING, EmiStack.of(ModBlocks.AWAKENING_ALTAR));

        // recipes
        RecipeManager manager = registry.getRecipeManager();
        manager.listAllOfType(ModRecipeTypes.INFUSION)
                .stream()
                .map(InfusionEmiRecipe::new)
                .forEach(registry::addRecipe);
        manager.listAllOfType(ModRecipeTypes.AWAKENING)
                .stream()
                .map(AwakeningEmiRecipe::new)
                .forEach(registry::addRecipe);
    }
}
