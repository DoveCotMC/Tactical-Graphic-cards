package team.dovecotmc.tacticalcards.utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.dovecotmc.tacticalcards.TacticalCards;
import team.dovecotmc.tacticalcards.block.GraphicCardBlock;
import team.dovecotmc.tacticalcards.block.GraphicCardBoxBlock;
import team.dovecotmc.tacticalcards.block.GraphicCardDecoBlock;
import team.dovecotmc.tacticalcards.block.TradeTableBlock;
import team.dovecotmc.tacticalcards.entity.GraphicCardEntity;
import team.dovecotmc.tacticalcards.item.GraphicCardItem;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@MethodsReturnNonnullByDefault
public class RegistryHandler {
    public static final Map<String, RegistryObject<Item>> ITEMS = new TreeMap<>();
    private static final DeferredRegister<Item> ITEMS_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TacticalCards.MOD_ID);
    public static final Map<String, RegistryObject<Block>> BLOCKS = new TreeMap<>();
    private static final DeferredRegister<Block> BLOCKS_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TacticalCards.MOD_ID);
    public static final Map<String, RegistryObject<EntityType<?>>> ENTITIES = new TreeMap<>();
    private static final DeferredRegister<EntityType<?>> ENTITIES_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TacticalCards.MOD_ID);

    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, TacticalCards.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, TacticalCards.MOD_ID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, TacticalCards.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TacticalCards.MOD_ID);

    public static final RegistryObject<Block> TRADE_TABLE_BLOCK = BLOCKS_REGISTER.register("trade_table", () -> new TradeTableBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<Item> TRADE_TABLE_ITEM = ITEMS_REGISTER.register("trade_table", () -> new BlockItem(TRADE_TABLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<PoiType> SELLER_POI = POI_TYPES.register("card_seller", () -> new PoiType(Set.of(TRADE_TABLE_BLOCK.get().defaultBlockState()), 50, 50));
    public static final RegistryObject<VillagerProfession> SELLER_PROFESSION = PROFESSIONS.register("card_seller", () -> new VillagerProfession("card_seller",
            h -> h.value().equals(SELLER_POI.get()),
            h -> h.value().equals(SELLER_POI.get()),
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.ANVIL_USE));


    public static void register(IEventBus bus) {
        BLOCKS.put("trade_table", TRADE_TABLE_BLOCK);
        ITEMS.put("trade_table", TRADE_TABLE_ITEM);
        // NVIDIA
        registerCard("gt_610", 15,
                Block.box(4.0D, 5.0D, 14.0D, 12.0D, 11.0D, 16.0D),
                Block.box(4.0D, 5.0D, 0.0D, 12.0D, 11.0D, 2.0D),
                Block.box(14.0D, 5.0D, 4.0D, 16.0D, 11.0D, 12.0D),
                Block.box(0.0D, 5.0D, 4.0D, 2.0D, 11.0D, 12.0D),
                Block.box(4.0D, 0.0D, 5.0D, 12.0D, 2.0D, 11.0D),
                Block.box(5.0D, 0.0D, 4.0D, 11.0D, 2.0D, 12.0D),
                Block.box(4.0D, 14.0D, 5.0D, 12.0D, 16.0D, 11.0D),
                Block.box(5.0D, 14.0D, 4.0D, 11.0D, 16.0D, 12.0D));
        registerDeco("gt_610");
        registerCard("gtx_590", 20);
        registerDeco("gtx_590");
        registerCard("gtx_690", 30);
        registerDeco("gtx_690");
        registerCard("titan_z", 40);
        registerDeco("titan_z");
        registerCard("rtx_3090", 50);
        registerDeco("rtx_3090");
        registerCard("rtx_4090", 55);
        registerDeco("rtx_4090");
        registerCard("rtx_4099", 60);
        registerDeco("rtx_4099");
        // AMD
        registerCard("r9_295_x2", 30);
        registerDeco("r9_295_x2");
        // What about RX 580?
        registerCard("hd_3870", 40);
        registerDeco("hd_3870");
        // Coming soon: ATi Voodoo5 6000
        // registerCard("ati_voodoo5_6000", 20);
        // registerDeco("ati_voodoo5_6000");
        // Paintings
        PAINTINGS.register("poster_1", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_2", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_3", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_4", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_5", () -> new PaintingVariant(128, 96));
        // Entity
        ENTITIES.put("card", ENTITIES_REGISTER.register("card", () -> EntityType.Builder.<GraphicCardEntity>of(GraphicCardEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build(TacticalCards.MOD_ID + ".card")));
        // Tabs
        TABS.register("main", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.tactical_cards"))
                .icon(() -> ITEMS.get("gtx_690").get().getDefaultInstance())
                .displayItems((p, o) -> ITEMS.values()
                        .stream()
                        .filter(r -> !r.get().getDescriptionId().contains("deco")).
                        forEach(r -> o.accept(r.get())))
                .build());
        // Final registry
        ITEMS_REGISTER.register(bus);
        BLOCKS_REGISTER.register(bus);
        TABS.register(bus);
        ENTITIES_REGISTER.register(bus);
        PAINTINGS.register(bus);
        PROFESSIONS.register(bus);
        POI_TYPES.register(bus);
    }

    public static void registerCard(String id, float damage) {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
        BLOCKS.put(id + "_box", BLOCKS_REGISTER.register(id + "_box", () -> new GraphicCardBoxBlock((GraphicCardBlock) BLOCKS.get(id).get())));
        ITEMS.put(id + "_box", ITEMS_REGISTER.register(id + "_box", () -> new BlockItem(BLOCKS.get(id + "_box").get(), new Item.Properties().rarity(Rarity.RARE))));
    }

    public static void registerCard(String id, float damage, VoxelShape NORTH_AABB, VoxelShape SOUTH_AABB, VoxelShape WEST_AABB, VoxelShape EAST_AABB, VoxelShape UP_AABB_Z, VoxelShape UP_AABB_X, VoxelShape DOWN_AABB_Z, VoxelShape DOWN_AABB_X) {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage, NORTH_AABB, SOUTH_AABB, WEST_AABB, EAST_AABB, UP_AABB_Z, UP_AABB_X, DOWN_AABB_Z, DOWN_AABB_X)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
        BLOCKS.put(id + "_box", BLOCKS_REGISTER.register(id + "_box", () -> new GraphicCardBoxBlock((GraphicCardBlock) BLOCKS.get(id).get())));
        ITEMS.put(id + "_box", ITEMS_REGISTER.register(id + "_box", () -> new BlockItem(BLOCKS.get(id + "_box").get(), new Item.Properties().rarity(Rarity.RARE))));
    }

    public static void registerDeco(String cardId) {
        BLOCKS.put(cardId + "_deco", BLOCKS_REGISTER.register(cardId + "_deco", () -> new GraphicCardDecoBlock(BlockBehaviour.Properties.of())));
        ITEMS.put(cardId + "_deco", ITEMS_REGISTER.register(cardId + "_deco", () -> new BlockItem(BLOCKS.get(cardId + "_deco").get(), new Item.Properties())));
    }

    public static GraphicCardItem getCard(String id) {
        return (GraphicCardItem) ITEMS.get(id).get();
    }

    public static Item getCardWithBox(String id) {
        return ITEMS.get(id + "_box").get();
    }
}
