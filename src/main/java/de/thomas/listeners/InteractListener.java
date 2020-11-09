package de.thomas.listeners;

import de.thomas.utils.InventoryBuilder;
import de.thomas.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Comparator;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();

        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
            InventoryBuilder inventoryBuilder = new InventoryBuilder("§6Wähle einen Spieler", calculateInventorySize(player.getWorld().getPlayerCount()));

            player.getWorld().getPlayers().stream().filter(playerToFilter -> playerToFilter != player).forEachOrdered(filteredPlayer -> {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(filteredPlayer.getName());
                itemBuilder.setSkullTexture(filteredPlayer);
                inventoryBuilder.addItem(itemBuilder.toItemStack());
            });

            player.openInventory(inventoryBuilder.build());

            //player.setCompassTarget(getClosestPlayerToPlayer(player).getLocation());
        }
    }

    private Player getClosestPlayerToPlayer(Player player) {
        return (Player) player.getWorld().getEntitiesByClasses(Player.class).stream().min(Comparator.comparingInt(p -> (int) p.getLocation().distance(player.getLocation()))).orElse(player);
    }

    private int calculateInventorySize(int playerSize) {
        if (playerSize <= 9)
            return 9;
        else if (playerSize <= 18)
            return 18;
        else if (playerSize <= 27)
            return 27;
        else if (playerSize <= 36)
            return 36;
        else if (playerSize <= 45)
            return 45;
        else
            return 54;
    }
}
