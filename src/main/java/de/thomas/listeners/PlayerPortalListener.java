package de.thomas.listeners;

import de.thomas.utils.Variables;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author derech1e
 * @since 13.11.2021
 **/
public class PlayerPortalListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPortal(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            switch (event.getTo().getWorld().getEnvironment()) {
                case NETHER -> {
                    Location location = event.getFrom();
                    location.setYaw(location.getYaw() + 180);
                    location.add(0, 0.5, 0);
                    Variables.playerPortalLocationMap.put(event.getPlayer().getUniqueId(), location);
                    Variables.playerPortalLocationSpawnMap.put(event.getPlayer().getUniqueId(), event.getTo());
                    Variables.targetCompassPlayers.put(event.getPlayer().getUniqueId(), null);
                    event.getPlayer().setCompassTarget(location);
                }
                case NORMAL ->
                        event.setTo(Variables.playerPortalLocationMap.getOrDefault(event.getPlayer().getUniqueId(), event.getTo()));
            }
        }
    }
}
