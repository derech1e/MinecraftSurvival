package de.thomas.utils;

import de.thomas.utils.animation.particle.base.IParticleTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Variables {

    public static final List<Player> glidingPlayers = new ArrayList<>();
    public static final List<Player> protectedPlayers = new ArrayList<>();
    public static final String INVENTORY_NAME_COMPASS = ChatColor.GOLD + "Wähle einen Spieler";
    public static final String INVENTORY_NAME_SETTINGS = ChatColor.DARK_GRAY + "Einstellungen";
    public static final String INVENTORY_NAME_WAYPOINTS = ChatColor.GOLD + "Wegpunkte";
    public static final String INVENTORY_NAME_WAYPOINT_SELECT = ChatColor.GOLD + "Auswählen";
    public static final String INVENTORY_NAME_WAYPOINT_ADD = ChatColor.GREEN + "Hinzufügen";
    public static final String INVENTORY_NAME_WAYPOINT_DELETE = ChatColor.RED + "Entfernen";
    public static final HashMap<Short, UUID> verifyCodes = new HashMap<>();
    public static final List<IParticleTask> activeTasks = new ArrayList<>();
    public static final List<UUID> frozenPlayers = new ArrayList<>();
    public static final HashMap<UUID, Player> targetCompassPlayers = new HashMap<>();
    public static final List<Player> playerWithOpenAnvilGUI = new ArrayList<>();

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
}
