package de.thomas.listeners;

import de.thomas.utils.config.ConfigCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            boolean isPlayerInSpawnArea = player.getLocation().distance(Objects.requireNonNull(ConfigCache.glideAreaLocation)) <= ConfigCache.glideAreaRadius;
            event.setCancelled(isPlayerInSpawnArea);
        }
    }


    @EventHandler
    public void onBlockBreak(@NotNull BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            boolean isPlayerInSpawnArea = player.getLocation().distance(Objects.requireNonNull(ConfigCache.glideAreaLocation)) <= ConfigCache.glideAreaRadius;
            event.setCancelled(isPlayerInSpawnArea);
        }
    }
}
