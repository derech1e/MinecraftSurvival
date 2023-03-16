package de.thomas.commands;

import de.thomas.MinecraftSurvival;
import de.thomas.utils.message.ErrorMessageType;
import de.thomas.utils.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChunkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(new Message(ErrorMessageType.NOT_A_PLAYER).getRawMessageString());
            return true;
        }
        if (args.length != 1) {
            player.sendMessage(new Message(ErrorMessageType.FALSE_PARAM).getPrefixedMessage());
            return false;
        } else {
            switch (args[0].toLowerCase()) {
                case "add" -> {
                    MinecraftSurvival.getINSTANCE().chunkManager.addChunk(player, player.getChunk());
                    player.sendMessage(new Message(String.format("Added Chunk %s %s", player.getChunk().getX(), player.getChunk().getZ())).getPrefixedMessage());
                }
                case "remove" -> {
                    MinecraftSurvival.getINSTANCE().chunkManager.removeChunk(player, player.getChunk());
                    player.sendMessage(new Message(String.format("Removed Chunk %s %s", player.getChunk().getX(), player.getChunk().getZ())).getPrefixedMessage());
                }
            }
        }
        return true;
    }
}
