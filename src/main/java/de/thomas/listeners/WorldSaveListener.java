package de.thomas.listeners;

import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.Date;

public class WorldSaveListener implements Listener {

    private static double lastTime = -1;

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        if (event.getWorld().getName().equals("world") && (lastTime + 60000 * 30) <= new Date().getTime()) {
            Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(player -> new Message(ChatColor.GRAY.toString() + ChatColor.ITALIC + "[Server: Saved the game]").getMessage());
            lastTime = new Date().getTime();
        }
    }
}
