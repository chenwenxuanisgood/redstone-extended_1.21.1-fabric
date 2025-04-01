package shuangjiangguyi.redstone_extended.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private void itemWithBlock(RecipeExporter exporter, Item item, Block block) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, item,
                RecipeCategory.BUILDING_BLOCKS, block);
    }

    @Override
    public void generate(RecipeExporter exporter) {
    }
}
