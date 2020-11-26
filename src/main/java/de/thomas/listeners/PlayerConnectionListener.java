package de.thomas.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());
        if (!player.hasPlayedBefore() || Variables.frozenPlayers.contains(event.getPlayer().getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255, false, false));
            player.teleport(ConfigCache.spawnLocation);
            if (!Variables.frozenPlayers.contains(event.getPlayer().getUniqueId()))
                Variables.frozenPlayers.add(event.getPlayer().getUniqueId());
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(MinecraftSurvival.getINSTANCE(), player));
            Bukkit.getOnlinePlayers().forEach(player1 -> player.hidePlayer(MinecraftSurvival.getINSTANCE(), player1));
        }
        MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing(Bukkit.getOnlinePlayers().size() + " Spieler Online (" + Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")) + ")"), true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> {
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            String playerNames = "(" + Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")) + ")";
            MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing(onlinePlayers.size() + " Spieler Online" + (Bukkit.getOnlinePlayers().size() != 0 ? playerNames : "")), true);
        }, 20 * 2);

        event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());
    }


    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) throws IOException, ParseException {
        String encodedDate = IOUtils.toString(new URL("https://raw.githubusercontent.com/derech1e/baguettelauncher/master/launch_date.txt"), StandardCharsets.UTF_8);
        DateFormat dateFormatParsable = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormatReadable = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = dateFormatParsable.parse(encodedDate);

        if (new Date().before(date) && !Bukkit.getOfflinePlayer(event.getUniqueId()).isOp()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§7Das Projekt hat leider noch nicht gestartet! \nTut mir leid :/\n\nBitte komme am §c" + dateFormatReadable.format(date) + " §7wieder!");
        }

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

    @EventHandler
    public void onServerListPing(PaperServerListPingEvent event) {
        event.setMotd(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")));
        event.setMaxPlayers(event.getNumPlayers() + 1);
    }
}
