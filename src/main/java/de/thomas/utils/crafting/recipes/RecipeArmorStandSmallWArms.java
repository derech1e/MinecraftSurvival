package de.thomas.utils.crafting.recipes;

import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandSmallWArms extends ShapedRecipe implements RecipeValues {

    public RecipeArmorStandSmallWArms() {
        super(NamespacedKey.randomKey(), new ItemBuilder(Material.ARMOR_STAND).showArmorStandArms(true).setArmorStandSmall(true).toItemStack());
        initValues();
    }

    @Override
    public void initValues() {
        this.shape("XXX", "YZY", "XXX");
        this.setIngredient('X', Material.GOLD_NUGGET);
        this.setIngredient('Y', Material.STICK);
        this.setIngredient('Z', Material.ARMOR_STAND);
    }
}
