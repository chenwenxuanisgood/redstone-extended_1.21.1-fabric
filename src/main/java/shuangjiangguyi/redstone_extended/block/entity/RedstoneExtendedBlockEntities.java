package shuangjiangguyi.redstone_extended.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import shuangjiangguyi.redstone_extended.RedstoneExtended;
import shuangjiangguyi.redstone_extended.block.RedstoneExtendedBlocks;

public class RedstoneExtendedBlockEntities {
    public static final BlockEntityType<RedstoneTimerBlockEntity> REDSTONE_TIMER = create("redstone_timer_block_entity",
            BlockEntityType.Builder.create(RedstoneTimerBlockEntity::new, RedstoneExtendedBlocks.REDSTONE_TIMER));
    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(RedstoneExtended.MOD_ID, id), builder.build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id)));
    }
    public static void registerBlockEntities() {
    }
}
