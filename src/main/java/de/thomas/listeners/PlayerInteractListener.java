package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.builder.InventoryBuilder;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.config.ConfigCache;
import de.thomas.utils.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();
        int playerCount = player.getWorld().getPlayerCount();

        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
            if (!player.isSneaking()) {
                int distance;

                if (Variables.targetCompassPlayers.get(player.getUniqueId()) == null)
                    distance = (int) Math.round(player.getCompassTarget().distance(player.getLocation()));
                else
                    distance = (int) Math.round(Variables.targetCompassPlayers.get(player.getUniqueId()).getLocation().distance(player.getLocation()));

                if (distance > 10)
                    player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel!").getMessage());
                else {
                    double playerHeightToTarget = player.getLocation().getY() - player.getCompassTarget().getY();
                    if (playerHeightToTarget <= -4)
                        player.sendMessage(new Message("Schau mal nach" + ChatColor.GOLD + " oben" + ChatColor.WHITE + ". Dein Ziel befindet sich über dir!").getMessage());
                    else if (playerHeightToTarget >= 4)
                        player.sendMessage(new Message("Schau mal nach" + ChatColor.GOLD + " unten" + ChatColor.WHITE + ". Dein Ziel befindet sich unter dir!").getMessage());
                    else
                        player.sendMessage(new Message("Schau mal nach um. Dein Ziel befindet sich auf " + ChatColor.GOLD + " gleicher Höhe" + ChatColor.WHITE + "!").getMessage());
                }
                return;
            }
            InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_COMPASS, calculateInventorySize(playerCount + 2));

            ItemBuilder spawnItemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            spawnItemBuilder.setName(ChatColor.GOLD + "Spawnpunkt");
            spawnItemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=");
            inventoryBuilder.addItem(spawnItemBuilder.toItemStack());

            ItemBuilder wayPointItemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            wayPointItemBuilder.setName(ChatColor.GOLD + "Wegpunkt");
            wayPointItemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZWE5NzdhZWViYTFjODM3NjY5NDEzYjg4Yzk1YzI3ZDA4ZmI0MjlmM2RmZmI0MzFhOGZhYjM2MWE5ZiJ9fX0=");
            if (ConfigCache.playerWaypoints.containsKey(player.getUniqueId())) {
                Location location = ConfigCache.playerWaypoints.get(player.getUniqueId());
                if (location.getWorld().getName().equalsIgnoreCase(player.getWorld().getName()))
                    inventoryBuilder.addItem(wayPointItemBuilder.toItemStack());
            }

            player.getWorld().getPlayers().stream().filter(playerToFilter -> playerToFilter != player).forEachOrdered(filteredPlayer -> {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(ChatColor.WHITE + filteredPlayer.getName());
                itemBuilder.setSkullTexture(filteredPlayer);
                inventoryBuilder.addItem(itemBuilder.toItemStack());
            });
            player.openInventory(inventoryBuilder.build());
        }
    }

    private int calculateInventorySize(int onlinePlayers) {
        if (onlinePlayers <= 9)
            return 9;
        else if (onlinePlayers <= 18)
            return 18;
        else if (onlinePlayers <= 27)
            return 27;
        else if (onlinePlayers <= 36)
            return 36;
        else if (onlinePlayers <= 45)
            return 45;
        else
            return 54;
    }
}
