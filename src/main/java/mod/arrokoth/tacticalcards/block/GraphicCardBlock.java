package mod.arrokoth.tacticalcards.block;

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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardBlock extends FaceAttachedHorizontalDirectionalBlock
{
    protected final float damage;
    // Block.box(x, y, z, w, h, d)
//    protected static final VoxelShape NORTH_AABB = Block.box(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
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

    @Override
    public boolean canSurvive(BlockState p_53186_, LevelReader p_53187_, BlockPos p_53188_)
    {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
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
        return List.of(new ItemStack(this));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_)
    {
        p_54663_.add(FACE, FACING);
    }

    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext)
    {
        switch((AttachFace) state.getValue(FACE))
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
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), this.damage / 2, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            for (int x = (int) (pos.getX() - (this.damage / 2)); x < (int) (pos.getX() + (this.damage / 2)); ++x)
            {
                for (int z = (int) (pos.getZ() - (this.damage / 2)); z < (int) (pos.getZ() + (this.damage / 2)); ++z)
                {
                    for (int y = (int) (pos.getY() - (this.damage / 2)); y < (int) (pos.getY() + (this.damage / 2)); ++y)
                    {
                        double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(z - pos.getZ(), 2) + Math.pow(y - pos.getY(), 2));
                        if (distance <= this.damage / 2)
                        {
                            BlockPos pos1 = new BlockPos(x, y, z);
                            if (level.getBlockState(pos1.below()).getMaterial().isSolid() && !level.getBlockState(pos1).getMaterial().isSolid() && (distance == 0 || level.random.nextInt((int) (this.damage + (distance * 2))) <= this.damage - distance))
                            {
                                level.setBlockAndUpdate(pos1, BaseFireBlock.getState(level, pos1));
                            }
                        }
                    }
                }
            }
        }
    }
}
