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
        if (state.getBlock() instanceof RedstoneTimerBlock) {
            int power = 0;
            if (state.get(HorizontalFacingBlock.FACING) == Direction.NORTH || state.get(HorizontalFacingBlock.FACING) == Direction.SOUTH) {
                if (world.isEmittingRedstonePower(pos.west(1), Direction.EAST)) {
                    power += ((AbstractBlockMixinInterface) world.getBlockState(pos.west(1)).getBlock()).
                            publicGetStrongRedstonePower(world.getBlockState(pos.west(1)), world, pos, Direction.EAST);
                }
                if (world.isEmittingRedstonePower(pos.east(1), Direction.WEST)) {
                    power += ((AbstractBlockMixinInterface) world.getBlockState(pos.east(1)).getBlock()).
                            publicGetStrongRedstonePower(world.getBlockState(pos.east(1)), world, pos, Direction.WEST);
                }
            }
            else if (state.get(HorizontalFacingBlock.FACING) == Direction.WEST || state.get(HorizontalFacingBlock.FACING) == Direction.EAST) {
                if (world.isEmittingRedstonePower(pos.north(1), Direction.SOUTH)) {
                    power += ((AbstractBlockMixinInterface) world.getBlockState(pos.east(1)).getBlock()).
                            publicGetStrongRedstonePower(world.getBlockState(pos.east(1)), world, pos, Direction.SOUTH);
                }
                if (world.isEmittingRedstonePower(pos.south(1), Direction.NORTH)) {
                    power += ((AbstractBlockMixinInterface) world.getBlockState(pos.east(1)).getBlock()).
                            publicGetStrongRedstonePower(world.getBlockState(pos.east(1)), world, pos, Direction.NORTH);
                }
            }
            blockEntity.needWaitToTick = 20 + (power * 20);
        }
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
