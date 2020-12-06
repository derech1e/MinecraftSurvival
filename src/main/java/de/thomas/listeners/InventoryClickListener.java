package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (event.getView().getTitle().equals(Variables.INVENTORY_NAME_COMPASS)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getI18NDisplayName() == null)
                return;
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Location spawnLocation = player.getBedSpawnLocation() == null ? player.getWorld().getSpawnLocation() : player.getBedSpawnLocation();
            int distance = (int) Math.round(player.getLocation().distance(spawnLocation));
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Spawnpunkt")) {
                player.setCompassTarget(spawnLocation);
                player.sendMessage(new Message("Du hast deinen " + ChatColor.GOLD + "Spawnpunkt" + ChatColor.WHITE + " als neues Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt" + ChatColor.WHITE + ")").getMessage());
                player.closeInventory();
                return;
            }

            Player targetPlayer = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName().replace(String.valueOf(ChatColor.WHITE), ""));
            assert targetPlayer != null;
            distance = (int) Math.round(player.getLocation().distance(targetPlayer.getLocation()));
            player.setCompassTarget(targetPlayer.getLocation());
            player.sendMessage(new Message("Du hast " + ChatColor.AQUA + targetPlayer.getName() + ChatColor.WHITE + " als dein neues Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt " + ChatColor.WHITE + ")").getMessage());
            targetPlayer.sendMessage(new Message(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " hat dich als sein neues Compass-Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt " + ChatColor.WHITE + ")").getMessage());
            player.closeInventory();
        }
    }
}
