package de.thomas.minecraftsurvival;

import de.thomas.commands.TpSpawnCommand;
import de.thomas.listeners.CompassListener;
import de.thomas.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.Objects;

public final class MinecraftSurvival extends JavaPlugin {

    public final Logger LOGGER = getSLF4JLogger();

    @Override
    public void onEnable() {
        LOGGER.info("Enabled Plugin " + Message.PREFIX);
        LOGGER.info("Start to Init registries.");
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        LOGGER.info("Disabled Plugin " + Message.PREFIX);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CompassListener(), this);

        LOGGER.info("All Events registered!");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new TpSpawnCommand());

        LOGGER.info("All Commands registered!");
    }
}
