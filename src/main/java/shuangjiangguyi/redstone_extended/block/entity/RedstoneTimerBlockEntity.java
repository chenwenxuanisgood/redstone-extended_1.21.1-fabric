package shuangjiangguyi.redstone_extended.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import shuangjiangguyi.redstone_extended.AbstractBlockMixinInterface;
import shuangjiangguyi.redstone_extended.block.custom.RedstoneTimerBlock;

public class RedstoneTimerBlockEntity extends BlockEntity {
    public int needWaitToTick = 20;

    public RedstoneTimerBlockEntity(BlockPos pos, BlockState state) {
        super(RedstoneExtendedBlockEntities.REDSTONE_TIMER, pos, state);

    }

    public void tick(World world, BlockPos pos, BlockState state, RedstoneTimerBlockEntity blockEntity) {
        Direction direction = state.get(RedstoneTimerBlock.FACING);
        Direction direction2 = direction.rotateYClockwise();
        Direction direction3 = direction.rotateYCounterclockwise();
        boolean bl = ((RedstoneTimerBlock) state.getBlock()).getSideInputFromGatesOnly();
        int power = (world.getEmittedRedstonePower(pos.offset(direction2), direction2, false)) +
                (world.getEmittedRedstonePower(pos.offset(direction3), direction3, false));
        blockEntity.needWaitToTick = 20 + (power * 20);

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("needWaitToTick", needWaitToTick);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        needWaitToTick = nbt.getInt("needWaitToTick");
    }
}
