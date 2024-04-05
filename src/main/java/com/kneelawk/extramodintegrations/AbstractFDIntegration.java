package com.kneelawk.extramodintegrations;

import com.kneelawk.extramodintegrations.util.ReflectionUtils;
import dev.emi.emi.api.EmiRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFDIntegration {
    @Nullable
    public static final AbstractFDIntegration INSTANCE;

    static {
        if (FabricLoader.getInstance().isModLoaded("farmersdelight") && !isNewFD()) {
            INSTANCE =
                    ReflectionUtils.newIntegrationInstance(
                            "com.kneelawk.extramodintegrations.farmersdelight.FDIntegration", "Farmer's Delight");
        } else {
            INSTANCE = null;
        }
    }

    private static boolean isNewFD() {
        try {
            Class.forName("vectorwing.farmersdelight.FarmersDelight");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    protected abstract void registerImpl(EmiRegistry registry);

    public static void register(EmiRegistry registry) {
        if (INSTANCE != null) {
            INSTANCE.registerImpl(registry);
        } else {
            ExMIMod.logSkipping("Farmer's Delight");
        }
    }
}
