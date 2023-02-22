package mod.arrokoth.tacticalcards.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TradeTableBlock extends HorizontalDirectionalBlock {
    public TradeTableBlock(Properties p_49795_)
    {
        super(p_49795_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_56872_) {
        return this.defaultBlockState().setValue(FACING, p_56872_.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.join(Block.box(0.0D, 11.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(1.5D, 0.0D, 1.5D, 14.5D, 11.0D, 14.5D), BooleanOp.OR);
    }

    @Override
    public boolean canSurvive(BlockState p_60525_, LevelReader p_60526_, BlockPos p_60527_) {
        return super.canSurvive(p_60525_, p_60526_, p_60527_) && !(p_60526_.getBlockState(new BlockPos(p_60527_.getX(), p_60527_.getY() - 1, p_60527_.getZ())).getBlock() instanceof TradeTableBlock);
    }
}
