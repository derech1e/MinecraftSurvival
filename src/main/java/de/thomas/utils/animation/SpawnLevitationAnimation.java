package de.thomas.utils.animation;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SpawnLevitationAnimation {

    private final Player targetPlayer;
    private int taskID_1, taskID_2;

    public SpawnLevitationAnimation(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public void start() {
        targetPlayer.setVelocity(new Vector(0, 15, 0));

        taskID_1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            for (Location location : getCircle(0.5 + Math.random(), 10)) {
                ParticleBuilder particleBuilder = new ParticleBuilder(Particle.CLOUD);
                particleBuilder.location(location);
                particleBuilder.force(true);
                particleBuilder.count(1);
                particleBuilder.extra(0.001);
                particleBuilder.allPlayers();
                particleBuilder.spawn();
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, 1, 1);
            }
        },1,1);


        taskID_2 = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftSurvival.getINSTANCE(), () -> {
            targetPlayer.teleport(ConfigCache.spawnLocation.clone().add(0, 1, 0));
            stop();
        }, 25);
    }

    private void stop() {
        Bukkit.getScheduler().cancelTask(taskID_1);
        Bukkit.getScheduler().cancelTask(taskID_2);
    }

    public @NotNull ArrayList<Location> getCircle(double radius, int amount) {
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = targetPlayer.getLocation().getX() + (radius * Math.cos(angle));
            double z = targetPlayer.getLocation().getZ() + (radius * Math.sin(angle));
            locations.add(new Location(targetPlayer.getLocation().getWorld(), x, targetPlayer.getLocation().getY(), z));
        }
        return locations;
    }
}
