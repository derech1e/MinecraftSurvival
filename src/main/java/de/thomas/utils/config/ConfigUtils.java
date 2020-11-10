package de.thomas.utils.config;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Location;

public class ConfigUtils {

    private static final MinecraftSurvival INSTANCE = MinecraftSurvival.getINSTANCE();

    private final String configItemName;

    public ConfigUtils(String configItemName) {
        this.configItemName = configItemName;
    }

    public Location loadGlideAreaLocation() {
        return INSTANCE.getConfig().getLocation(configItemName + ".Location");
    }

    public double loadGlideAreaRadius() {
        return INSTANCE.getConfig().getDouble(configItemName + ".Radius");
    }

    public void saveGlideAreaLocation(Location configLocation, double radius) {
        INSTANCE.getConfig().set(configItemName + ".Location", configLocation);
        INSTANCE.getConfig().set(configItemName + ".Radius", radius);
    }

    public boolean loadGlideBoost() {
        return INSTANCE.getConfig().getBoolean(configItemName);
    }

    public void saveGlideBoost(boolean state) {
        INSTANCE.getConfig().set(configItemName, state);
    }
}
