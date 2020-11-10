package de.thomas.minecraftsurvival;

import de.thomas.commands.GlideAreaCommand;
import de.thomas.commands.GlideBoostCommand;
import de.thomas.listeners.CompassListener;
import de.thomas.listeners.PlayerConnectionListener;
import de.thomas.listeners.PlayerGlideListener;
import de.thomas.listeners.PlayerSleepListener;
import de.thomas.utils.RestartThread;
import de.thomas.utils.config.ConfigLoader;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public final class MinecraftSurvival extends JavaPlugin {

    private static MinecraftSurvival INSTANCE;
    public final Logger LOGGER = getSLF4JLogger();

    public static MinecraftSurvival getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        LOGGER.info("Enabled Plugin " + Message.PREFIX);
        INSTANCE = this;
        LOGGER.info("Start to Init registries.");

        //Register some Stuff
        registerCommands();
        registerListeners();

        //Load Config
        LOGGER.info("Load Config File...");
        ConfigLoader.loadConfig();
        LOGGER.info("Loaded Config File.");

        //Load Threads
        new RestartThread();
        LOGGER.info("Started Restart Thread");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Saving Config File...");
        ConfigLoader.saveConfig();
        LOGGER.info("Saved Config File.");
        LOGGER.info("Disabled Plugin " + Message.PREFIX);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CompassListener(), this);
        pluginManager.registerEvents(new PlayerSleepListener(), this);
        pluginManager.registerEvents(new PlayerGlideListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);

        LOGGER.info("All Events registered!");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("glidearea")).setExecutor(new GlideAreaCommand());
        Objects.requireNonNull(getCommand("glideboost")).setExecutor(new GlideBoostCommand());
        LOGGER.info("All Commands registered!");
    }
}
