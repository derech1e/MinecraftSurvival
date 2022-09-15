package de.thomas.utils.crafting.recipes.armorstand;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandSmallWArms extends ShapedRecipe {

    public RecipeArmorStandSmallWArms() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(), "armorstand_small_with_arms"),
                new ItemBuilder(Material.ARMOR_STAND)
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .addEnchantment(Enchantment.DURABILITY, 1, true)
                        .showArmorStandArms(true)
                        .setArmorStandSmall(true)
                        .setName("§fSmall Armor Stand with Arms")
                        .toItemStack());
        initValues();
    }

    public void initValues() {
        this.shape("XXX", "YZY", "XXX");
        this.setIngredient('X', Material.GOLD_NUGGET);
        this.setIngredient('Y', Material.STICK);
        this.setIngredient('Z', new ItemBuilder(Material.ARMOR_STAND).toItemStack());
    }
}
