package de.thomas.listeners;

import de.thomas.utils.crafting.RecipeManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getRecipe().getResult().equals(new ItemStack(Material.ARMOR_STAND))) {
            RecipeManager.discoverRecipe((Player) event.getWhoClicked());
        }
    }
}
