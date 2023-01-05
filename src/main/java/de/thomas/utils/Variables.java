package de.thomas.utils;

import de.thomas.utils.config.context.PlayerContext;
import de.thomas.utils.interfaces.CompassTarget;
import de.thomas.utils.message.Message;
import de.thomas.utils.resourcepack.ResourcePack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Variables {

    public static final Component INVENTORY_NAME_COMPASS = new Message(ChatColor.GOLD + "Wähle einen Spieler", false).getMessage();
    public static final Component INVENTORY_NAME_SETTINGS = new Message(ChatColor.DARK_GRAY + "Einstellungen", false).getMessage();
    public static final Component INVENTORY_NAME_WAYPOINTS = new Message(ChatColor.GOLD + "Wegpunkte", false).getMessage();
    public static final Component INVENTORY_NAME_WAYPOINT_SELECT = new Message(ChatColor.GOLD + "Auswählen", false).getMessage();

    public static final Component INVENTORY_NAME_WAYPOINT_ADD = new Message(ChatColor.GREEN + "Hinzufügen", false).getMessage();
    public static final Component INVENTORY_NAME_WAYPOINT_DELETE = new Message(ChatColor.RED + "Entfernen", false).getMessage();
    public static final HashMap<UUID, CompassTarget<?>> targetCompassPlayers = new HashMap<>();
    public static final HashMap<UUID, PlayerContext> playerConfigData = new HashMap<>();
    public static final HashMap<UUID, Location> playerPortalLocationSpawnMap = new HashMap<>();
    public static final String TOTAL_BAGUETTE_COUNTER_CONFIG_NAME = "totalBaguetteCounter";
    public static ResourcePack resourcePack = null;

    public static int calculateInventorySize(int onlinePlayers) {
        if (onlinePlayers <= 9)
            return 9;
        else if (onlinePlayers <= 18)
            return 18;
        else if (onlinePlayers <= 27)
            return 27;
        else if (onlinePlayers <= 36)
            return 36;
        else if (onlinePlayers <= 45)
            return 45;
        else
            return 54;
    }

    public static String locationToString(Location location) {
        return String.format("%s;%s;%s;%s;%s;%s", location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public static Location stringToLocation(String location) {
        String[] params = location.split(";");
        return new Location(Bukkit.getWorld(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]), Double.parseDouble(params[3]), Float.parseFloat(params[4]), Float.parseFloat(params[5]));
    }
}
