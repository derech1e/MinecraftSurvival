package de.thomas.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CompassTarget<T> {

    T target;

    private CompassTarget(T target) {
        this.target = target;
    }

    public static <T extends Player> CompassTarget<T> create(T param) {
        return new CompassTarget<>(param);
    }

    public static <T extends Location> CompassTarget<T> create(T param) {
        return new CompassTarget<>(param);
    }

    public boolean isPlayer() {
        return target != null && target instanceof Player;
    }

    public boolean isLocation() {
        return target != null && target instanceof Location;
    }

    public boolean isSameEnvironment(World world) {
        if (isPlayer())
            return getPlayer().getWorld().getEnvironment() == world.getEnvironment();
        return getLocation().getWorld().getEnvironment() == world.getEnvironment();
    }

    public Location getTrivialLocation() {
        if (isPlayer())
            return getPlayer().getLocation();
        return getLocation();
    }

    public Player getPlayer() throws TypeNotPresentException {
        if (!isPlayer()) throw new TypeNotPresentException(Player.class.getTypeName(), null);
        return (Player) target;
    }

    public Location getLocation() throws TypeNotPresentException {
        if (!isLocation()) throw new TypeNotPresentException(Location.class.getTypeName(), null);
        return (Location) target;
    }
}