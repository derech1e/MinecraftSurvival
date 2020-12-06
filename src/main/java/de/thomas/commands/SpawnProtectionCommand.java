package de.thomas.commands;

import de.thomas.utils.config.ConfigCache;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SpawnProtectionCommand implements CommandExecutor {

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

        ConfigCache.spawnProtection = !ConfigCache.spawnProtection;
        player.sendMessage(new Message("Du hast die SpawnProtection " + ((ConfigCache.spawnProtection ? ChatColor.GREEN : ChatColor.RED) + (ConfigCache.spawnProtection ? "Aktiviert" : "Deaktiviert"))).getMessage());
        return true;
    }
}
