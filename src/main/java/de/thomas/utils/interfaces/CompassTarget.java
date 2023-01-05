package de.thomas.utils.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CompassTarget<T> {

    T target;

    public CompassTarget(T target) {
        if(target instanceof Player || target instanceof Location) {
            this.target = target;
        } else {
            throw new IllegalArgumentException("Instance must be of type Player or Location");
        }
    }

    public boolean isPlayer() {
        return target != null && target instanceof Player;
    }

    public boolean isLocation() {
        return target != null && target instanceof Location;
    }

    public Player getPlayer() {
        if(!isPlayer()) throw new TypeNotPresentException(Player.class.getTypeName(), null);
        return (Player) target;
    }

    public Location getLocation() {
        if(!isLocation()) throw new TypeNotPresentException(Location.class.getTypeName(), null);
        return (Location) target;
    }
}