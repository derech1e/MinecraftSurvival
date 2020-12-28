package de.thomas.utils.config;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigUtils {

    private static final MinecraftSurvival INSTANCE = MinecraftSurvival.getINSTANCE();

    private final String configItemName;

    public ConfigUtils(String configItemName) {
        this.configItemName = configItemName;
    }

    public @Nullable Location loadGlideAreaLocation() {
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

    public @Nullable Location loadSpawnLocation() {
        return INSTANCE.getConfig().getLocation(configItemName);
    }

    public void saveSpawnLocation(Location configLocation) {
        INSTANCE.getConfig().set(configItemName, configLocation);
    }

    public void saveVerifiedPlayers(@NotNull HashMap<UUID, String> players) {
        for (Map.Entry<UUID, String> map : players.entrySet()) {
            INSTANCE.getConfig().set("Players.Verified." + map.getKey().toString(), map.getValue());
        }
    }

    public @NotNull HashMap<UUID, String> loadVerifiedPlayers() {
        HashMap<UUID, String> verifiedPlayers = new HashMap<>();
        for (String key : Objects.requireNonNull(INSTANCE.getConfig().getConfigurationSection("Players.Verified")).getKeys(false)) {
            verifiedPlayers.put(UUID.fromString(key), Objects.requireNonNull(INSTANCE.getConfig().getString("Players.Verified." + key)));
        }
        return verifiedPlayers;
    }

    public void savePlayerWaypoints(@NotNull HashMap<UUID, HashMap<String, Location>> playerWaypoints) {
        if (playerWaypoints.isEmpty()) {
            INSTANCE.getConfig().set("Players.Waypoints", null);
            return;
        }
        for (Map.Entry<UUID, HashMap<String, Location>> map : playerWaypoints.entrySet()) {
            INSTANCE.getConfig().set("Players.Waypoints." + map.getKey().toString(), map.getValue());
        }
    }

    public @NotNull HashMap<UUID, HashMap<String, Location>> loadPlayerWaypoints() {
        HashMap<UUID, HashMap<String, Location>> playerWaypoints = new HashMap<>();
        HashMap<String, Location> detail = new HashMap<>();
        try {
            for (String uuid : Objects.requireNonNull(INSTANCE.getConfig().getConfigurationSection("Players.Waypoints")).getKeys(false)) {
                for (String name : INSTANCE.getConfig().getConfigurationSection("Players.Waypoints." + uuid).getKeys(false)) {
                    detail.put(name, INSTANCE.getConfig().getLocation("Players.Waypoints." + uuid + "." + name));
                }
                playerWaypoints.put(UUID.fromString(uuid), detail);
            }
        } catch (NullPointerException ex) {
            Logger.getGlobal().log(Level.CONFIG, "Fehler beim Laden der PlayerWaypoints", ex);
            return playerWaypoints;
        }
        return playerWaypoints;
    }

    public @NotNull HashMap<UUID, Boolean> loadPlayerClockTime() {
        HashMap<UUID, Boolean> verifiedPlayers = new HashMap<>();
        if (INSTANCE.getConfig().getConfigurationSection("Players.Clock") != null)
            for (String key : Objects.requireNonNull(INSTANCE.getConfig().getConfigurationSection("Players.Clock")).getKeys(false)) {
                verifiedPlayers.put(UUID.fromString(key), INSTANCE.getConfig().getBoolean("Players.Clock." + key));
            }
        return verifiedPlayers;
    }

    public void savePlayerClockTime(@NotNull HashMap<UUID, Boolean> clockTime) {
        for (Map.Entry<UUID, Boolean> map : clockTime.entrySet()) {
            INSTANCE.getConfig().set("Players.Clock." + map.getKey().toString(), map.getValue());
        }
    }
}
