package shuangjiangguyi.redstone_extended.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;
import org.jetbrains.annotations.Nullable;
import shuangjiangguyi.redstone_extended.RedstoneExtended;

public abstract class AbstractRedstoneGateBlockWithEntity extends AbstractRedstoneGateBlock implements BlockEntityProvider {
    protected AbstractRedstoneGateBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    protected abstract MapCodec<? extends AbstractRedstoneGateBlockWithEntity> getCodec();

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
    }

    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
    }

    /**
     * {@return the ticker if the given type and expected type are the same, or {@code null} if they are different}
     */
    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker
    ) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    protected abstract int getUpdateDelayInternal(BlockState state, World world, BlockPos pos);

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isLocked(world, pos, state)) {
            boolean bl = state.get(POWERED);
            boolean bl2 = this.hasPower(world, pos, state);
            if (bl && !bl2) {
                world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
            } else if (!bl) {
                world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_LISTENERS);
                if (!bl2) {
                    world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state, world, pos), TickPriority.VERY_HIGH);
                }
            }
        }
    }

    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (!this.isLocked(world, pos, state)) {
            boolean bl = state.get(POWERED);
            boolean bl2 = this.hasPower(world, pos, state);
            if (bl != bl2 && !world.getBlockTickScheduler().isTicking(pos, this)) {
                TickPriority tickPriority = TickPriority.HIGH;
                if (this.isTargetNotAligned(world, pos, state)) {
                    tickPriority = TickPriority.EXTREMELY_HIGH;
                } else if (bl) {
                    tickPriority = TickPriority.VERY_HIGH;
                }

                world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state, world, pos), tickPriority);
            }
        }
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (state.canPlaceAt(world, pos)) {
            if (!this.isLocked(world, pos, state)) {
                boolean bl = state.get(POWERED);
                boolean bl2 = this.hasPower(world, pos, state);
                if (bl != bl2 && !world.getBlockTickScheduler().isTicking(pos, this)) {
                    TickPriority tickPriority = TickPriority.HIGH;
                    if (this.isTargetNotAligned(world, pos, state)) {
                        tickPriority = TickPriority.EXTREMELY_HIGH;
                    } else if (bl) {
                        tickPriority = TickPriority.VERY_HIGH;
                    }

                    world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state, world, pos), tickPriority);
                }
            }
        } else {
            BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            dropStacks(state, world, pos, blockEntity);
            world.removeBlock(pos, false);

            for (Direction direction : Direction.values()) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }
        }
    }

    @Override
    protected abstract int getUpdateDelayInternal(BlockState state);
}
