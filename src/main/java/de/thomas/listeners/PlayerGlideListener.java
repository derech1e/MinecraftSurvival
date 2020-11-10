package de.thomas.listeners;

import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerGlideListener implements Listener {

    private List<Player> glidingPlayers = new ArrayList<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Collection<Player> playerInGlideArea = player.getWorld().getNearbyEntitiesByType(Player.class, ConfigCache.glideAreaLocation, ConfigCache.glideAreaRadius);

        if (playerInGlideArea.contains(player)) {
            if (!player.isOnGround() && player.getFallDistance() >= 3)
                glidingPlayers.add(player);

        }
        if (glidingPlayers.contains(player))
            if (player.isOnGround())
                glidingPlayers.remove(player);

        player.setGliding(glidingPlayers.contains(player));
    }
}
