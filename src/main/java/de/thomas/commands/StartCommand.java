package de.thomas.commands;

import de.thomas.utils.Variables;
import de.thomas.utils.animation.TitleAnimation;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collections;

public class StartCommand implements CommandExecutor {

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

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Variables.freezedPlayers.removeAll(Collections.singleton(onlinePlayer.getUniqueId()));
            new TitleAnimation(onlinePlayer).startFirstJoinAnimation();
        }
        return false;
    }
}
