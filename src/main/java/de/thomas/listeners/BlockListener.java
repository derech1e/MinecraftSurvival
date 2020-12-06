package de.thomas.listeners;

import de.thomas.utils.config.ConfigCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        boolean isPlayerInSpawnArea = player.getLocation().distance(ConfigCache.glideAreaLocation) <= ConfigCache.glideAreaRadius;
        event.setCancelled(isPlayerInSpawnArea);
    }


    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        boolean isPlayerInSpawnArea = player.getLocation().distance(ConfigCache.glideAreaLocation) <= ConfigCache.glideAreaRadius;
        event.setCancelled(isPlayerInSpawnArea);
    }
}
