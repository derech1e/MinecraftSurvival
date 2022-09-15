package de.thomas.utils.crafting;

import de.thomas.utils.crafting.recipes.armorstand.RecipeArmorStandSmall;
import de.thomas.utils.crafting.recipes.armorstand.RecipeArmorStandSmallWArms;
import de.thomas.utils.crafting.recipes.armorstand.RecipeArmorStandWArms;
import de.thomas.utils.crafting.recipes.bread.RecipeBreadAsBaguette;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeManager {

    private static final List<ShapedRecipe> shapedRecipeList = new ArrayList<>();

    private static void init() {
        shapedRecipeList.add(new RecipeArmorStandWArms());
        shapedRecipeList.add(new RecipeArmorStandSmallWArms());
        shapedRecipeList.add(new RecipeArmorStandSmall());
        shapedRecipeList.add(new RecipeBreadAsBaguette());
    }

    public static void discoverRecipe(Player player) {
        player.discoverRecipes(shapedRecipeList.stream().map(ShapedRecipe::getKey).collect(Collectors.toList()));
    }

    public static void registerRecipes(Server server) {
        init();
        shapedRecipeList.forEach(server::addRecipe);
    }

    public static void unregisterRecipes(Server server) {
        shapedRecipeList.stream().map(ShapedRecipe::getKey).forEach(server::removeRecipe);
    }
}
