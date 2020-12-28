package de.thomas.commands;

import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getMessage());
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 1) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if(targetPlayer != null)
                player = targetPlayer;
            else {
                sender.sendMessage(new Message(ErrorMessageType.NULL).getMessage());
                return true;
            }
        }
        try {
            assert player != null;
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
            sender.sendMessage(new Message("Dein Ping beträgt §6" + ping + "§f ms.").getMessage());
        } catch (@NotNull IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }
}
