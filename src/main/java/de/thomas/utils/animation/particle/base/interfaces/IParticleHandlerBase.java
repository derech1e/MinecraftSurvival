package de.thomas.utils.animation.particle.base.interfaces;

import de.thomas.utils.animation.particle.base.IParticleTask;

public interface IParticleHandlerBase {

    void addAnimations(IParticleTask... particleTasks);
    void onFinal(FinalizedAnimation finalized);
    void start();
}
