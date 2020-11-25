package de.thomas.utils.animation.particle.base;

import de.thomas.utils.Variables;
import de.thomas.utils.animation.particle.base.interfaces.IParticleAnimationBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;


public abstract class IParticleTask implements IParticleAnimationBase {

    public final Location location;
    public int taskID;
    final int delay = 0;
    final int amount = 1;

    public IParticleTask(Location location) {
        this.location = location;
    }

    @Override
    public void start() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(INSTANCE, this::loop, delay, amount);
        Variables.activeTasks.add(this);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskID);
        Variables.activeTasks.remove(this);
    }
}
