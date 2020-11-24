package de.thomas.utils.animation.particle;

import com.destroystokyo.paper.ParticleBuilder;
import de.thomas.utils.animation.particle.base.IParticleTask;
import de.thomas.utils.animation.particle.base.interfaces.ParticleOption;
import org.bukkit.Location;
import org.bukkit.Particle;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@ParticleOption(canPlayParallel = false, playAtLeast = true)
public class WaveParticleAnimation extends IParticleTask {

    double time = Math.PI / 4;

    public WaveParticleAnimation(Location location) {
        super(location);
    }

    @Override
    public void loop() {
        time = time + 0.1 * Math.PI;
        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
            double x = time * cos(theta);
            double y = 2 * Math.exp(-0.1 * time) * sin(time) + 1.5;
            double z = time * sin(theta);
            location.add(x, y, z);

            ParticleBuilder particleBuilder_Up_Down = new ParticleBuilder(Particle.FIREWORKS_SPARK);
            particleBuilder_Up_Down.location(location);
            particleBuilder_Up_Down.extra(0.001);
            particleBuilder_Up_Down.count(1);
            particleBuilder_Up_Down.allPlayers();
            particleBuilder_Up_Down.spawn();
            location.subtract(x, y, z);

            theta = theta + Math.PI / 64;

            x = time * cos(theta);
            y = 2 * Math.exp(-0.1 * time) * sin(time) + 1.5;
            z = time * sin(theta);
            location.add(x, y, z);

            ParticleBuilder particleBuilder2_Down_Up = new ParticleBuilder(Particle.SPELL_WITCH);
            particleBuilder2_Down_Up.location(location);
            particleBuilder2_Down_Up.count(1);
            particleBuilder2_Down_Up.extra(0.001);
            particleBuilder2_Down_Up.allPlayers();
            particleBuilder2_Down_Up.spawn();

            location.subtract(x, y, z);
        }
        if (time > 65) {
            stop();
        }
    }
}
