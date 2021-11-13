package de.thomas.commands;

import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getMessageAsString());
            return true;
        }
        if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null)
                player = targetPlayer;
            else {
                sender.sendMessage(new Message(ChatColor.RED + "Dieser Spieler ist derzeit nicht online!", true).getMessageAsString());
                return true;
            }
        }
        sender.sendMessage(new Message("Dein Ping beträgt §6" + player.getPing() + "§f ms.", true).getMessageAsString());

        return false;
    }
}
