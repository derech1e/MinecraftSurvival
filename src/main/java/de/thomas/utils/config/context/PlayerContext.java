package de.thomas.utils.config.context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.thomas.utils.Variables;
import de.thomas.utils.config.base.JsonConfigSerializable;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public record PlayerContext(boolean clock, List<WayPoint> waypoints, int baguetteCounter) implements JsonConfigSerializable {

    public static PlayerContext deserialize(JsonObject object) {
        boolean clock = object.get("clock").getAsBoolean();
        int baguetteCounter = object.get("baguetteCounter").getAsInt();
        List<WayPoint> waypoints = new ArrayList<>();
        object.get("waypoints").getAsJsonArray().forEach(waypoint -> {
            JsonObject waypointObject = waypoint.getAsJsonObject();
            String id = waypointObject.get("id").getAsString();
            String name = waypointObject.get("name").getAsString();
            Location location = Variables.stringToLocation(waypointObject.get("location").getAsString());
            waypoints.add(new WayPoint(id, name, location));
        });
        return new PlayerContext(clock, waypoints, baguetteCounter);
    }

    public boolean isClock() {
        return clock;
    }

    public List<WayPoint> getWaypoints() {
        return waypoints;
    }

    public int getBaguetteCounter() {
        return baguetteCounter;
    }

    @Override
    public void serialize(JsonObject object) {
        object.addProperty("clock", this.clock);
        object.addProperty("baguetteCounter", this.baguetteCounter);
        JsonArray waypoints = new JsonArray();
        this.waypoints.forEach(waypoint -> {
            JsonObject waypointObject = new JsonObject();
            waypointObject.addProperty("id", waypoint.id());
            waypointObject.addProperty("name", waypoint.name());
            waypointObject.addProperty("location", Variables.locationToString(waypoint.location()));
            waypoints.add(waypointObject);
        });
        object.add("waypoints", waypoints);
    }
}
