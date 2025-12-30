package net.kenji.woh.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class WohColliderPreset implements PreparableReloadListener {
    private static final BiMap<ResourceLocation, Collider> PRESETS = HashBiMap.create();
    public static final Collider TSUME_CLAWS = registerCollider(new ResourceLocation("epicfight", "tsume_claws"), new MultiOBBCollider(3, 0.4, 0.75, 0.4, (double)0.0F, (double)0.0F, -0.1));

    public static Collider registerCollider(ResourceLocation rl, Collider collider) {
        if (PRESETS.containsKey(rl)) {
            throw new IllegalStateException("Collider named " + rl + " already registered.");
        } else {
            PRESETS.put(rl, collider);
            return collider;
        }
    }

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        CompletableFuture var10000 = CompletableFuture.runAsync(() -> {
        }, gameExecutor);
        Objects.requireNonNull(stage);
        return var10000.thenCompose(stage::wait);
    }
}
