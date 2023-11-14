package com.kneelawk.extramodintegrations.mystagri;

import com.alex.mysticalagriculture.init.ModBlocks;
import com.alex.mysticalagriculture.init.ModItems;
import com.alex.mysticalagriculture.init.ModRecipeTypes;
import com.kneelawk.extramodintegrations.AbstractMystAgriIntegration;
import com.kneelawk.extramodintegrations.ExMIMod;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

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

    public static final EmiRecipeCategory REPROCESSOR = new EmiRecipeCategory(
            new Identifier("mysticalagriculture", "reprocessor"),
            EmiStack.of(ModBlocks.BASIC_REPROCESSOR)
    ) {
        @Override
        public Text getName() {
            return Text.translatable("rei.category.mysticalagriculture.reprocessor");
        }
    };

    public static final EmiRecipeCategory SOUL_EXTRACTION = new EmiRecipeCategory(
            new Identifier("mysticalagriculture", "soul_extractor"),
            EmiStack.of(ModBlocks.SOUL_EXTRACTOR)
    ) {
        @Override
        public Text getName() {
            return Text.translatable("rei.category.mysticalagriculture.soul_extractor");
        }
    };

    @Override
    protected void registerImpl(EmiRegistry registry) {
        ExMIMod.LOGGER.info("[Extra Mod Integrations] Loading Mystical Agriculture: Refabricated Integration...");

        // categories
        registry.addCategory(INFUSION);
        registry.addCategory(AWAKENING);
        registry.addCategory(REPROCESSOR);
        registry.addCategory(SOUL_EXTRACTION);

        // workstations
        //vanilla
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.INFERIUM_FURNACE));
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.PRUDENTIUM_FURNACE));
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.TERTIUM_FURNACE));
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.IMPERIUM_FURNACE));
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.SUPREMIUM_FURNACE));
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(ModBlocks.AWAKENED_SUPREMIUM_FURNACE));

        //mod
        registry.addWorkstation(INFUSION, EmiStack.of(ModBlocks.INFUSION_ALTAR));
        registry.addWorkstation(INFUSION, EmiStack.of(ModBlocks.INFUSION_PEDESTAL));

        registry.addWorkstation(AWAKENING, EmiStack.of(ModBlocks.AWAKENING_ALTAR));
        registry.addWorkstation(AWAKENING, EmiStack.of(ModBlocks.AWAKENING_PEDESTAL));
        registry.addWorkstation(AWAKENING, EmiStack.of(ModBlocks.ESSENCE_VESSEL));

        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.BASIC_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.BASIC_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.INFERIUM_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.PRUDENTIUM_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.TERTIUM_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.IMPERIUM_REPROCESSOR));
        registry.addWorkstation(REPROCESSOR, EmiStack.of(ModBlocks.SUPREMIUM_REPROCESSOR));

        registry.addWorkstation(SOUL_EXTRACTION, EmiStack.of(ModBlocks.SOUL_EXTRACTOR));

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
        manager.listAllOfType(ModRecipeTypes.REPROCESSOR)
                .stream()
                .map(ReprocessorEmiRecipe::new)
                .forEach(registry::addRecipe);
        manager.listAllOfType(ModRecipeTypes.SOUL_EXTRACTION)
                .stream()
                .map(SoulExtractionEmiRecipe::new)
                .forEach(registry::addRecipe);

        // info recipes
        registry.addRecipe(new EmiInfoRecipe(
                List.of(EmiStack.of(ModItems.COGNIZANT_DUST)),
                List.of(Text.translatable("jei.desc.mysticalagriculture.cognizant_dust")),
                new Identifier("mysticalagriculture", "cognizant_dust"))
        );
    }
}
