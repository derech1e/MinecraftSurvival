package de.thomas.utils.animation;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class WorldBorderAnimation {

    private final Player targetPlayer;
    private BukkitTask taskID = null;
    private int time;
    private int size;

    public WorldBorderAnimation(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.size = 15;
        this.time = 5;
    }

    public WorldBorderAnimation(Player targetPlayer, int time, int size) {
        this.targetPlayer = targetPlayer;
        this.time = time;
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWorldBorderInTime() {
        WorldBorder border = targetPlayer.getWorld().getWorldBorder();
        border.setSize(size, time);
    }

    public void openWorldBorderFullDelayed() {
        int time = 5;
        WorldBorder border = targetPlayer.getWorld().getWorldBorder();
        border.setCenter(ConfigCache.spawnLocation);
        border.setSize(500, time);

        if (taskID == null)
            taskID = Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> border.setSize(60000000), 20 * time);
    }

    public void reset() {
        WorldBorder border = targetPlayer.getWorld().getWorldBorder();
        border.setCenter(ConfigCache.spawnLocation);
        border.setSize(1);
    }
}
