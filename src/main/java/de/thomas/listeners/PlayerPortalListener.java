package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import org.bukkit.Location;
import org.bukkit.World;
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
            if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
                Variables.playerPortalLocationSpawnMap.put(event.getPlayer().getUniqueId(), event.getTo());
                MinecraftSurvival.getINSTANCE().configuration.updateNetherPortalLocationByPlayer(event.getPlayer(), event.getTo());
                Variables.targetCompassPlayers.put(event.getPlayer().getUniqueId(), null);
            }
        }
    }
}
