package de.thomas.utils.animation.particle.base;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParticleAnimationHandler {

    private final HashMap<IParticleTask, ParticleOption> allAnimations = new HashMap<>();
    private final List<IParticleTask> animationAtLeast = new ArrayList<>();
    private final List<IParticleTask> parallelAnimations = new ArrayList<>();
    private int taskID;

    public ParticleAnimationHandler(IParticleTask... particleTasks) {
        for (IParticleTask particleTask : particleTasks) {
            ParticleOption particleOption = particleTask.getClass().getAnnotation(ParticleOption.class);
            allAnimations.put(particleTask, particleOption);
        }
    }

    private void sort() {
        parallelAnimations.addAll(allAnimations.entrySet().stream().filter(animation -> animation.getValue().canPlayParallel() && !animation.getValue().playAtLeast()).map(Map.Entry::getKey).collect(Collectors.toList()));
        animationAtLeast.addAll(allAnimations.entrySet().stream().filter(animation -> animation.getValue().playAtLeast()).map(Map.Entry::getKey).collect(Collectors.toList()));
    }

    public void start() {
        sort();
        parallelAnimations.forEach(IParticleTask::start);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            System.out.println("Checking for Task-2... " + Variables.activeTasks.size());
            if(Variables.activeTasks.size() == 0) {
                animationAtLeast.forEach(IParticleTask::start);
                Bukkit.getScheduler().cancelTask(taskID);
            }
        },0,1);
    }
}
