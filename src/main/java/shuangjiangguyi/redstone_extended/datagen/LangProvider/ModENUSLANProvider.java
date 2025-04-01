package shuangjiangguyi.redstone_extended.datagen.LangProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import shuangjiangguyi.redstone_extended.block.RedstoneExtendedBlocks;

import java.util.concurrent.CompletableFuture;

public class ModENUSLANProvider extends FabricLanguageProvider{
    public ModENUSLANProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(RedstoneExtendedBlocks.REDSTONE_TIMER, "Redstone Timer");
        translationBuilder.add("text.block.redstone_timer.use_text.show_need_to_wait_tick", "Need to wait tick:");
    }
}
