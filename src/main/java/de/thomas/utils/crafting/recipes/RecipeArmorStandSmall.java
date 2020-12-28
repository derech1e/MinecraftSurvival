package de.thomas.utils.crafting.recipes;

import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandSmall extends ShapedRecipe implements RecipeValues {

    public RecipeArmorStandSmall() {
        super(NamespacedKey.randomKey(), new ItemBuilder(Material.ARMOR_STAND).setArmorStandSmall(true).toItemStack());
        initValues();
    }

    @Override
    public void initValues() {
        this.shape("XXX", "XZX", "XXX");
        this.setIngredient('X', Material.GOLD_NUGGET);
        this.setIngredient('Z', Material.ARMOR_STAND);
    }
}
