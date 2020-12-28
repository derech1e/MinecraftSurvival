package de.thomas.utils.crafting;

import de.thomas.utils.crafting.recipes.RecipeArmorStandSmall;
import de.thomas.utils.crafting.recipes.RecipeArmorStandSmallWArms;
import de.thomas.utils.crafting.recipes.RecipeArmorStandWArms;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeManager {

    private static final List<ShapedRecipe> shapedRecipeList = new ArrayList<>();

    private static void init() {
        shapedRecipeList.add(new RecipeArmorStandWArms());
        shapedRecipeList.add(new RecipeArmorStandSmallWArms());
        shapedRecipeList.add(new RecipeArmorStandSmall());
    }

    public static void discoverRecipe(@NotNull Player player) {
        player.discoverRecipes(shapedRecipeList.stream().map(ShapedRecipe::getKey).collect(Collectors.toList()));
    }

    public static void registerRecipes(@NotNull Server server) {
        init();
        shapedRecipeList.forEach(server::addRecipe);
    }
}
