package de.thomas.utils.crafting.recipes.bread;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.builder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;


public class RecipeBreadAsBaguette extends ShapedRecipe {

    public static int counter = -1;

    public RecipeBreadAsBaguette() {
        super(new NamespacedKey(MinecraftSurvival.getINSTANCE(), "baguette"), new ItemBuilder(Material.BREAD)
                .setName(String.format("§fBaguette #%s", counter))
                .setCustomModelData(1)
                .toItemStack());
        initValues();
    }

    public void initValues() {
        this.shape("  X", " X ", "X  ");
        this.setIngredient('X', Material.BREAD);
    }


}
