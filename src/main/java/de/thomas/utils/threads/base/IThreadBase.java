package de.thomas.utils.threads.base;

import org.bukkit.Bukkit;

public abstract class IThreadBase {

    protected IThreadBase() {
        startThread();
    }

    protected int taskID;

    protected void startThread() {

    }

    protected void stopThread() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
