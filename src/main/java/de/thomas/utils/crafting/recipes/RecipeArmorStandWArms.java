package de.thomas.utils.crafting.recipes;

import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandWArms extends ShapedRecipe implements RecipeValues {

    public RecipeArmorStandWArms() {
        super(NamespacedKey.randomKey(), new ItemBuilder(Material.ARMOR_STAND).showArmorStandArms(true).toItemStack());
        initValues();

    }

    @Override
    public void initValues() {
        this.shape("XXX", "YZY", "XXX");
        this.setIngredient('X', Material.AIR);
        this.setIngredient('Y', Material.STICK);
        this.setIngredient('Z', Material.ARMOR_STAND);
    }
}
