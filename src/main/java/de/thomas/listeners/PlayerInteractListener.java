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
                if(targetLocation == null) {
                    return;
                }
                player.setCompassTarget(targetLocation);

                spawnParticlesAtLocation(player, targetLocation.clone());

                int distance = (int) Math.round(targetLocation.distance(player.getLocation()));
                double distanceY = Math.round(player.getLocation().getY() - targetLocation.getY());
                System.out.println(distanceY);
                boolean isNegative = Double.doubleToRawLongBits(distanceY) < 0;
                player.sendMessage(new Message(String.format("Es sind noch " + ChatColor.GOLD + distance + ChatColor.WHITE + " Blöcke bis zum Ziel! " + ChatColor.GRAY + "%s", isNegative ? "▲" : distanceY == 0 ? "-" : "▼"), true).getRawMessageString());
                return;
            }
            int inventorySize = Variables.calculateInventorySize(playerCount + 2);
            InventoryBuilder inventoryBuilder = new InventoryBuilder(Variables.INVENTORY_NAME_COMPASS, inventorySize);

            ItemBuilder spawnItemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            spawnItemBuilder.setName(ChatColor.GOLD + "Spawnpunkt");
            spawnItemBuilder.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=");
            inventoryBuilder.addItem(spawnItemBuilder.toItemStack());

            player.getWorld().getPlayers().stream().filter(playerToFilter -> playerToFilter != player && playerToFilter == null).forEachOrdered(filteredPlayer -> {
                ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
                itemBuilder.setName(ChatColor.RESET + filteredPlayer.getName());
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

    private void spawnParticlesAtLocation(Player player, Location location) {

        // particle effects for targetLocation
        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
        particleBuilder.count(1);
        particleBuilder.color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        double min = player.getLocation().getY() - 20;
        double max = player.getLocation().getY() + 20;

        Location targetLocation = location.clone();

        // Beacon
        for (double i = min; i < max; i = i + 0.25) {
            targetLocation.setY(i);
            do {
                targetLocation.subtract(0, 1, 0);
            } while (!targetLocation.getBlock().getType().isAir());
            particleBuilder.location(targetLocation.clone().subtract(0,5,0));
            particleBuilder.receivers(player);
            particleBuilder.spawn();
        }

        // Cone
        int radius = 2;
        int particles = 40;
        float curve = 8;
        int strands = 5 + new Random().nextInt(10);
        double rotation = Math.PI / 3;

        for (double i = 1; i <= strands; i++) {
            for (double y = 1; y <= particles; y++) {
                float ratio = (float) y / particles;
                double angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + rotation;
                double x = Math.cos(angle) * ratio * radius;
                double z = Math.sin(angle) * ratio * radius;

                particleBuilder.location(targetLocation.clone().subtract(0,5,0).add(x, y / 10, z));
                particleBuilder.receivers(player);
                particleBuilder.spawn();
            }
        }
    }

    private Location getCompassTargetLocation(Player player) {
        Player targetPlayer = Variables.targetCompassPlayers.get(player.getUniqueId());
        switch (player.getWorld().getEnvironment()) {
            case NORMAL, THE_END -> {
                if (targetPlayer == null)
                    return player.getBedSpawnLocation();
                return targetPlayer.getLocation();
            }
            case NETHER -> {
                if (targetPlayer == null)
                    return Variables.playerPortalLocationSpawnMap.getOrDefault(player.getUniqueId(), player.getCompassTarget()); // TODO: Save player portal location
                return targetPlayer.getLocation();
            }
        }
        return player.getWorld().getSpawnLocation();
    }
}
