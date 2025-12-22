package net.kenji.woh.client.client_events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.ModModelLayers;
import net.kenji.woh.client.enitity_models.ExiledRoninModel;
import net.kenji.woh.client.entity_renderers.ExiledRoninRenderer;
import net.kenji.woh.client.layers.HolsteredItemLayer;
import net.kenji.woh.client.layers.OffHandHolsteredItemLayer;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.registry.item.WOHItems;
import net.kenji.woh.render.EnhancedKatanaRender;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.entity.PPlayerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void RenderRegistry(PatchedRenderersEvent.Add event) {
        event.addItemRenderer((Item) WOHItems.SHOTOGATANA.get(), new EnhancedKatanaRender());

    }

    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.EXILED_RONIN_LAYER, ExiledRoninModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
          EntityRenderers.register(ModEntities.EXILED_RONIN.get(), ExiledRoninRenderer::new);
          ItemProperties.register(
                  WOHItems.SHOTOGATANA.get(),
                  new ResourceLocation("woh", "unsheathed"),
                  (stack, level, entity, seed) -> {
                      if(entity instanceof Player player) {
                          return player.getMainHandItem().getItem() == WOHItems.SHOTOGATANA.get() ? 1.0F : 0.0F;
                      }
                      return 0;
                  }
          );
          ItemProperties.register(
                  WOHItems.MASTERS_WAKIZASHI.get(),
                  new ResourceLocation("woh", "wakizashi_unsheathed"),
                  (stack, level, entity, seed) -> {
                      if(entity instanceof Player player) {
                          return player.getMainHandItem().getItem() == stack.getItem() ? 1.0F : 0.0F;
                      }
                      return 0;
                  }
          );
      });
    }
    @SubscribeEvent
    public static void onModifyPatchedRenderers(PatchedRenderersEvent.Modify event) {
        PatchedEntityRenderer renderer = event.get(EntityType.PLAYER);
        if (renderer instanceof PPlayerRenderer playerRenderer) {
            for(int i = 0; i < 8; i++) {
                playerRenderer.addCustomLayer(new HolsteredItemLayer(null, i));
            }
        }
    }
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers evt) {
        evt.getSkins().forEach((s) -> {
            EntityRenderer<? extends Player> renderer = evt.getSkin(s);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new OffHandHolsteredItemLayer(livingRenderer));
            }
        });
    }
}