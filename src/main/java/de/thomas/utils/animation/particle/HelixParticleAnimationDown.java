package de.thomas.utils.animation.particle;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.utils.animation.particle.base.IParticleTask;
import de.thomas.utils.animation.particle.base.interfaces.ParticleOption;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.Random;

@ParticleOption(canPlayParallel = true)
public class HelixParticleAnimationDown extends IParticleTask {

    double time;
    double radius;
    final double radiusEnd;

    public HelixParticleAnimationDown(Location location, double radius, double radiusEnd, double time) {
        super(location);
        this.radius = radius;
        this.radiusEnd = radiusEnd;
        this.time = time;
    }

    @Override
    public void loop() {
        time = time + Math.PI / 64;
        double x = radius * Math.cos(time);
        double y = 0.5 * time;
        double z = radius * Math.sin(time);
        location.subtract(x, y, z);
        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.REDSTONE);
        particleBuilder.location(location);
        int blue = (int) (radius * 30);
        int green = (int) -(-255 + (radius * 30));
        particleBuilder.color(Color.fromRGB(0, green > 255 ? 255 : Math.max(green, 0), blue > 255 ? 255 : Math.max(blue, 0)), new Random().nextInt(5));
        particleBuilder.count(new Random().nextInt(5));
        particleBuilder.offset(0.2, 0, 0.2);
        particleBuilder.allPlayers();
        particleBuilder.spawn();
        location.add(x, y, z);
        radius = radius + 0.01;
        if (radius >= radiusEnd) {
            stop();
        }
    }
}