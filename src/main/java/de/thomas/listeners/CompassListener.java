package de.thomas.listeners;

import de.thomas.utils.builder.InventoryBuilder;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Comparator;

public class CompassListener implements Listener {

    private final String INVENTORY_NAME = ChatColor.GOLD + "Wähle einen Spieler";

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();
        int playerCount = player.getWorld().getPlayerCount();

        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
            if (!player.isSneaking()) {
                int distance = (int) Math.round(player.getCompassTarget().distance(player.getLocation()));
                if (distance > 20)
                    player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel!").getMessage());
                else {
                    double playerHeightToTarget = player.getLocation().getY() - player.getCompassTarget().getY();
                    System.out.println(playerHeightToTarget);
                    if (playerHeightToTarget <= -4)
                        player.sendMessage(new Message(ChatColor.RED + "Schau mal nach" + ChatColor.GREEN + " oben" + ChatColor.RED + ". Dein Ziel befindet sich über dir!").getMessage());
                    else if (playerHeightToTarget >= 4)
                        player.sendMessage(new Message(ChatColor.RED + "Schau mal nach" + ChatColor.GREEN + " unten" + ChatColor.RED + ". Dein Ziel befindet sich unter dir!").getMessage());
                    else
                        player.sendMessage(new Message(ChatColor.RED + "Schau mal nach um. Dein Ziel befindet sich auf " + ChatColor.GREEN + " gleicher Höhe" + ChatColor.RED + "!").getMessage());
                }
                return;
            }
            InventoryBuilder inventoryBuilder = new InventoryBuilder(INVENTORY_NAME, calculateInventorySize(playerCount + 1));

            ItemBuilder spawnItemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            spawnItemBuilder.setName(ChatColor.GREEN + "Spawnpunkt");
            spawnItemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=");
            inventoryBuilder.addItem(spawnItemBuilder.toItemStack());

            player.getWorld().getPlayers().stream().filter(playerToFilter -> playerToFilter != player).forEachOrdered(filteredPlayer -> {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(ChatColor.WHITE + filteredPlayer.getName());
                itemBuilder.setSkullTexture(filteredPlayer);
                inventoryBuilder.addItem(itemBuilder.toItemStack());
            });
            player.openInventory(inventoryBuilder.build());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (event.getView().getTitle().equals(INVENTORY_NAME)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getI18NDisplayName() == null)
                return;
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Location spawnLocation = player.getBedSpawnLocation() == null ? player.getWorld().getSpawnLocation() : player.getBedSpawnLocation();
            int distance = (int) Math.round(player.getLocation().distance(spawnLocation));
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Spawnpunkt")) {
                player.setCompassTarget(spawnLocation);
                player.sendMessage(new Message("Du hast deinen " + ChatColor.AQUA + "Spawnpunkt" + ChatColor.WHITE + " als neues Ziel gesetzt. (" + ChatColor.GOLD + distance + " Blöcke entfernt" + ChatColor.WHITE + ")").getMessage());
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

    private Player getClosestPlayerToPlayer(Player player) {
        return (Player) player.getWorld().getEntitiesByClasses(Player.class).stream().min(Comparator.comparingInt(p -> (int) p.getLocation().distance(player.getLocation()))).orElse(player);
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
