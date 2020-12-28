package de.thomas.utils.config;


import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class ConfigCache {

    public static @Nullable Location glideAreaLocation = null;
    public static double glideAreaRadius = -1;
    public static boolean glideBoots = false, spawnProtection = true;
    public static @Nullable Location spawnLocation = null;
    public static HashMap<UUID, String> verifiedPlayers = new HashMap<>();
    public static HashMap<UUID, Boolean> clockTime = new HashMap<>();
    public static HashMap<UUID, HashMap<String, Location>> playerWaypoints = new HashMap<>();
}
