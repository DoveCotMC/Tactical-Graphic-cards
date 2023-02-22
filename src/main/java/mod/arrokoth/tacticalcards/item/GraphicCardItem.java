package mod.arrokoth.tacticalcards.item;

import com.google.common.collect.Multimap;
import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardItem extends BlockItem {
    private final TagKey<Block> blocks;
    protected final float damage;

    public GraphicCardItem(Block block, float damage) {
        super(block, new Properties().tab(RegistryHandler.TAB).stacksTo(1).rarity(Rarity.RARE));
        this.damage = damage;
        this.blocks = BlockTags.MINEABLE_WITH_PICKAXE;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (this.damage * 4);
    }

    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(this.blocks) ? (16.0F) * (Math.max(0.5F + ((float) this.getDamage(p_41004_) / (float) this.getMaxDamage(p_41004_)), 1)) : 1.0F;
    }

    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(2, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        this.damageItem(p_40998_, 1, p_41002_, null);

        if (this.getDamage(p_40998_) == 0) {
            explode(p_40999_, p_41001_);
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(blocks) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(Tiers.NETHERITE, state);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            GraphicCardEntity card = new GraphicCardEntity(world, player, player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z(), new ItemStack(this), this.damage);
            card.setPos(player.getEyePosition());
            world.addFreshEntity(card);
            player.getCooldowns().addCooldown(this, 20);
            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }

    protected void explode(Level level, BlockPos pos)
    {
        if (!level.isClientSide)
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, null);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.pow(this.damage / 3, 1.25), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            Iterable<BlockPos> positions = BlockPos.betweenClosed((int) (pos.getX() - this.damage / 2), (int) (pos.getY() - this.damage / 2), (int) (pos.getZ() - this.damage / 2), (int) (pos.getX() + this.damage / 2), (int) (pos.getY() + this.damage / 2), (int) (pos.getZ() + this.damage / 2));
            for (BlockPos pos1 : positions)
            {
                double distance = Math.sqrt(Math.pow(pos1.getX() - pos.getX(), 2) + Math.pow(pos1.getZ() - pos.getZ(), 2) + Math.pow(pos1.getY() - pos.getY(), 2));
                if (distance <= this.damage / 2)
                {
                    BlockPos pos2 = new BlockPos(pos1.getX(), pos1.getY(), pos1.getZ());
                    if (level.getBlockState(pos2.below()).getMaterial().isSolid() &&
                            !level.getBlockState(pos2).getMaterial().isSolid() &&
                            !level.getBlockState(pos2).getMaterial().isLiquid() &&
                            (distance == 0 || level.getRandom().nextInt((int) (this.damage + (distance * 2))) <= this.damage - distance))
                    {
                        level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
                    }
                }
            }
//            ExplosionManager.explode(this.damage, level, pos);
        }
    }
}
