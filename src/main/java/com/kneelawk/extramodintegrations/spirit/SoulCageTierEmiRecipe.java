package com.kneelawk.extramodintegrations.spirit;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.codexadrian.spirit.data.Tier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulCageTierEmiRecipe implements EmiRecipe {

    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;
    private final Tier recipe;

    public SoulCageTierEmiRecipe(Tier recipe) {
        this.id = recipe.getId();
        this.inputs = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getOutput());
        this.recipe = recipe;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return SpiritIntegration.SOUL_CAGE_TIER;
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
        return 178;
    }

    @Override
    public int getDisplayHeight() {
        return 111;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addDrawable(4, 4, getDisplayWidth() - 8, getDisplayHeight() - 8, (matrices, mouseX, mouseY, delta) -> {
            matrices.push();
            TextRenderer font = MinecraftClient.getInstance().textRenderer;
            font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.tier_prefix", Text.translatable(recipe.displayName())), 5, 5, 0x00a8ba);
            font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.required_souls", recipe.requiredSouls()), 5, 17, 0x373737);
            font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.spawn_delay", recipe.minSpawnDelay(), recipe.maxSpawnDelay()), 5, 29, 0x373737);
            font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.spawn_count", recipe.spawnCount()), 5, 41, 0x373737);
            font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.spawn_range", recipe.spawnRange()), 5, 53, 0x373737);
            if (recipe.nearbyRange() == -1) font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.player_nearby_not_required"), 5, 65, 0x373737);
            else font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.player_nearby", recipe.nearbyRange()), 5, 65, 0x373737);
            if (recipe.redstoneControlled()) font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.redstone_controlled_true"), 5, 77, 0x373737);
            else font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.redstone_controlled_false"), 5, 77, 0x373737);
            if (recipe.redstoneControlled())
                font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.ignored_spawn_conditions_true"), 5, 89, 0x373737);
            else font.draw(matrices, Text.translatable("spirit.jei.soul_cage_info.ignored_spawn_conditions_false"), 5, 89, 0x373737);
            matrices.pop();
        });
    }
}
