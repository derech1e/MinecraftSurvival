package de.thomas.listeners;

import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldSaveListener implements Listener {

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        Bukkit.broadcast(new Message(ChatColor.GRAY.toString() + ChatColor.ITALIC + "[Server: Saved the game]").getMessage());
    }
}
