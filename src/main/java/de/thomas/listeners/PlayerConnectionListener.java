package de.thomas.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.config.context.PlayerContext;
import de.thomas.utils.crafting.RecipeManager;
import de.thomas.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(new Message(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName(), false).getMessage());

        PlayerContext playerContext = MinecraftSurvival.getINSTANCE().configuration.get(player.getUniqueId().toString(), PlayerContext.class);
        if (playerContext == null) {
            playerContext = new PlayerContext(true, new ArrayList<>());
        }
        Variables.playerConfigData.put(player.getUniqueId(), playerContext);
        MinecraftSurvival.getINSTANCE().configuration.set(player.getUniqueId().toString(), playerContext);
        MinecraftSurvival.getINSTANCE().configuration.save();

        RecipeManager.discoverRecipe(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerContext playerContext = Variables.playerConfigData.get(player.getUniqueId());
        MinecraftSurvival.getINSTANCE().configuration.set(player.getUniqueId().toString(), playerContext);
        MinecraftSurvival.getINSTANCE().configuration.save();
        event.quitMessage(new Message(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName(), false).getMessage());
    }

    @EventHandler
    public void onServerListPing(PaperServerListPingEvent event) {
        boolean day = (Objects.requireNonNull(Bukkit.getWorld("world")).getTime() > 23850 || Objects.requireNonNull(Bukkit.getWorld("world")).getTime() < 12300);
        String time = "Es ist: " + (day ? "§aTag" : "§1Nacht") + (Bukkit.getOnlinePlayers().size() != 0 ? "§r - " : "");
        event.motd(new Message(time + Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")), false).getMessage());
    }
}
