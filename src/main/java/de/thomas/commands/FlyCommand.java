package de.thomas.commands;

import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getRawMessageString());
            return true;
        }
        ((Player) sender).setAllowFlight(!((Player) sender).getAllowFlight());
        return true;
    }
}
