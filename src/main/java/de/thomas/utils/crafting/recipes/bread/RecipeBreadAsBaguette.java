package de.thomas.utils.crafting.recipes.bread;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;


public class RecipeBreadAsBaguette extends ShapedRecipe {

    public RecipeBreadAsBaguette() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(), "baguette"), new ItemBuilder(Material.BREAD)
                .setName("§fBaguette")
                .setCustomModelData(1)
                .addEnchantment(Enchantment.KNOCKBACK, 2, true)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .toItemStack());
        initValues();
    }
    public void initValues() {
        this.shape("  X", " X ", "X  ");
        this.setIngredient('X', Material.BREAD);
    }


}
