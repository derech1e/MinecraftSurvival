package de.thomas.utils.animation.particle.base;


import de.thomas.minecraftsurvival.MinecraftSurvival;

public interface IParticleAnimationBase {

    MinecraftSurvival INSTANCE = MinecraftSurvival.getINSTANCE();

    void start();

    void loop();

    void stop();
}
