package de.thomas.utils.threads.base;

import org.bukkit.Bukkit;

public abstract class IThreadBase {

    protected IThreadBase() {
    }

    protected int taskID;

    public void startThread() {
    }

    public void stopThread() {
        Bukkit.getScheduler().cancelTask(taskID);
        taskID = -1;
    }
}
