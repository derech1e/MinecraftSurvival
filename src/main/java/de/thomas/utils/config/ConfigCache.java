package de.thomas.utils.config;


import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class ConfigCache {

    public static Location glideAreaLocation = null;
    public static double glideAreaRadius = -1;
    public static boolean glideBoots = false;
    public static Location spawnLocation = null;
    public static HashMap<UUID, String> verifiedPlayers = new HashMap<>();
}
