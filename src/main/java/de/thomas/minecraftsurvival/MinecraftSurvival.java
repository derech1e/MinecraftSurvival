package de.thomas.minecraftsurvival;

import de.thomas.commands.PingCommand;
import de.thomas.listeners.*;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.crafting.RecipeManager;
import de.thomas.utils.threads.ClockTimeThread;
import de.thomas.utils.threads.RestartThread;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MinecraftSurvival extends JavaPlugin {

    private static MinecraftSurvival INSTANCE;
    public Configuration configuration;

    public static MinecraftSurvival getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        configuration = new Configuration(this);
        configuration.init();
        configuration.load();

        registerCommands();
        registerListeners();


        //Load Recipes
        RecipeManager.registerRecipes(getServer());

        //Load Threads
        new RestartThread().startThread();
        new ClockTimeThread().startThread();
    }

    @Override
    public void onDisable() {
        RecipeManager.unregisterRecipes(getServer());
        configuration.save();
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerBedListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerPortalListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCommand());
    }
}
