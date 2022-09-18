package de.thomas.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.builder.ItemBuilder;
import de.thomas.utils.config.context.PlayerContext;
import de.thomas.utils.crafting.RecipeManager;
import de.thomas.utils.message.Message;
import de.thomas.utils.resourcepack.ResourcePack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
            playerContext = new PlayerContext(true, new ArrayList<>(), 0);
        }
        Variables.playerConfigData.put(player.getUniqueId(), playerContext);
        MinecraftSurvival.getINSTANCE().configuration.set(player.getUniqueId().toString(), playerContext);
        MinecraftSurvival.getINSTANCE().configuration.save();

        Variables.resourcePack.setResourcePack(player.getUniqueId());

        if (!player.hasPlayedBefore()) {
            RecipeManager.discoverRecipe(player);
            new ItemBuilder(Material.COMPASS).setName("§6Der weise Wegweiser").addLore("HILFE: ICH DREHE GLEICH DURCH HIER").inject(player.getInventory());
        }
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
        String time = "Es ist: " + (day ? "<green>Tag</green>" : "<dark_aqua>Nacht</dark_aqua>") + (Bukkit.getOnlinePlayers().size() != 0 ? "<reset> - " : "");
        Component message = MiniMessage.miniMessage().deserialize(new Message("<rainbow>Survival Projekt #2022</rainbow><br>" + time + Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")), false).getMessageAsString());
        event.motd(message);
        event.setMaxPlayers(event.getNumPlayers());
    }

}
