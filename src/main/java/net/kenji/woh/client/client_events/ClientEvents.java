package net.kenji.woh.client.client_events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.ModModelLayers;
import net.kenji.woh.client.enitity_models.ExiledRoninModel;
import net.kenji.woh.client.entity_renderers.ExiledRoninRenderer;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.registry.item.WOHItems;
import net.kenji.woh.render.EnhancedKatanaRender;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jline.utils.Log;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void RenderRegistry(PatchedRenderersEvent.Add event) {
        event.addItemRenderer((Item) WOHItems.ENHANCED_KATANA.get(), new EnhancedKatanaRender());

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
                  WOHItems.ENHANCED_KATANA.get(),
                  new ResourceLocation("woh", "unsheathed"),
                  (stack, level, entity, seed) -> {
                      if(entity instanceof Player player) {

                          return player.getMainHandItem().getItem() == stack.getItem() ? 1.0F : 0.0F;
                      }
                      return 0;
                  }
          );
      });
      }
}