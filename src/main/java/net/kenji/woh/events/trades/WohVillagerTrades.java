package net.kenji.woh.events.trades;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohVillagerTrades {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {


        if (event.getType() == VillagerProfession.ARMORER) {

            // Level 4 = Expert
            event.getTrades().get(4).add((trader, rand) -> {

                // 35% chance PER villager
                if (rand.nextFloat() > 0.4f) {
                    return null; // trade not added
                }

                return new MerchantOffer(
                        new ItemStack(Items.EMERALD, 48),
                        new ItemStack(WohItems.ARBITERS_SHIELD.get()),
                        1,     // max uses
                        20,    // villager XP
                        0.05F  // price multiplier
                );
            });
        }
    }
}
