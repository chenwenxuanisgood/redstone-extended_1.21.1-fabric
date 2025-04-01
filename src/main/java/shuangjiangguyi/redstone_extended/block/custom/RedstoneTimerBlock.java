package shuangjiangguyi.redstone_extended.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import shuangjiangguyi.redstone_extended.block.entity.RedstoneExtendedBlockEntities;
import shuangjiangguyi.redstone_extended.block.entity.RedstoneTimerBlockEntity;

public class RedstoneTimerBlock extends AbstractRedstoneGateBlockWithEntity {
    public static final BooleanProperty LOCKED = Properties.LOCKED;
    public static final EnumProperty<Type> TYPE = EnumProperty.of("type", Type.class);
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final MapCodec<RedstoneTimerBlock> CODEC = createCodec(RedstoneTimerBlock::new);
    public RedstoneTimerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TYPE, Type.NOT_ACTIVATION).with(LOCKED, false).with(POWERED, false));
    }

    @Override
    protected MapCodec<? extends AbstractRedstoneGateBlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneTimerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, RedstoneExtendedBlockEntities.REDSTONE_TIMER,
                ((world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LOCKED, POWERED, TYPE);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        return blockState.with(LOCKED, this.isLocked(ctx.getWorld(), ctx.getBlockPos(), blockState));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state, World world, BlockPos pos) {
        return ((RedstoneTimerBlockEntity) world.getBlockEntity(pos)).needWaitToTick;
    }

    public enum Type implements StringIdentifiable {
        NOT_ACTIVATION("not_activation"),
        ACTIVATION("activation");

        private final String name;
        Type(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (direction == Direction.DOWN && !this.canPlaceAbove(world, neighborPos, neighborState)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return !world.isClient() && direction.getAxis() != state.get(FACING).getAxis()
                    ? state.with(LOCKED, this.isLocked(world, pos, state))
                    : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public boolean isLocked(WorldView world, BlockPos pos, BlockState state) {
        return this.getMaxInputLevelSides(world, pos, state) > 0;
    }

    @Override
    protected boolean getSideInputFromGatesOnly() {
        return true;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof RedstoneTimerBlockEntity) {
            RedstoneTimerBlockEntity blockEntity = (RedstoneTimerBlockEntity) world.getBlockEntity(pos);
            player.sendMessage(Text.translatable("text.block.redstone_timer.use_text.show_need_to_wait_tick").append(String.valueOf(blockEntity.needWaitToTick)), true);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }
}
