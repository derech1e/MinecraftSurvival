package de.thomas.minecraftsurvival;

import de.thomas.minecraftsurvival.commands.TpSpawnCommand;
import de.thomas.minecraftsurvival.listeners.InteractListener;
import de.thomas.minecraftsurvival.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class MinecraftSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.getGlobal().info("Enabled Plugin " + Message.PREFIX);
        Logger.getGlobal().info("Start to Init registries.");
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        Logger.getGlobal().info("Disabled Plugin " + Message.PREFIX);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InteractListener(), this);

        Logger.getGlobal().info("All Events registered!");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new TpSpawnCommand());

        Logger.getGlobal().info("All Commands registered!");
    }
}
