package de.thomas.utils.config.context;

import org.bukkit.Location;

import java.util.UUID;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public record WayPoint(String id, String name, Location location) {
    public WayPoint(String name, Location location) {
        this(UUID.randomUUID().toString(), name, location);
    }
}
