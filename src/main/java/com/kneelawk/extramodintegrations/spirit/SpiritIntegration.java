package com.kneelawk.extramodintegrations.spirit;

import com.alex.mysticalagriculture.init.ModBlocks;
import com.kneelawk.extramodintegrations.AbstractSpiritIntegration;
import com.kneelawk.extramodintegrations.ExMIMod;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import me.codexadrian.spirit.registry.SpiritBlocks;
import me.codexadrian.spirit.registry.SpiritItems;
import me.codexadrian.spirit.registry.SpiritMisc;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpiritIntegration extends AbstractSpiritIntegration {
    public static final EmiRecipeCategory SOUL_CAGE_TIER = new EmiRecipeCategory(
            new Identifier("spirit", "soul_cage_tier"),
            EmiStack.of(SpiritBlocks.SOUL_CAGE.get())
    ) {
        @Override
        public Text getName() {
            return Text.translatable("spirit.jei.soul_cage_tier.title");
        }
    };
    public static final EmiRecipeCategory SOUL_ENGULFING = new EmiRecipeCategory(
            new Identifier("spirit", "soul_engulfing"),
            EmiStack.of(SpiritItems.SOUL_CRYSTAL.get())
    ) {
        @Override
        public Text getName() {
            return Text.translatable("spirit.jei.soul_engulfing.title");
        }
    };
    // aka "PedestalRecipeCategory"
    public static final EmiRecipeCategory SOUL_TRANSMUTATION = new EmiRecipeCategory(
            new Identifier("spirit", "soul_transmutation"),
            EmiStack.of(SpiritBlocks.SOUL_PEDESTAL.get())
    ) {
        @Override
        public Text getName() {
            return Text.translatable("spirit.jei.soul_transmutation.title");
        }
    };

    @Override
    protected void registerImpl(EmiRegistry registry) {
        ExMIMod.LOGGER.info("[Extra Mod Integrations] Loading Spirit Integration...");

        // categories
        registry.addCategory(SOUL_CAGE_TIER);
        registry.addCategory(SOUL_ENGULFING);
        registry.addCategory(SOUL_TRANSMUTATION);

        // workstations
        registry.addWorkstation(SOUL_CAGE_TIER, EmiStack.of(SpiritBlocks.SOUL_CAGE.get()));
        registry.addWorkstation(SOUL_ENGULFING, EmiStack.of(Items.FLINT_AND_STEEL));
        registry.addWorkstation(SOUL_ENGULFING, EmiStack.of(Blocks.SOUL_SAND));
        registry.addWorkstation(SOUL_TRANSMUTATION, EmiStack.of(SpiritBlocks.SOUL_PEDESTAL.get()));
        registry.addWorkstation(SOUL_TRANSMUTATION, EmiStack.of(SpiritBlocks.PEDESTAL.get()));

        // recipes
        RecipeManager manager = registry.getRecipeManager();
        manager.listAllOfType(SpiritMisc.TIER_RECIPE.get())
                .stream()
                .map(SoulCageTierEmiRecipe::new)
                .forEach(registry::addRecipe);
        manager.listAllOfType(SpiritMisc.SOUL_ENGULFING_RECIPE.get())
                .stream()
                .map(SoulEngulfingEmiRecipe::new)
                .forEach(registry::addRecipe);
        manager.listAllOfType(SpiritMisc.SOUL_TRANSMUTATION_RECIPE.get())
                .stream()
                .map(SoulTransmutationEmiRecipe::new)
                .forEach(registry::addRecipe);
    }
}
