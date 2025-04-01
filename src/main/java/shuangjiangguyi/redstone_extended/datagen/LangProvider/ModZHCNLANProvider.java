package shuangjiangguyi.redstone_extended.datagen.LangProvider;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import shuangjiangguyi.redstone_extended.block.RedstoneExtendedBlocks;

import java.util.concurrent.CompletableFuture;

public class ModZHCNLANProvider extends FabricLanguageProvider{
    public ModZHCNLANProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(RedstoneExtendedBlocks.REDSTONE_TIMER, "红石计时器");
        translationBuilder.add("text.block.redstone_timer.use_text.show_need_to_wait_tick", "需要等待的刻：");
    }
}