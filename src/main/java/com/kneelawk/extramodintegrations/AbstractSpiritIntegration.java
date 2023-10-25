package com.kneelawk.extramodintegrations;

import com.kneelawk.extramodintegrations.util.ReflectionUtils;
import dev.emi.emi.api.EmiRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSpiritIntegration {
    @Nullable
    public static final AbstractSpiritIntegration INSTANCE;

    static {
        if (FabricLoader.getInstance().isModLoaded("spirit")) {
            INSTANCE =
                    ReflectionUtils.newIntegrationInstance(
                            "com.kneelawk.extramodintegrations.spirit.SpiritIntegration",
                            "Spirit"
                    );
        } else {
            INSTANCE = null;
        }
    }

    protected abstract void registerImpl(EmiRegistry registry);

    public static void register(EmiRegistry registry) {
        if (INSTANCE != null) {
            INSTANCE.registerImpl(registry);
        }
    }
}
