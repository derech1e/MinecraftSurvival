package de.thomas.utils.crafting.recipes;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandSmallWArms extends ShapedRecipe implements RecipeValues {

    public RecipeArmorStandSmallWArms() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(),"armorstand_small_with_arms"), new ItemBuilder(Material.ARMOR_STAND).showArmorStandArms(true).setArmorStandSmall(true).setName("§fSmall Armor Stand with Arms").toItemStack());
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
