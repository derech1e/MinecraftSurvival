package de.thomas.listeners;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.InventoryBuilder;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.message.Message;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        Player player = event.getPlayer();
        int playerCount = player.getWorld().getPlayerCount();

        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
            if (!player.isSneaking()) {
                Location targetLocation = getCompassTargetLocation(player);
                player.setCompassTarget(targetLocation);
                int distance = (int) Math.round(targetLocation.distance(player.getLocation()));

                // particle effects for targetLocation
                ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
                particleBuilder.color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
                particleBuilder.count(1);
                double min = player.getLocation().getY() - 20;
                double max = player.getLocation().getY() + 20;

                int radius = 2;
                int particles = 40;
                float curve = 8;
                int strands = 5 + new Random().nextInt(10);
                double rotation = Math.PI / 3;

                Location location = getCompassTargetLocation(player);
                if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                    location = Variables.playerPortalLocationSpawnMap.get(player.getUniqueId());
                    location.setY(player.getLocation().getY());
                }

                for (double i = 1; i <= strands; i++) {
                    for (double j = 1; j <= particles; j++) {
                        float ratio = (float) j / particles;
                        double angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + rotation;
                        double x = Math.cos(angle) * ratio * radius;
                        double z = Math.sin(angle) * ratio * radius;

                        particleBuilder.location(location.clone().add(0, 15, 0).add(x, j / 10, z));
                        particleBuilder.receivers(player);
                        particleBuilder.spawn();
                    }
                }

                particleBuilder.color(255, 20, 80);
                for (double i = min; i < max; i = i + 0.25) {
                    location.setY(i);
                    do {
                        location.subtract(0, 1, 0);
                    } while (!location.getBlock().getType().isAir());
                    particleBuilder.location(location);
                    particleBuilder.receivers(player);
                    particleBuilder.spawn();
                }

                if (distance > 10)
                    player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel!", true).getMessageAsString());
                else {
                    double playerHeightToTarget = player.getLocation().getY() - targetLocation.getY();
                    if (playerHeightToTarget <= -4)
                        player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel! (Oben)", true).getMessageAsString());
                    else if (playerHeightToTarget >= 4)
                        player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel! (Unten)", true).getMessageAsString());
                    else
                        player.sendMessage(new Message("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel!", true).getMessageAsString());
                }
                return;
            }
            int inventorySize = Variables.calculateInventorySize(playerCount + 2);
            InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_COMPASS, inventorySize);

            ItemBuilder spawnItemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            spawnItemBuilder.setName(ChatColor.GOLD + "Spawnpunkt");
            spawnItemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=");
            inventoryBuilder.addItem(spawnItemBuilder.toItemStack());

            player.getWorld().getPlayers().stream().filter(playerToFilter -> playerToFilter != player).forEachOrdered(filteredPlayer -> {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(ChatColor.WHITE + filteredPlayer.getName());
                itemBuilder.setSkullTexture(filteredPlayer);
                inventoryBuilder.addItem(itemBuilder.toItemStack());
            });

            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            itemBuilder.setName(ChatColor.WHITE + "Einstellungen");
            itemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0=");
            inventoryBuilder.setItem(inventorySize - 1, itemBuilder.toItemStack());
            //Placeholder
            inventoryBuilder.setPlaceHolder(new ItemBuilder(Material.GRAY_STAINED_GLASS).setName("").toItemStack(), false);
            player.openInventory(inventoryBuilder.build());
        }
    }

    private Location getCompassTargetLocation(Player player) {
        if (Variables.targetCompassPlayers.get(player.getUniqueId()) == null)
            if (player.getWorld().getEnvironment() == World.Environment.NETHER)
                return Variables.playerPortalLocationSpawnMap.get(player.getUniqueId());
            else
                return player.getCompassTarget();

        return Variables.targetCompassPlayers.get(player.getUniqueId()).getLocation();
    }
}
