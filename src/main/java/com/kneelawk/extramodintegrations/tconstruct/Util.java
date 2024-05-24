package com.kneelawk.extramodintegrations.tconstruct;

import dev.emi.emi.api.stack.EmiStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.text.Text;
import slimeknights.mantle.fluid.tooltip.FluidTooltipHandler;

import java.util.List;
import java.util.function.Supplier;

public class Util {
    public static EmiStack convertFluid(FluidStack fluidStack) {
        return EmiStack.of(fluidStack.getFluid(), fluidStack.getTag(), fluidStack.getAmount());
    }

    public static Supplier<List<Text>> getFluidTiCTooltip(FluidStack fluidStack) {
        return () -> FluidTooltipHandler.getFluidTooltip(fluidStack);
    }
}
