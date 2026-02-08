package net.kenji.woh.compat.combat_hotbar;

import net.kenji.epic_fight_combat_hotbar.capability.CombatHotbarProvider;
import net.kenji.woh.client.layers.HolsteredItemLayer;
import net.kenji.woh.client.layers.OffHandHolsteredItemLayer;
import net.kenji.woh.compat.combat_hotbar.layers.HolsteredItemCompatLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.entity.PPlayerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;

public class CombatHotbarRenderCompat {

    public static void Init(){
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
            modBus.addListener(CombatHotbarRenderCompat.ClientEvents::onModifyPatchedRenderers);
        });    }

    public static class ClientEvents{
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onModifyPatchedRenderers(PatchedRenderersEvent.Modify event) {
            PatchedEntityRenderer renderer = event.get(EntityType.PLAYER);
            if (renderer instanceof PPlayerRenderer playerRenderer) {
                for(int i = 0; i < CombatHotbarProvider.SLOTS; i++) {
                    playerRenderer.addCustomLayer(new HolsteredItemCompatLayer(null, i));
                }
            }
        }
    }
}
