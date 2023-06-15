package mod.arrokoth.tacticalcards.utils;

import com.google.common.collect.ImmutableSet;
import mod.arrokoth.tacticalcards.TacticalCards;
import mod.arrokoth.tacticalcards.block.GraphicCardBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardBoxBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardDecoBlock;
import mod.arrokoth.tacticalcards.block.TradeTableBlock;
import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.entity.villager.RandomTradeBuilder;
import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
    //public static final CreativeModeTab TAB_ = new CreativeModeTab("tactical_cards") {
    //    public ItemStack makeIcon() {
    //        return new ItemStack(ITEMS.get("gtx_690").get());
    //    }
    //};

    //    public static final CreativeModeTab DECO_TAB = new CreativeModeTab("tactical_cards_deco")
//    {
//        @Override
//        public ItemStack makeIcon()
//        {
//            return new ItemStack(ITEMS.get("gtx_690_box").get());
//        }
//    };
    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(TacticalCards.MOD_ID, "main"), b -> {
            b.title(Component.translatable("itemGroup.tactical_cards"))
                    .icon(() -> ITEMS.get("gtx_690").get().getDefaultInstance())
                    .displayItems((p, o) -> {
                        ITEMS.values().stream().filter(r -> !r.get().getDescriptionId().contains("deco")).forEach(r -> o.accept(r.get()));
                    });
        });
        //event.registerCreativeModeTab(new ResourceLocation(TacticalCards.MOD_ID, "deco"), b -> {
        //    b.displayItems((p, o) -> {

        //    });
        //});
    }

    public static final RegistryObject<Block> TRADE_TABLE_BLOCK = BLOCKS_REGISTER.register("trade_table", () -> new TradeTableBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()));
    public static final RegistryObject<Item> TRADE_TABLE_ITEM = ITEMS_REGISTER.register("trade_table", () -> new BlockItem(TRADE_TABLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<PoiType> SELLER_POI = POI_TYPES.register("card_seller", () -> new PoiType(Set.of(TRADE_TABLE_BLOCK.get().defaultBlockState()), 50, 50));
    public static final RegistryObject<VillagerProfession> SELLER_PROFESSION = PROFESSIONS.register("card_seller", () -> new VillagerProfession("card_seller",
            p -> p.is(SELLER_POI.getKey()),
            // Available?
            VillagerProfession.ALL_ACQUIRABLE_JOBS,
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.ANVIL_USE));


    public static void register(IEventBus bus) {
        BLOCKS.put("trade_table", TRADE_TABLE_BLOCK);
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
        // Comming soon: ATi Voodoo5 6000
//        registerCard("ati_voodoo5_6000", 20);
//        registerDeco("ati_voodoo5_6000");
        // Paintings
        PAINTINGS.register("poster_1", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_2", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_3", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_4", () -> new PaintingVariant(16, 32));
        PAINTINGS.register("poster_5", () -> new PaintingVariant(128, 96));
        // Entity
        ENTITIES.put("card", ENTITIES_REGISTER.register("card", () -> EntityType.Builder.<GraphicCardEntity>of(GraphicCardEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build(TacticalCards.MOD_ID + ".card")));
        // Villager
        // Final registry
        ITEMS_REGISTER.register(bus);
        BLOCKS_REGISTER.register(bus);
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
        BLOCKS.put(cardId + "_deco", BLOCKS_REGISTER.register(cardId + "_deco", () -> new GraphicCardDecoBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY))));
//        ITEMS.put(cardId + "_deco", ITEMS_REGISTER.register(cardId + "_deco", () -> new BlockItem(BLOCKS.get(cardId + "_deco").get(), new Item.Properties().tab(DECO_TAB))));
        ITEMS.put(cardId + "_deco", ITEMS_REGISTER.register(cardId + "_deco", () -> new BlockItem(BLOCKS.get(cardId + "_deco").get(), new Item.Properties())));
    }

    public static GraphicCardItem getCard(String id) {
        return (GraphicCardItem) ITEMS.get(id).get();
    }

    public static Item getCardWithBox(String id) {
        return ITEMS.get(id + "_box").get();
    }
}

@Mod.EventBusSubscriber(modid = TacticalCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
class TradesRegistry {
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
            event.getTrades().get(1).add(new RandomTradeBuilder(30, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("gt_610"), getMaxPrice("gt_610")).setForSale(RegistryHandler.getCardWithBox("gt_610"), 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(28, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("gtx_590"), getMaxPrice("gtx_590")).setForSale(RegistryHandler.getCardWithBox("gtx_590"), 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(28, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("gtx_690"), getMaxPrice("gtx_690")).setForSale(RegistryHandler.getCardWithBox("gtx_690"), 1, 1).build());
            event.getTrades().get(3).add(new RandomTradeBuilder(24, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("r9_295_x2"), getMaxPrice("r9_295_x2")).setForSale(RegistryHandler.getCardWithBox("r9_295_x2"), 1, 1).build());
            event.getTrades().get(4).add(new RandomTradeBuilder(24, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("hd_3870"), getMaxPrice("hd_3870")).setForSale(RegistryHandler.getCardWithBox("hd_3870"), 1, 1).build());
            event.getTrades().get(4).add(new RandomTradeBuilder(20, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("titan_z"), getMaxPrice("titan_z")).setForSale(RegistryHandler.getCardWithBox("titan_z"), 1, 1).build());
            event.getTrades().get(5).add(new RandomTradeBuilder(18, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("rtx_3090"), getMaxPrice("rtx_3090")).setForSale(RegistryHandler.getCardWithBox("rtx_3090"), 1, 1).build());
            event.getTrades().get(5).add(new RandomTradeBuilder(18, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("rtx_4090"), getMaxPrice("rtx_4090")).setForSale(RegistryHandler.getCardWithBox("rtx_4090"), 1, 1).build());
            event.getTrades().get(5).add(new RandomTradeBuilder(14, 5, 0.05f).setPrice(Items.EMERALD, getMinPrice("rtx_4099"), getMaxPrice("rtx_4099")).setForSale(RegistryHandler.getCardWithBox("rtx_4099"), 1, 1).build());
        }
    }
}
