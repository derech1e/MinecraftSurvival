package de.thomas.utils.config;


import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    private static final File configFile = new File("plugins/MinecraftSurvival/config.yml");
    private static final MinecraftSurvival INSTANCE = MinecraftSurvival.getINSTANCE();

    private static void setupConfig() {
        INSTANCE.getConfig().options().copyDefaults(true);
        INSTANCE.getConfig().addDefault("GlideArea.Location", new Location(INSTANCE.getServer().getWorld("world"), -10, 110, 10));
        INSTANCE.getConfig().addDefault("GlideArea.Radius", 15.0D);
        INSTANCE.getConfig().addDefault("GlideBoost", false);
        try {
            INSTANCE.getConfig().save(configFile);
        } catch (IOException e) {
            INSTANCE.LOGGER.error("Ein Fehler beim Setup der Config ist aufgetreten!", e);
        }
    }

    public static void loadConfig() {
        setupConfig();
        try {
            INSTANCE.getConfig().load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            INSTANCE.LOGGER.error("Ein Fehler beim Laden der Config ist aufgetreten!", e);
        }
        cacheConfig();
    }

    public static void saveConfig() {
        saveCache();
        try {
            INSTANCE.getConfig().save(configFile);
        } catch (IOException e) {
            INSTANCE.LOGGER.error("Ein Fehler beim speichern der Config ist aufgetreten!", e);
        }
    }

    private static void cacheConfig() {
        ConfigUtils configUtils = new ConfigUtils("GlideArea");
        ConfigCache.glideAreaLocation = configUtils.loadGlideAreaLocation();
        ConfigCache.glideAreaRadius = configUtils.loadGlideAreaRadius();


        ConfigUtils configUtilsBoost = new ConfigUtils("GlideBoost");
        ConfigCache.glideBoots = configUtilsBoost.loadGlideBoost();
    }

    private static void saveCache() {
        ConfigUtils configUtils = new ConfigUtils("GlideArea");
        configUtils.saveGlideAreaLocation(ConfigCache.glideAreaLocation, ConfigCache.glideAreaRadius);

        ConfigUtils configUtilsBoost = new ConfigUtils("GlideBoost");
        configUtilsBoost.saveGlideBoost(ConfigCache.glideBoots);
    }
}
