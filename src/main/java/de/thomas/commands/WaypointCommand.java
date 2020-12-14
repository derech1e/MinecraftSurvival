package de.thomas.commands;

import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import de.thomas.utils.config.ConfigLoader;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class WaypointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getMessage());
            return true;
        }
        Player player = (Player) sender;

        Location location = player.getLocation();
        HashMap<String, Location> loctions = new HashMap<>();
        loctions.put("test", player.getLocation());
        ConfigCache.playerWaypoints.put(player.getUniqueId(), loctions);
        ConfigLoader.saveConfig();
        ConfigLoader.loadConfig();
        Variables.targetCompassPlayers.remove(player.getUniqueId());
        Variables.targetCompassPlayers.put(player.getUniqueId(), null);
        player.sendMessage(new Message("Du hast deinen Wegpunkt auf §6X: " + Math.round(location.getX()) + " Y: " + Math.round(location.getY()) + " Z: " + Math.round(location.getZ()) + " §r gesetzt!").getMessage());
        return true;
    }
}
