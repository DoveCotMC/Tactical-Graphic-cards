package team.dovecotmc.tacticalcards.utils;

import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.dovecotmc.tacticalcards.TacticalCards;
import team.dovecotmc.tacticalcards.entity.villager.RandomTradeBuilder;

@Mod.EventBusSubscriber(modid = TacticalCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradesRegistry {
    private static int getPrice(String name) {
        return (int) (RegistryHandler.getCard(name).damage / 1.5F);
    }

    private static int getMaxPrice(String name) {
        return getPrice(name) + 4;
    }

    private static int getMinPrice(String name) {
        return getPrice(name) - 4;
    }

    @SubscribeEvent
    public static void register(VillagerTradesEvent event) {
        if (event.getType().equals(RegistryHandler.SELLER_PROFESSION.get())) {
            // TODO: Auto register
            event.getTrades().get(1).add(new RandomTradeBuilder(30, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("gt_610"), getMaxPrice("gt_610"))
                    .setForSale(RegistryHandler.getCardWithBox("gt_610"), 1, 1)
                    .build());
            event.getTrades().get(2).add(new RandomTradeBuilder(28, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("gtx_590"), getMaxPrice("gtx_590"))
                    .setForSale(RegistryHandler.getCardWithBox("gtx_590"), 1, 1)
                    .build());
            event.getTrades().get(2).add(new RandomTradeBuilder(28, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("gtx_690"), getMaxPrice("gtx_690"))
                    .setForSale(RegistryHandler.getCardWithBox("gtx_690"), 1, 1)
                    .build());
            event.getTrades().get(3).add(new RandomTradeBuilder(24, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("r9_295_x2"), getMaxPrice("r9_295_x2"))
                    .setForSale(RegistryHandler.getCardWithBox("r9_295_x2"), 1, 1)
                    .build());
            event.getTrades().get(4).add(new RandomTradeBuilder(24, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("hd_3870"), getMaxPrice("hd_3870"))
                    .setForSale(RegistryHandler.getCardWithBox("hd_3870"), 1, 1)
                    .build());
            event.getTrades().get(4).add(new RandomTradeBuilder(20, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("titan_z"), getMaxPrice("titan_z"))
                    .setForSale(RegistryHandler.getCardWithBox("titan_z"), 1, 1)
                    .build());
            event.getTrades().get(5).add(new RandomTradeBuilder(18, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("rtx_3090"), getMaxPrice("rtx_3090"))
                    .setForSale(RegistryHandler.getCardWithBox("rtx_3090"), 1, 1)
                    .build());
            event.getTrades().get(5).add(new RandomTradeBuilder(18, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("rtx_4090"), getMaxPrice("rtx_4090"))
                    .setForSale(RegistryHandler.getCardWithBox("rtx_4090"), 1, 1)
                    .build());
            event.getTrades().get(5).add(new RandomTradeBuilder(14, 5, 0.05f)
                    .setPrice(Items.EMERALD, getMinPrice("rtx_4099"), getMaxPrice("rtx_4099"))
                    .setForSale(RegistryHandler.getCardWithBox("rtx_4099"), 1, 1)
                    .build());
        }
    }
}
