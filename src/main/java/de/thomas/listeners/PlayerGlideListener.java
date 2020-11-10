package de.thomas.listeners;

import de.thomas.utils.config.ConfigCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerGlideListener implements Listener {

    private final List<Player> glidingPlayers = new ArrayList<>();

    @Deprecated
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Collection<Player> playerInGlideArea = player.getWorld().getNearbyEntitiesByType(Player.class, ConfigCache.glideAreaLocation, ConfigCache.glideAreaRadius);

        if (playerInGlideArea.contains(player))
            if (!player.isOnGround() && player.getFallDistance() >= 3) {
                glidingPlayers.add(player);
                if (ConfigCache.glideBoots)
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(2));
            }

        if (glidingPlayers.contains(player))
            if (player.isOnGround())
                glidingPlayers.remove(player);

        player.setGliding(glidingPlayers.contains(player));
    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            if (glidingPlayers.contains(player))
                event.setCancelled(true);


    }
}
