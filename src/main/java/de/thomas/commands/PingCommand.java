package de.thomas.commands;

import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
            player.sendMessage(new Message("Dein Ping beträgt §6" + ping + "§f ms.").getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }
}
