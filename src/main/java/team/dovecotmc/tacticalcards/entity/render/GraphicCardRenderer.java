package team.dovecotmc.tacticalcards.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class GraphicCardRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public GraphicCardRenderer(EntityRendererProvider.Context ctx, float scale, boolean fullBright) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public GraphicCardRenderer(EntityRendererProvider.Context ctx) {
        this(ctx, 1.0F, false);
    }

    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrix, MultiBufferSource bufferSource, int p_116090_) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            matrix.pushPose();
            matrix.scale(this.scale, this.scale, this.scale);
            matrix.translate(0, 0.75, 0);
            matrix.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrix.mulPose(Axis.YP.rotationDegrees(entity.tickCount * 30));
            matrix.mulPose(Axis.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(null, entity.getItem(), ItemDisplayContext.GROUND, false, matrix, bufferSource, entity.level(), p_116090_, OverlayTexture.NO_OVERLAY, entity.getId());
            matrix.popPose();
            super.render(entity, entityYaw, partialTicks, matrix, bufferSource, p_116090_);
        }
    }

    public ResourceLocation getTextureLocation(Entity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
