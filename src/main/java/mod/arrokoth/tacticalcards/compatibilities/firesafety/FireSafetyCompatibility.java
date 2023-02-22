package mod.arrokoth.tacticalcards.compatibilities.firesafety;

import committee.nova.firesafety.api.FireSafetyApi;
import committee.nova.firesafety.api.event.FireSafetyExtensionEvent;
import mod.arrokoth.tacticalcards.TacticalCards;
import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

import static mod.arrokoth.tacticalcards.utils.RegistryHandler.ITEMS;
import static net.minecraft.sounds.SoundEvents.GENERIC_EXTINGUISH_FIRE;
import static net.minecraft.sounds.SoundSource.BLOCKS;


public class FireSafetyCompatibility {
    public static final HashSet<Item> cards = new HashSet<>();
    public static void onExtension(FireSafetyExtensionEvent event) {
        event.addExtinguishable(TacticalCards.MOD_NAME, (short) 32666, new FireSafetyApi.ExtinguishableEntity(
                (l, e) -> e instanceof GraphicCardEntity,
                (l, e) -> {
                    final var stack = ((GraphicCardEntity) e).getItem();
                    l.addFreshEntity(new ItemEntity(l, e.getX(), e.getY(), e.getZ(), stack));
                    l.playSound(null, e, GENERIC_EXTINGUISH_FIRE, BLOCKS, 1F, 1F);
                    e.discard();
                }
        ));
        event.addFireDanger(TacticalCards.MOD_NAME, (short) 32666, new FireSafetyApi.FireDangerEntity(
                (l, e) -> e instanceof GraphicCardEntity,
                (l, e) -> 4,
                (l, e) -> new TranslatableComponent("tips.firesafety.danger.tactical_cards")
        ));
        event.addFireDanger(TacticalCards.MOD_NAME, (short) 32667, new FireSafetyApi.FireDangerEntity(
                //todo: nice now don't need to do that :D
                (l, e) -> e instanceof Player p && p.getInventory().hasAnyOf(getCards()),
                (l, e) -> 3,
                (l, e) -> new TranslatableComponent("tips.firesafety.danger.player_with_card")
        ));
    }


    public static Set<Item> getCards() {
        if (cards.isEmpty()) ITEMS.forEach((s, i) -> {
            final var item = i.get();
            if (item instanceof GraphicCardItem) cards.add(item);
        });
        return cards;
    }
}
