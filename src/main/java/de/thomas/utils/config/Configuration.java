package de.thomas.utils.config;

import com.google.gson.JsonObject;
import de.thomas.utils.Variables;
import de.thomas.utils.config.base.AbstractConfiguration;
import de.thomas.utils.config.context.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public class Configuration extends AbstractConfiguration {

    public Configuration(Plugin plugin) {
        super(plugin.getDataFolder(), "config.json");
    }

    @Override
    public void addDefaults() {
        this.root = new JsonObject();
        this.save();
    }

    public boolean getClockStateByPlayer(Player player) {
        if (Variables.playerConfigData.containsKey(player.getUniqueId()))
            return Variables.playerConfigData.get(player.getUniqueId()).clock();
        else
            return true;
    }

    public boolean getSpeedBlockStateByPlayer(Player player) {
        if (Variables.playerConfigData.containsKey(player.getUniqueId()))
            return Variables.playerConfigData.get(player.getUniqueId()).speedBlock();
        else
            return false;
    }

    public Location getPortalLocationByPlayer(Player player) {
        if (Variables.playerConfigData.containsKey(player.getUniqueId()))
            return Variables.playerConfigData.get(player.getUniqueId()).portalLocation();
        return null;
    }

    public int getBaguetteCounterByPlayer(Player player) {
        if (Variables.playerConfigData.containsKey(player.getUniqueId()))
            return Variables.playerConfigData.get(player.getUniqueId()).baguetteCounter();
        else
            return 0;
    }

    public void addBaguetteCounter(Player player) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(getClockStateByPlayer(player), getWayPoints(player), getBaguetteCounterByPlayer(player) + 1, getPortalLocationByPlayer(player), getSpeedBlockStateByPlayer(player)));
    }

    public void addWayPoint(Player player, String name) {
        List<WayPoint> wayPoints = getWayPoints(player);
        wayPoints.add(new WayPoint(name, player.getLocation()));
        setWaypoints(player, wayPoints);
    }

    public void removeWayPoint(Player player, int index, Inventory inventory) {
        int redundantIndex = 0;
        for (int i = 0; i < index; i++) {
            if (!Objects.requireNonNull(inventory.getItem(i)).getType().equals(Material.PLAYER_HEAD)) {
                redundantIndex++;
            }
        }
        WayPoint wayPoint = getWorldWayPoints(player).get(index - redundantIndex);
        List<WayPoint> wayPoints = getWayPoints(player);
        wayPoints.remove(wayPoint);
        setWaypoints(player, wayPoints);
    }

    public List<WayPoint> getWayPoints(Player player) {
        return Variables.playerConfigData.get(player.getUniqueId()).waypoints();
    }

    public List<WayPoint> getWorldWayPoints(Player player) {
        return Variables.playerConfigData.get(player.getUniqueId()).waypoints().stream().filter(wayPoint -> wayPoint.location().getWorld().getName().equals(player.getWorld().getName())).collect(Collectors.toList());
    }

    public void setWaypoints(Player player, List<WayPoint> wayPoints) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(getClockStateByPlayer(player), wayPoints, getBaguetteCounterByPlayer(player), getPortalLocationByPlayer(player), getSpeedBlockStateByPlayer(player)));
    }

    public boolean updateClockStateByPlayer(Player player, boolean state) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(state, getWayPoints(player), getBaguetteCounterByPlayer(player), getPortalLocationByPlayer(player), getSpeedBlockStateByPlayer(player)));
        return state;
    }

    public boolean updateSpeedBlockStateByPlayer(Player player, boolean state) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(getClockStateByPlayer(player), getWayPoints(player), getBaguetteCounterByPlayer(player), getPortalLocationByPlayer(player), state));
        return state;
    }

    public void updatePortalLocationByPlayer(Player player, Location location) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(getClockStateByPlayer(player), getWayPoints(player), getBaguetteCounterByPlayer(player), location, getSpeedBlockStateByPlayer(player)));
    }

    public List<ChunkData> getLoadedChunks() {
        ChunkLoadingContext context = this.get("loadedChunks", ChunkLoadingContext.class);
        return context == null ? new ArrayList<>() : context.chunkDataList();
    }
    public void addChunk(ChunkData chunkData) {
        List<ChunkData> newChunkData = getLoadedChunks();
        newChunkData.add(chunkData);
        this.set("loadedChunks", new ChunkLoadingContext(newChunkData));
        this.save();
    }
    public void removeChunk(ChunkData chunkData) {
        List<ChunkData> newChunkData = getLoadedChunks();
        newChunkData.remove(chunkData);
        this.set("loadedChunks", new ChunkLoadingContext(newChunkData));
        this.save();
    }
}
