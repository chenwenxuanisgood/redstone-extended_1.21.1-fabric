package shuangjiangguyi.redstone_extended;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public interface AbstractBlockMixinInterface {
    int publicGetStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction);
}
