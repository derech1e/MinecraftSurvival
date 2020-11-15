package de.thomas.utils.animation.particle;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.utils.Variables;
import de.thomas.utils.animation.particle.base.IParticleTask;
import de.thomas.utils.animation.particle.base.ParticleOption;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.Random;

@ParticleOption(canPlayParallel = true)
public class HelixParticleAnimationUp extends IParticleTask {

    double time;
    double radius;
    double radiusEnd;
    double amount_p;

    public HelixParticleAnimationUp(Location location, int radius, double radiusEnd, double time, int amount) {
        super(location);
        this.radius = radius;
        this.radiusEnd = radiusEnd;
        this.time = time;
        this.amount_p = amount;
    }

    @Override
    public void loop() {
        time = time + Math.PI / amount_p;
        double x = radius * Math.cos(time);
        double y = 0.5 * time;
        double z = radius * Math.sin(time);
        location.add(x, y, z);
        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
        particleBuilder.location(location);
        int red = (int) (radius * 30);
        int green = (int) -(-255 + (radius * 30));
        particleBuilder.color(Color.fromRGB(red > 255 ? 255 : Math.max(red, 0), green > 255 ? 255 : Math.max(green, 0), 0), new Random().nextInt(5));
        particleBuilder.count(new Random().nextInt(5));
        particleBuilder.offset(0.2, 0, 0.2);
        particleBuilder.allPlayers();
        particleBuilder.spawn();
        location.subtract(x, y, z);
        radius = radius - 0.01;
        if (radius <= radiusEnd) {
            stop();
        }
    }
}
