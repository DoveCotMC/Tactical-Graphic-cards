package mod.arrokoth.tacticalcards.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;

public class ExplosionManager {
    public static void explode(float damage, Level level, BlockPos pos) {
        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, null);
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.pow(damage / 3, 1.25), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
        Iterable<BlockPos> positions = BlockPos.betweenClosed((int) (pos.getX() - damage / 2), (int) (pos.getY() - damage / 2), (int) (pos.getZ() - damage / 2), (int) (pos.getX() + damage / 2), (int) (pos.getY() + damage / 2), (int) (pos.getZ() + damage / 2));
        for (BlockPos pos1 : positions) {
            double distance = Math.sqrt(Math.pow(pos1.getX() - pos.getX(), 2) + Math.pow(pos1.getZ() - pos.getZ(), 2) + Math.pow(pos1.getY() - pos.getY(), 2));
            if (distance <= damage / 2) {
                BlockPos pos2 = new BlockPos(pos1.getX(), pos1.getY(), pos1.getZ());
                if (level.getBlockState(pos2.below()).getMaterial().isSolid() &&
                        !level.getBlockState(pos2).getMaterial().isSolid() &&
                        !level.getBlockState(pos2).getMaterial().isLiquid() &&
                        (distance == 0 || level.getRandom().nextInt((int) (damage + (distance * 2))) <= damage - distance)) {
                    level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
                }
            }
        }
        // TODO: New explosion function
//        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.pow(damage / 3, 1.25), false, Explosion.BlockInteraction.NONE);
//        for (short i = 0; i < level.getRandom().nextInt(4) + 32; ++i) {
//            System.out.println(i);
//            double rx = Math.sin(level.getRandom().nextInt(360));
//            double rz = Math.cos(level.getRandom().nextInt(360));
//            double ry = (level.getRandom().nextDouble() * 2) - 1;
////            for (int j = 0; j < level.getRandom().nextInt(2) + (damage / 8); ++j) {
//////                System.out.println(pos.getX() + (rx * i));
////                BlockPos pos2 = new BlockPos(pos.getX() + (rx * i), pos.getY() + ry, pos.getZ() + (rz * i)).below();
////                level.setBlockAndUpdate(pos2, Blocks.DIAMOND_BLOCK.defaultBlockState());
////            }
//            for (int j = 0; j < level.getRandom().nextInt(8) + (damage / 1.5); ++j) {
//                BlockPos pos1 = new BlockPos(pos.getX() + (rx * j), pos.getY() + (ry * j), pos.getZ() + (rz * j)).below();
////                if (level.getBlockState(pos2.below()).getMaterial().isSolid() &&
////                        !level.getBlockState(pos2).getMaterial().isSolid() &&
////                        !level.getBlockState(pos2).getMaterial().isLiquid())
////                {
////                    level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
////                }
//                int var0 = level.getRandom().nextInt(2);
//                for (BlockPos pos2 : BlockPos.betweenClosed(new BlockPos(pos1.getX() - var0, pos1.getY() - var0, pos1.getZ() - var0),
//                                            new BlockPos(pos1.getX() + var0, pos1.getY() + var0, pos1.getZ() + var0))) {
//                    if (level.getRandom().nextBoolean()) {
////                        level.setBlockAndUpdate(pos2, Blocks.DIAMOND_BLOCK.defaultBlockState());
//                        if (level.getBlockState(pos2.below()).getMaterial().isSolid() &&
//                                !level.getBlockState(pos2).getMaterial().isSolid() &&
//                                !level.getBlockState(pos2).getMaterial().isLiquid())
//                        {
//                            level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
//                        }
//                    }
//                }
//            }
//        }
    }
}
