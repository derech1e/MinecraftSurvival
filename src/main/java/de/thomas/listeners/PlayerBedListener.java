package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.message.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerBedListener implements Listener {

    private final List<Player> playerInBed = new ArrayList<>();
    private BukkitTask taskID;
    private boolean inProgress = false;

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (!event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK))
            return;
            playerInBed.add(player);

        int countToSkip = (getPlayerCountToSkip() - playerInBed.size());
        Bukkit.broadcastMessage(new Message(ChatColor.GOLD + player.getName() + ChatColor.WHITE + " hat sich ins Bett gelegt." + (countToSkip != 0 ? "(Noch " + countToSkip + ")" : "")).getMessage());

        if (canSkip()) {
            taskID = Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> {
                player.getWorld().setTime(0);
                Bukkit.broadcastMessage(new Message(ChatColor.GREEN + "Guten Morgen...").getMessage());
                inProgress = true;
            }, 20 * 4);
        }

    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        if (!inProgress) {
            int countToSkip = (getPlayerCountToSkip() - playerInBed.size());
            Bukkit.broadcastMessage(new Message(ChatColor.GOLD + player.getName() + ChatColor.WHITE + " hat das Bett verlassen." + (countToSkip != 0 ? "(Noch " + countToSkip + ")" : "")).getMessage());
        }
        playerInBed.remove(player);

        if (!canSkip()) {
            taskID.cancel();
            inProgress = false;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(canSkip()) {
            player.getWorld().setTime(0);
            Bukkit.broadcastMessage(new Message(ChatColor.GREEN + "Guten Morgen...").getMessage());
            taskID.cancel();
            inProgress = false;
        }
    }

    private boolean canSkip() {
        return playerInBed.size() >= getPlayerCountToSkip();
    }

    private int getPlayerCountToSkip() {
        return (int) Math.max(Math.round(33.3 * Bukkit.getOnlinePlayers().size() / 100f), 1);
    }
}
