package de.thomas.commands;

import de.thomas.utils.config.ConfigLoader;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ReloadConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getMessage());
            return true;
        }
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(new Message(ErrorMessageType.NOT_ENOUGH_PERMISSION).getMessage());
            return true;
        }

        ConfigLoader.loadConfig();
        Bukkit.getServer().reloadCommandAliases();
        Bukkit.getServer().reloadData();
        Bukkit.getServer().reloadPermissions();
        Bukkit.getServer().reloadWhitelist();
        player.sendMessage(new Message("Config wurde erfolgreich Reloaded!").getMessage());
        return true;
    }
}
