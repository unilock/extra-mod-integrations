package com.kneelawk.extramodintegrations.tconstruct.recipe;

import com.google.common.collect.Lists;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.TankWidget;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Supplier;

public class TiCTankWidget extends TankWidget {

    protected Supplier<List<Text>> tiCTooltipSupplier;

    public TiCTankWidget(EmiIngredient stack, int x, int y, int width, int height, long capacity) {
        super(stack, x, y, width, height, capacity);
    }

    public TiCTankWidget setTiCTooltipSupplier(Supplier<List<Text>> tiCTooltipSupplier) {
        this.tiCTooltipSupplier = tiCTooltipSupplier;
        return this;
    }

    @Override
    public List<TooltipComponent> getTooltip(int mouseX, int mouseY) {
        List<TooltipComponent> list = Lists.newArrayList();
        if (getStack().isEmpty()) {
            return list;
        }

        list.addAll(tiCTooltipSupplier == null ? getStack().getTooltip() : tiCTooltipSupplier.get().stream().map(Text::asOrderedText).map(TooltipComponent::of).toList());

        addSlotTooltip(list);
        return list;
    }
}
