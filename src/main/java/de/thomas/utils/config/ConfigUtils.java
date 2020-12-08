package de.thomas.utils.config;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

    public boolean loadSpawnProtection() {
        return INSTANCE.getConfig().getBoolean(configItemName);
    }

    public void saveSpawnProtection(boolean state) {
        INSTANCE.getConfig().set(configItemName, state);
    }

    public Location loadSpawnLocation() {
        return INSTANCE.getConfig().getLocation(configItemName);
    }

    public void saveSpawnLocation(Location configLocation) {
        INSTANCE.getConfig().set(configItemName, configLocation);
    }

    public void saveVerifiedPlayers(HashMap<UUID, String> players) {
        for (Map.Entry<UUID, String> map : players.entrySet()) {
            INSTANCE.getConfig().set("Players.Verified." + map.getKey().toString(), map.getValue());
        }
    }

    public HashMap<UUID, String> loadVerifiedPlayers() {
        HashMap<UUID, String> verifiedPlayers = new HashMap<>();
        for(String key : Objects.requireNonNull(INSTANCE.getConfig().getConfigurationSection("Players.Verified")).getKeys(false)) {
             verifiedPlayers.put(UUID.fromString(key), Objects.requireNonNull(INSTANCE.getConfig().getString("Players.Verified." + key)));
        }
        return verifiedPlayers;
    }

    public void savePlayerWaypoints(HashMap<UUID, Location> playerWaypoints) {
        for (Map.Entry<UUID, Location> map : playerWaypoints.entrySet()) {
            INSTANCE.getConfig().set("Players.Waypoints." + map.getKey().toString(), map.getValue());
        }
    }

    public HashMap<UUID, Location> loadPlayerWaypoints() {
        HashMap<UUID, Location> playerWaypoints = new HashMap<>();
        for(String key : Objects.requireNonNull(INSTANCE.getConfig().getConfigurationSection("Players.Waypoints")).getKeys(false)) {
            playerWaypoints.put(UUID.fromString(key), Objects.requireNonNull(INSTANCE.getConfig().getLocation("Players.Waypoints." + key)));
        }
        return playerWaypoints;
    }
}
