package de.thomas.listeners;

import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.crafting.recipes.bread.RecipeBreadAsBaguette;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerEatListener implements Listener {

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == new ItemBuilder(Material.BREAD)
                .setName(String.format("§fBaguette #%s", RecipeBreadAsBaguette.counter))
                .setCustomModelData(1)
                .toItemStack()) {
            player.setFoodLevel(20);
            event.setItem(null);
            player.updateInventory();
        }

    }
}
