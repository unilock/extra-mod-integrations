package com.kneelawk.extramodintegrations;

import com.kneelawk.extramodintegrations.util.ReflectionUtils;
import dev.emi.emi.api.EmiRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractHibernalHerbsIntegration {
    @Nullable
    public static final AbstractHibernalHerbsIntegration INSTANCE;

    static {
        if (FabricLoader.getInstance().isModLoaded("hibernalherbs")) {
            INSTANCE = ReflectionUtils.newIntegrationInstance(
                    "com.kneelawk.extramodintegrations.hibernalherbs.HibernalHerbsIntegration", "Hibernal Herbs");
        } else {
            INSTANCE = null;
        }
    }

    protected abstract void registerImpl(EmiRegistry registry);

    public static void register(EmiRegistry registry) {
        if (INSTANCE != null) {
            INSTANCE.registerImpl(registry);
        } else {
            ExMIMod.logSkipping("Hibernal Herbs");
        }
    }
}
