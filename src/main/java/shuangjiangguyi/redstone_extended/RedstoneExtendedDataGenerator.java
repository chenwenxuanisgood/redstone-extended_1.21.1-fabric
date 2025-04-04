package shuangjiangguyi.redstone_extended;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import shuangjiangguyi.redstone_extended.datagen.LangProvider.*;
import shuangjiangguyi.redstone_extended.datagen.*;

public class RedstoneExtendedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModItemTagsProvider::new);

		pack.addProvider(ModENUSLANProvider::new);
		pack.addProvider(ModZHCNLANProvider::new);

		pack.addProvider(ModRecipesProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelsProvider::new);
	}
}
