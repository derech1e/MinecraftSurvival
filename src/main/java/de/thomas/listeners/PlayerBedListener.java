package de.thomas.listeners;

import de.thomas.MinecraftSurvival;
import de.thomas.utils.message.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerBedListener implements Listener {

    private final List<UUID> playerInBed = new ArrayList<>();
    private BukkitTask taskID;
    private boolean inProgress = false;

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK))
            return;
        playerInBed.add(player.getUniqueId());

        int countToSkip = getPlayerCountToSkip() - playerInBed.size();
        broadcastToWorld(new Message(ChatColor.GOLD + player.getName() + ChatColor.WHITE + " hat sich ins Bett gelegt." + (countToSkip > 0 ? " (Noch " + countToSkip + ")" : ""), true).getMessage());

        if (canSkip()) {
            skipNight(false, player);
        }
    }

    private void skipNight(boolean instant, Player player) {
        taskID = Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> {
            inProgress = true;
            if (!(player.getWorld().getTime() > 23850 || player.getWorld().getTime() < 12300)) {
                player.getWorld().setTime(0);
                broadcastToWorld(new Message(ChatColor.GREEN + "Guten Morgen :)", true).getMessage());
            }
            if (!player.getWorld().isClearWeather() || player.getWorld().isThundering()) {
                player.getWorld().setClearWeatherDuration(new Random().nextInt((minToTicks(80) - minToTicks(30) + 1)) + minToTicks(30));
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftSurvival.getINSTANCE(), () -> inProgress = false, 20 * 2);
        }, instant ? 1 : 20 * 4);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        playerInBed.remove(player.getUniqueId());
        if (!canSkip()) {
            if (taskID != null)
                taskID.cancel();
        }

        if (!inProgress) {
            int countToSkip = (getPlayerCountToSkip() - playerInBed.size());
            broadcastToWorld(new Message(ChatColor.GOLD + player.getName() + ChatColor.WHITE + " hat das Bett verlassen." + (countToSkip > 0 ? " (Noch " + countToSkip + ")" : ""), false).getMessage());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerInBed.remove(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> {
            if (canSkip()) {
                skipNight(true, player);
            }
        }, 20);
    }

    private boolean canSkip() {
        return playerInBed.size() >= getPlayerCountToSkip();
    }

    private int getPlayerCountToSkip() {
        return Math.max(Math.round(50 * Bukkit.getWorld("world").getPlayers().size() / 100f), 1);
    }

    private int minToTicks(int min) {
        return min * 60 * 20;
    }

    private void broadcastToWorld(Component message) {
        Bukkit.getWorld("world").getPlayers().forEach(player -> player.sendMessage(message));
    }
}