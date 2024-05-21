package com.kneelawk.extramodintegrations.tconstruct;

import java.util.ArrayList;
import java.util.List;
import dev.emi.emi.api.stack.EmiStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import slimeknights.mantle.fluid.tooltip.FluidTooltipHandler;

public class Util {
    public static EmiStack convertFluid(FluidStack fluidStack) {
        return EmiStack.of(fluidStack.getFluid(), fluidStack.getTag(), fluidStack.getAmount());
    }

    public static List<Text> getFluidTiCTooltip(FluidStack fluidStack) {
        List<Text> tooltip = new ArrayList<Text>();
        if (FluidTooltipHandler.appendMaterialNoShift(fluidStack.getFluid(), fluidStack.getAmount(), tooltip)) {
            return tooltip.stream().map((Text text) -> {
                return (Text) Text.literal(text.getString()).formatted(Formatting.BLUE);
            }).toList();
        }
        return new ArrayList<Text>();
    }
}
