package de.thomas.utils.animation.particle.base;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.animation.particle.base.interfaces.FinalizedAnimation;
import de.thomas.utils.animation.particle.base.interfaces.ParticleOption;
import de.thomas.utils.animation.particle.base.interfaces.IParticleHandlerBase;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParticleAnimationHandler implements IParticleHandlerBase {

    private final List<IParticleTask> animationAtLeast = new ArrayList<>();
    private final List<IParticleTask> parallelAnimations = new ArrayList<>();
    private final HashMap<IParticleTask, ParticleOption> allAnimations = new HashMap<>();
    private int taskID;
    FinalizedAnimation finalized;

    public ParticleAnimationHandler() {}

    public ParticleAnimationHandler(FinalizedAnimation finalized) {
        this(finalized, (IParticleTask) null);
    }

    public ParticleAnimationHandler(IParticleTask... particleTasks) {
        addAnimations(particleTasks);
        finalized = null;
    }

    public ParticleAnimationHandler(FinalizedAnimation finalized, IParticleTask... particleTasks) {
        addAnimations(particleTasks);
        this.finalized = finalized;
    }

    @Override
    public void addAnimations(IParticleTask... particleTasks) {
        for (IParticleTask particleTask : particleTasks) {
            ParticleOption particleOption = particleTask.getClass().getAnnotation(ParticleOption.class);
            allAnimations.put(particleTask, particleOption);
        }
    }

    @Override
    public void onFinal(FinalizedAnimation finalized) {
        this.finalized = finalized;
        stop();
    }

    private void sort() {
        parallelAnimations.addAll(allAnimations.entrySet().stream().filter(animation -> animation.getValue().canPlayParallel() && !animation.getValue().playAtLeast()).map(Map.Entry::getKey).collect(Collectors.toList()));
        animationAtLeast.addAll(allAnimations.entrySet().stream().filter(animation -> animation.getValue().playAtLeast()).map(Map.Entry::getKey).collect(Collectors.toList()));
    }

    @Override
    public void start() {
        sort();
        parallelAnimations.forEach(IParticleTask::start);
        parallelAnimations.clear();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            if (Variables.activeTasks.size() == 0) {
                animationAtLeast.forEach(IParticleTask::start);
                animationAtLeast.clear();
                finalized.onFinal();
            }
        }, 0, 1);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        finalized = null;
    }

    public void forceStop() {
        allAnimations.forEach((iParticleTask, particleOption) -> iParticleTask.stop());
    }
}
