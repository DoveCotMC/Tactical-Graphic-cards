package team.dovecotmc.tacticalcards.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.Vec3;

public class ExplosionManager {
    public static void explode(float damage, Level level, Vec3 pos) {
        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, null);
        level.explode(null, pos.x(), pos.y(), pos.z(), (float) Math.pow(damage / 3, 1.25), flag, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
        Iterable<BlockPos> positions = BlockPos.betweenClosed((int) (pos.x() - damage / 2), (int) (pos.y() - damage / 2), (int) (pos.z() - damage / 2), (int) (pos.x() + damage / 2), (int) (pos.y() + damage / 2), (int) (pos.z() + damage / 2));
        for (BlockPos pos1 : positions) {
            double distance = Math.sqrt(Math.pow(pos1.getX() - pos.x(), 2) + Math.pow(pos1.getZ() - pos.z(), 2) + Math.pow(pos1.getY() - pos.y(), 2));
            if (distance <= damage / 2) {
                BlockPos pos2 = new BlockPos(pos1.getX(), pos1.getY(), pos1.getZ());
                if (level.getBlockState(pos2.below()).isSolid() &&
                        !level.getBlockState(pos2).isSolid() &&
                        !level.getBlockState(pos2).liquid() &&
                        (distance == 0 || level.getRandom().nextInt((int) (damage + (distance * 2))) <= damage - distance)) {
                    level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
                }
            }
        }
        // TODO: New explosion function
//        level.explode(null, pos.x(), pos.y(), pos.z(), (float) Math.pow(damage / 3, 1.25), false, Explosion.BlockInteraction.NONE);
//        for (short i = 0; i < level.getRandom().nextInt(4) + 32; ++i) {
//            System.out.println(i);
//            double rx = Math.sin(level.getRandom().nextInt(360));
//            double rz = Math.cos(level.getRandom().nextInt(360));
//            double ry = (level.getRandom().nextDouble() * 2) - 1;
////            for (int j = 0; j < level.getRandom().nextInt(2) + (damage / 8); ++j) {
//////                System.out.println(pos.x() + (rx * i));
////                BlockPos pos2 = new BlockPos(pos.x() + (rx * i), pos.y() + ry, pos.z() + (rz * i)).below();
////                level.setBlockAndUpdate(pos2, Blocks.DIAMOND_BLOCK.defaultBlockState());
////            }
//            for (int j = 0; j < level.getRandom().nextInt(8) + (damage / 1.5); ++j) {
//                BlockPos pos1 = new BlockPos(pos.x() + (rx * j), pos.y() + (ry * j), pos.z() + (rz * j)).below();
////                if (level.getBlockState(pos2.below()).getMaterial().isSolid() &&
////                        !level.getBlockState(pos2).getMaterial().isSolid() &&
////                        !level.getBlockState(pos2).getMaterial().isLiquid())
////                {
////                    level.setBlockAndUpdate(pos2, BaseFireBlock.getState(level, pos2));
////                }
//                int var0 = level.getRandom().nextInt(2);
//                for (BlockPos pos2 : BlockPos.betweenClosed(new BlockPos(pos1.x() - var0, pos1.y() - var0, pos1.z() - var0),
//                                            new BlockPos(pos1.x() + var0, pos1.y() + var0, pos1.z() + var0))) {
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
