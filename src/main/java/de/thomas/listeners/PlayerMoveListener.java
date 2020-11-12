package de.thomas.listeners;

import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;

public class PlayerMoveListener implements Listener {


    @Deprecated
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Collection<Player> playerInGlideArea = player.getWorld().getNearbyEntitiesByType(Player.class, ConfigCache.glideAreaLocation, ConfigCache.glideAreaRadius);

        if (playerInGlideArea.contains(player))
            if (!player.isOnGround() && player.getFallDistance() >= 3) {
                Variables.glidingPlayers.add(player);
                if (ConfigCache.glideBoots)
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(2));
            }

        if (Variables.glidingPlayers.contains(player))
            if (player.isOnGround())
                Variables.glidingPlayers.remove(player);

        player.setGliding(Variables.glidingPlayers.contains(player));
    }
}
