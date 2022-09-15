package de.thomas.utils.crafting.recipes.armorstand;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandWArms extends ShapedRecipe {

    public RecipeArmorStandWArms() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(), "armorstand_with_arms"),
                new ItemBuilder(Material.ARMOR_STAND)
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .addEnchantment(Enchantment.DURABILITY, 1, true)
                        .showArmorStandArms(true)
                        .setName("§fArmor Stand with Arms")
                        .toItemStack());
        initValues();
    }

    public void initValues() {
        this.shape("   ", "YZY", "   ");
        this.setIngredient('Y', Material.STICK);
        this.setIngredient('Z', new ItemBuilder(Material.ARMOR_STAND).toItemStack());
    }
}
