package de.thomas.minecraftsurvival.commands;

import de.thomas.minecraftsurvival.utils.ErrorMessageType;
import de.thomas.minecraftsurvival.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getMessage());
            return true;
        }
        Player player = ((Player) sender).getPlayer();
        if (args.length == 0) {
            if (player != null) {
                player.teleport(player.getWorld().getSpawnLocation());
                return true;
            }
        }
        return false;
    }
}
