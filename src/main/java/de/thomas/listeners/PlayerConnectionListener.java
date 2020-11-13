package de.thomas.listeners;

import de.thomas.utils.animation.TitleAnimation;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());

        if(!player.hasPlayedBefore()) {
            player.setBedSpawnLocation(ConfigCache.spawnLocation, true);
            player.getWorld().setSpawnLocation(ConfigCache.spawnLocation);
            player.teleport(ConfigCache.spawnLocation);
        }
        new TitleAnimation(player).startFirstJoinAnimation();
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + event.getPlayer().getName());
    }
}
