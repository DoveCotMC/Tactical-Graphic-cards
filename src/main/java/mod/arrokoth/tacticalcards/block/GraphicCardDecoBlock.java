package mod.arrokoth.tacticalcards.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GraphicCardDecoBlock extends HorizontalDirectionalBlock {
    public GraphicCardDecoBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_56872_) {
        return this.defaultBlockState().setValue(FACING, p_56872_.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D);
    }

    @Override
    public boolean canSurvive(BlockState p_53186_, LevelReader p_53187_, BlockPos p_53188_) {
        return true;
    }
}
