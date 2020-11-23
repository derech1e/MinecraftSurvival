package de.thomas.listeners;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.animation.TitleAnimation;
import de.thomas.utils.config.ConfigCache;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());
        if (!player.hasPlayedBefore()) {
            player.setBedSpawnLocation(ConfigCache.spawnLocation, true);
            player.getWorld().setSpawnLocation(ConfigCache.spawnLocation);
            player.teleport(ConfigCache.spawnLocation);
            new TitleAnimation(player).startFirstJoinAnimation();
        }
        MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing(Bukkit.getOnlinePlayers().size() + " Spieler auf dem Server"), true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing(Bukkit.getOnlinePlayers().size() - 1 + " Spieler auf dem Server"), true);
        event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + event.getPlayer().getName());
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if (!ConfigCache.verifiedPlayers.containsKey(event.getUniqueId())) {
            String message = "&7Du musst dein &9Discord&7-Konto verknüpfen, um spielen zu können.\n\n&7Sende eine DM an den &bBaguetteBot&7, die dem Code &b{CODE}&7, um dein Account zu verknüpfen.\n\n&7Bei Problemen... » &bhast du Pech gehabt.";
            short code = (short) new Random().nextInt(1 << 15);
            if (Variables.verifyCodes.containsValue(event.getUniqueId())) {
                Variables.verifyCodes.values().remove(event.getUniqueId());
            }
            Variables.verifyCodes.put(code, event.getUniqueId());
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', message).replace("{CODE}", String.valueOf(code)));
        }
    }
}
