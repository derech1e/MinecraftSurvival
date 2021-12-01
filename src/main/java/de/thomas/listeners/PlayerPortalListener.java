package de.thomas.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author derech1e
 * @since 13.11.2021
 **/
public class PlayerPortalListener implements Listener {

    private final HashMap<UUID, Location> playerPortalLocationMap = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPortal(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            switch (event.getTo().getWorld().getEnvironment()) {
                case NETHER -> {
                    Location location = event.getFrom();
                    location.setYaw(location.getYaw() + 180);
                    location.add(0, 0.5, 0);
                    playerPortalLocationMap.put(event.getPlayer().getUniqueId(), location);
                }
                case NORMAL -> event.setTo(playerPortalLocationMap.getOrDefault(event.getPlayer().getUniqueId(), event.getTo()));
            }
        }
    }
}
