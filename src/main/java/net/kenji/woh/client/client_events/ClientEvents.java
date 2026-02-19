package net.kenji.woh.client.client_events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.ModModelLayers;
import net.kenji.woh.client.enitity_models.BeamSlashModel;
import net.kenji.woh.client.enitity_models.ExiledRoninModel;
import net.kenji.woh.client.entity_renderers.BeamSlashRenderer;
import net.kenji.woh.client.entity_renderers.ExiledRoninRenderer;
import net.kenji.woh.client.layers.HolsteredItemLayer;
import net.kenji.woh.client.layers.OffHandHolsteredItemLayer;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.render.ArbitersBladeRender;
import net.kenji.woh.render.ArbitersShieldRender;
import net.kenji.woh.render.ShotogatanaRender;
import net.kenji.woh.render.TsumeRender;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
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
    public static void registerRenderer(PatchedRenderersEvent.RegisterItemRenderer event) {
        event.addItemRenderer(WeaponsOfHarmony.identifier("shotogatana"), ShotogatanaRender::new);
        event.addItemRenderer(WeaponsOfHarmony.identifier("tsume"), TsumeRender::new);
        event.addItemRenderer(WeaponsOfHarmony.identifier("arbiters_blade"), ArbitersBladeRender::new);
        event.addItemRenderer(WeaponsOfHarmony.identifier("arbiters_shield"), ArbitersShieldRender::new);

    }

    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.EXILED_RONIN_LAYER, ExiledRoninModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BEAM_SLASH_ENTITY_LAYER, BeamSlashModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
          EntityRenderers.register(WohEntities.EXILED_RONIN.get(), ExiledRoninRenderer::new);
          EntityRenderers.register(WohEntities.RONIN_SKELETON.get(), SkeletonRenderer::new);
          EntityRenderers.register(WohEntities.WAR_FAN_PILLAGER.get(), PillagerRenderer::new);
          EntityRenderers.register(WohEntities.CLAWED_ZOMBIE_VILLAGER.get(), ZombieVillagerRenderer::new);

          EntityRenderers.register(
                  WohEntities.BEAM_SLASH.get(),
                  BeamSlashRenderer::new
          );
          ItemProperties.register(
                  WohItems.SHOTOGATANA.get(),
                  new ResourceLocation("woh", "unsheathed"),
                  (stack, level, entity, seed) -> {
                      if(entity instanceof Player player) {
                          return player.getMainHandItem().getItem() == WohItems.SHOTOGATANA.get() ? 1.0F : 0.0F;
                      }
                      return 0;
                  }
          );
          ItemProperties.register(
                  WohItems.WAKIZASHI.get(),
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
                playerRenderer.addCustomLayer(new OffHandHolsteredItemLayer(null));

            }
        }
    }
}