package mod.arrokoth.tacticalcards.utils;

import mod.arrokoth.tacticalcards.block.GraphicCardBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardBoxBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardDecoBlock;
import mod.arrokoth.tacticalcards.block.TradeTableBlock;
import mod.arrokoth.tacticalcards.entity.render.GraphicCardRenderer;
import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unchecked")
// TODO
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegistry {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) RegistryHandler.ENTITIES.get("card").get(), GraphicCardRenderer::new);
    }

    @SubscribeEvent
    public static void registerBlockRenderType(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<Block> block : RegistryHandler.BLOCKS.values()) {
                if (block.get() instanceof GraphicCardBlock || block.get() instanceof GraphicCardBoxBlock || block.get() instanceof TradeTableBlock) {
                    ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
                }
                if (block.get() instanceof GraphicCardDecoBlock) {
                    ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
                }
            }
        });
    }
}
