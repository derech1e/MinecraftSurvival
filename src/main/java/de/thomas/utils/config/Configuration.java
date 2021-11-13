package de.thomas.utils.config;

import com.google.gson.JsonObject;
import de.thomas.utils.Variables;
import de.thomas.utils.config.base.AbstractConfiguration;
import de.thomas.utils.config.context.PlayerContext;
import de.thomas.utils.config.context.WayPoint;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.List;
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
        return Variables.playerConfigData.get(player.getUniqueId()).isClock();
    }

    public void addWayPoint(Player player, String name) {
        List<WayPoint> wayPoints = getWayPoints(player);
        wayPoints.add(new WayPoint(name, player.getLocation()));
        setWaypoints(player, wayPoints);
    }

    public void removeWayPoint(Player player, int index, Inventory inventory) {
        int redundantIndex = 0;
        for (int i = 0; i < index; i++) {
            if (!inventory.getItem(i).getType().equals(Material.PLAYER_HEAD)) {
                redundantIndex++;
            }
        }
        WayPoint wayPoint = getWorldWayPoints(player).get(index - redundantIndex);
        List<WayPoint> wayPoints = getWayPoints(player);
        wayPoints.remove(wayPoint);
        setWaypoints(player, wayPoints);
    }

    public List<WayPoint> getWayPoints(Player player) {
        return Variables.playerConfigData.get(player.getUniqueId()).getWaypoints();
    }

    public List<WayPoint> getWorldWayPoints(Player player) {
        return Variables.playerConfigData.get(player.getUniqueId()).getWaypoints().stream().filter(wayPoint -> wayPoint.location().getWorld().getName().equals(player.getWorld().getName())).collect(Collectors.toList());
    }

    public List<WayPoint> setWaypoints(Player player, List<WayPoint> wayPoints) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(getClockStateByPlayer(player), wayPoints));
        return wayPoints;
    }

    public boolean getClockStateByPlayer(Player player, boolean state) {
        Variables.playerConfigData.put(player.getUniqueId(), new PlayerContext(state, getWayPoints(player)));
        return state;
    }
}
