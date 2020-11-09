package de.thomas.commands;

import de.thomas.utils.ErrorMessageType;
import de.thomas.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class TpSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
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
