package de.thomas.utils.crafting.recipes.armorstand;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeArmorStandSmall extends ShapedRecipe {

    public RecipeArmorStandSmall() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(), "armorstand_small"),
                new ItemBuilder(Material.ARMOR_STAND)
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .addEnchantment(Enchantment.DURABILITY, 1, true)
                        .setArmorStandSmall(true)
                        .setName("§fSmall Armor Stand")
                        .toItemStack());
        initValues();
    }

    public void initValues() {
        this.shape("XXX", "XZX", "XXX");
        this.setIngredient('X', Material.GOLD_NUGGET);
        this.setIngredient('Z', new ItemBuilder(Material.ARMOR_STAND).toItemStack());
    }
}
