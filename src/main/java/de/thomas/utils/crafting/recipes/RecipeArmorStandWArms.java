package de.thomas.utils.crafting.recipes;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandWArms extends ShapedRecipe implements RecipeValues {

    public RecipeArmorStandWArms() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(),"armorstand_with_arms"), new ItemBuilder(Material.ARMOR_STAND).showArmorStandArms(true).setName("§fArmor Stand with Arms").toItemStack());
        initValues();

    }

    @Override
    public void initValues() {
        this.shape("   ", "YZY", "   ");
        this.setIngredient('Y', Material.STICK);
        this.setIngredient('Z', Material.ARMOR_STAND);
    }
}
