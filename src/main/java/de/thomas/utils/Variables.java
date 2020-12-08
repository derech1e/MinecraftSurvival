package de.thomas.utils;

import de.thomas.utils.animation.particle.base.IParticleTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Variables {

    public static final List<Player> glidingPlayers = new ArrayList<>();
    public static final List<Player> protectedPlayers = new ArrayList<>();
    public static final String INVENTORY_NAME_COMPASS = ChatColor.GOLD + "Wähle einen Spieler";
    public static final HashMap<Short, UUID> verifyCodes = new HashMap<>();
    public static final List<IParticleTask> activeTasks = new ArrayList<>();
    public static final List<UUID> frozenPlayers = new ArrayList<>();
    public static final HashMap<UUID, Player> targetCompassPlayers = new HashMap<>();

}
