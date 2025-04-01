package shuangjiangguyi.redstone_extended.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import shuangjiangguyi.redstone_extended.RedstoneExtended;
import shuangjiangguyi.redstone_extended.block.custom.RedstoneTimerBlock;

public class RedstoneExtendedBlocks {
    public static final Block REDSTONE_TIMER = register("redstone_timer", new RedstoneTimerBlock(Block.Settings.create().strength(0.1f, 0.0f)));

    private static Block register(String id, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(RedstoneExtended.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registries.BLOCK, Identifier.of(RedstoneExtended.MOD_ID, id), block);
    }

    public static void registerBlocks() {
    }
}
