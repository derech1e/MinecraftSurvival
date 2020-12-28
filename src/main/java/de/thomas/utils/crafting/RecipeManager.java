package de.thomas.utils.crafting;

import de.thomas.utils.crafting.recipes.RecipeArmorStandSmall;
import de.thomas.utils.crafting.recipes.RecipeArmorStandSmallWArms;
import de.thomas.utils.crafting.recipes.RecipeArmorStandWArms;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    private static final List<ShapedRecipe> shapedRecipeList = new ArrayList<>();

    private static void init() {
        shapedRecipeList.add(new RecipeArmorStandWArms());
        shapedRecipeList.add(new RecipeArmorStandSmallWArms());
        shapedRecipeList.add(new RecipeArmorStandSmall());
    }

    public static void registerRecipes(Server server) {
        init();
        shapedRecipeList.forEach(server::addRecipe);
    }
}
