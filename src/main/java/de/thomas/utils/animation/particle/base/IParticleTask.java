package de.thomas.utils.animation.particle.base;

import de.thomas.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;


public abstract class IParticleTask implements IParticleAnimationBase {

    public Location location;
    public int taskID, taskID_Sound;
    int delay = 0, amount = 1;

    public IParticleTask(Location location) {
        this.location = location;
    }

    @Override
    public void start() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(INSTANCE, this::loop, delay, amount);
        this.taskID_Sound = Bukkit.getScheduler().scheduleSyncRepeatingTask(INSTANCE, () -> Bukkit.getServer().getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ITEM_ELYTRA_FLYING, 1, 0)),20,20);
        Variables.activeTasks.add(this);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskID);
        Variables.activeTasks.remove(this);
    }
}
