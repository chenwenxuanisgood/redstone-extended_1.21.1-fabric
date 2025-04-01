package shuangjiangguyi.redstone_extended.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import shuangjiangguyi.redstone_extended.AbstractBlockMixinInterface;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin implements AbstractBlockMixinInterface {
    @Shadow protected abstract int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction);

    @Override
    public int publicGetStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getStrongRedstonePower(state, world, pos, direction);
    }
}
