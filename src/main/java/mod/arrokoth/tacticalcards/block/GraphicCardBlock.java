package mod.arrokoth.tacticalcards.block;

import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardBlock extends FaceAttachedHorizontalDirectionalBlock
{
    public static final IntegerProperty ITEM_DAMAGE = IntegerProperty.create("damage", 0, 200);
    public final float damage;
    protected final VoxelShape NORTH_AABB;
    protected final VoxelShape SOUTH_AABB;
    protected final VoxelShape WEST_AABB;
    protected final VoxelShape EAST_AABB;
    protected final VoxelShape UP_AABB_Z;
    protected final VoxelShape UP_AABB_X;
    protected final VoxelShape DOWN_AABB_Z;
    protected final VoxelShape DOWN_AABB_X;

    public GraphicCardBlock(float damage)
    {
        this(damage,
                Block.box(1.0D, 5.0D, 14.0D, 15.0D, 11.0D, 16.0D),
                Block.box(1.0D, 5.0D, 0.0D, 15.0D, 11.0D, 2.0D),
                Block.box(14.0D, 5.0D, 1.0D, 16.0D, 11.0D, 15.0D),
                Block.box(0.0D, 5.0D, 1.0D, 2.0D, 11.0D, 15.0D),
                Block.box(1.0D, 0.0D, 5.0D, 15.0D, 2.0D, 11.0D),
                Block.box(5.0D, 0.0D, 1.0D, 11.0D, 2.0D, 15.0D),
                Block.box(1.0D, 14.0D, 5.0D, 15.0D, 16.0D, 11.0D),
                Block.box(5.0D, 14.0D, 1.0D, 11.0D, 16.0D, 15.0D));
    }

    public GraphicCardBlock(float damage, VoxelShape NORTH_AABB, VoxelShape SOUTH_AABB, VoxelShape WEST_AABB, VoxelShape EAST_AABB, VoxelShape UP_AABB_Z, VoxelShape UP_AABB_X, VoxelShape DOWN_AABB_Z, VoxelShape DOWN_AABB_X)
    {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY));
        this.damage = damage;
        this.NORTH_AABB = NORTH_AABB;
        this.SOUTH_AABB = SOUTH_AABB;
        this.WEST_AABB = WEST_AABB;
        this.EAST_AABB = EAST_AABB;
        this.UP_AABB_Z = UP_AABB_Z;
        this.UP_AABB_X = UP_AABB_X;
        this.DOWN_AABB_Z = DOWN_AABB_Z;
        this.DOWN_AABB_X = DOWN_AABB_X;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_53184_) {
        BlockState state = super.getStateForPlacement(p_53184_);
        if (state != null) {
            state = state.setValue(ITEM_DAMAGE, p_53184_.getItemInHand().getDamageValue());
            return state;
        }
        return null;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        if (level.hasNeighborSignal(pos)) {
            explode(level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public boolean canSurvive(BlockState p_53186_, LevelReader p_53187_, BlockPos p_53188_) {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion)
    {
        super.onBlockExploded(state, level, pos, explosion);
        explode(level, pos);
        level.removeBlock(pos, false);
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean value)
    {
        if (level.hasNeighborSignal(pos))
        {
            explode(level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        ItemStack stack = new ItemStack(this);
        stack.setDamageValue(state.getValue(ITEM_DAMAGE));
        return List.of(stack);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_)
    {
        p_54663_.add(FACE, FACING, ITEM_DAMAGE);
    }

    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext)
    {
        switch(state.getValue(FACE))
        {
            case FLOOR:
                switch(state.getValue(FACING).getAxis())
                {
                    case X:
                        return UP_AABB_X;
                    case Z:
                    default:
                        return UP_AABB_Z;
                }
            case WALL:
                switch((Direction) state.getValue(FACING))
                {
                    case EAST:
                        return EAST_AABB;
                    case WEST:
                        return WEST_AABB;
                    case SOUTH:
                        return SOUTH_AABB;
                    case NORTH:
                    default:
                        return NORTH_AABB;
                }
            case CEILING:
                default:
                    switch(state.getValue(FACING).getAxis())
                    {
                        case X:
                            return DOWN_AABB_X;
                        case Z:
                        default:
                            return DOWN_AABB_Z;
                    }
        }
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion)
    {
        return false;
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
