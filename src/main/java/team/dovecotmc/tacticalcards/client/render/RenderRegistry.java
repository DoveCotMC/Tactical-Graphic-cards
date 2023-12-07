package team.dovecotmc.tacticalcards.client.render;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.dovecotmc.tacticalcards.entity.render.GraphicCardRenderer;
import team.dovecotmc.tacticalcards.utils.RegistryHandler;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegistry {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) RegistryHandler.ENTITIES.get("card").get(), GraphicCardRenderer::new);
    }

    @SubscribeEvent
    public static void onRenderTypeReg(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(RegistryHandler.TRADE_TABLE_BLOCK.get(), RenderType.cutout());
        });
    }
}
