package de.thomas.utils.threads.base;

import org.bukkit.Bukkit;

public abstract class IThreadBase {

    protected int taskID;
    private boolean isRunning;
    protected IThreadBase() {
    }

    public void startThread() {
        if (isRunning)
            stopThread();
        isRunning = true;
    }

    public void stopThread() {
        Bukkit.getScheduler().cancelTask(taskID);
        isRunning = false;
    }
}
