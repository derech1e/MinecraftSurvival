package de.thomas.utils.animation;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ParticleAnimation {

    double t = 0;
    double t2 = 0;
    double t3 = Math.PI / 4;
    double r = 8;
    double r2 = 0;
    private Location location;
    private Location location2;
    private Location location3;
    private int taskID;
    private int taskID1;
    private int taskID3;

    public ParticleAnimation(Location location) {
        this.location = location;
        this.location2 = new Location(Bukkit.getWorld("world"), location.getX(), location.getY(), location.getZ());
        this.location3 = new Location(Bukkit.getWorld("world"), location.getX(), location.getY(), location.getZ());

    }

    public void start() {
        loop1();
        loop2();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    private void loop1() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            t = t + Math.PI / 64;
            double x = r * Math.cos(t);
            double y = 0.5 * t;
            double z = r * Math.sin(t);
            location.add(x, y, z);
            ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
            particleBuilder.location(location);
            particleBuilder.color(Color.fromRGB((int) (r * 30), (int) -(-255 + (r * 30)), 0), 2);
            particleBuilder.count(5);
            particleBuilder.offset(0.2, 0, 0.2);
            particleBuilder.allPlayers();
            particleBuilder.spawn();
            location.subtract(x, y, z);
            r = r - 0.01;
            if (r <= 0) {
                stop();
            }
        }, 0, 1);
    }

    private void loop2() {
        location2.add(0, 20, 0);
        taskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            t2 = t2 + Math.PI / 64;
            double x = r2 * Math.cos(t2);
            double y = 0.5 * t2;
            double z = r2 * Math.sin(t2);
            location2.subtract(x, y, z);
            ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
            particleBuilder.location(location2);
            particleBuilder.color(Color.fromRGB(0, (int) -(-255 + (r * 30)), (int) (r * 30)), 2);
            particleBuilder.count(5);
            particleBuilder.offset(0.2, 0, 0.2);
            particleBuilder.allPlayers();
            particleBuilder.spawn();
            location2.add(x, y, z);
            r2 = r2 + 0.01;
            if (r2 >= 8.5) {
                Bukkit.getScheduler().cancelTask(taskID1);
                loop3(5);

            }
        }, 0, 1);
    }

    private void loop3(int height) {
        new BukkitRunnable(){
            double t = Math.PI/4;
            Location loc = location3.add(0,height,0);
            public void run(){
                t = t + 0.1*Math.PI;
                for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                    double x = t*cos(theta);
                    double y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    double z = t*sin(theta);
                    loc.add(x,y,z);
                    ParticleBuilder particleBuilder = new ParticleBuilder(Particle.FIREWORKS_SPARK);
                    particleBuilder.location(loc);
                    particleBuilder.extra(0.001);
                    particleBuilder.count(1);
                    particleBuilder.allPlayers();
                    particleBuilder.spawn();
                    loc.subtract(x,y,z);

                    theta = theta + Math.PI/64;

                    x = t*cos(theta);
                    y = 2*Math.exp(-0.1*t) * sin(t) + 1.5;
                    z = t*sin(theta);
                    loc.add(x,y,z);
                    ParticleBuilder particleBuilder2 = new ParticleBuilder(Particle.SPELL_WITCH);
                    particleBuilder2.location(loc);
                    particleBuilder2.count(1);
                    particleBuilder2.extra(0.001);
                    particleBuilder2.allPlayers();
                    particleBuilder2.spawn();
                    loc.subtract(x,y,z);
                }
                if (t > 65){
                    this.cancel();
                }
            }

        }.runTaskTimer(MinecraftSurvival.getINSTANCE(), 0, 1);
    }
}
