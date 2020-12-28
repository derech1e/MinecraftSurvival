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


public class GlideAreaCommand implements CommandExecutor {

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

        if (args.length == 0) {
            ConfigCache.glideAreaLocation = player.getLocation();
            player.sendMessage(new Message(ChatColor.GRAY + "Du hast deine Position als neuen " + ChatColor.GOLD + "GlideArea-Mittelpunkt"+ ChatColor.GRAY + " gesetzt!").getMessage());
        } else {
            ConfigCache.glideAreaLocation = player.getLocation();
            try {
                ConfigCache.glideAreaRadius = Double.parseDouble(args[0]);
            } catch (Exception e) {
                player.sendMessage(new Message(ErrorMessageType.FALSE_PARAM).getMessage());
            }
            player.sendMessage(new Message(ChatColor.GRAY + "Du hast deine Position als neuen " + ChatColor.GOLD + "GlideArea-Mittelpunkt"+ ChatColor.GRAY + ", mit einem Radius von " + ChatColor.GOLD + ConfigCache.glideAreaRadius + ChatColor.GRAY + " gesetzt!").getMessage());
            return true;
        }
        return false;
    }
}
