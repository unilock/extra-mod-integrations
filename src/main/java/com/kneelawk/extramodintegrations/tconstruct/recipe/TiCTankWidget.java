package com.kneelawk.extramodintegrations.tconstruct.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.TankWidget;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;

public class TiCTankWidget extends TankWidget {

    protected List<Text> TiCTooltip = new ArrayList<>(); 

    public TiCTankWidget(EmiIngredient stack, int x, int y, int width, int height, long capacity) {
        super(stack, x, y, width, height, capacity);
    }

    public TiCTankWidget setTiCTooltip(List<Text> tooltips) {
        TiCTooltip.addAll(tooltips);
        Collections.reverse(TiCTooltip);
        return this;
    }

	@Override
	public List<TooltipComponent> getTooltip(int mouseX, int mouseY) {
		List<TooltipComponent> list = Lists.newArrayList();
		if (getStack().isEmpty()) {
			return list;
		}
        List<TooltipComponent> stackTooltip = getStack().getTooltip();
        if (stackTooltip.size() > 1) {
            TiCTooltip.forEach((text) -> stackTooltip.add(2, TooltipComponent.of(text.asOrderedText())));
        }
		list.addAll(stackTooltip);
		addSlotTooltip(list);
		return list;
	}
}
