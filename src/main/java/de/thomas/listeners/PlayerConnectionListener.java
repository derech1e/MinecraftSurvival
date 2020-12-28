package de.thomas.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.config.ConfigCache;
import de.thomas.utils.crafting.RecipeManager;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());
        updateDiscordStatus();
        if(!ConfigCache.clockTime.containsKey(player.getUniqueId())) {
            ConfigCache.clockTime.put(player.getUniqueId(), true);
        }
        RecipeManager.discoverRecipe(player);
        /*if (!player.hasPlayedBefore() || Variables.frozenPlayers.contains(event.getPlayer().getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 255, false, false));
            player.teleport(ConfigCache.spawnLocation);
            if (!Variables.frozenPlayers.contains(event.getPlayer().getUniqueId()))
                Variables.frozenPlayers.add(event.getPlayer().getUniqueId());
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(MinecraftSurvival.getINSTANCE(), player));
            Bukkit.getOnlinePlayers().forEach(player1 -> player.hidePlayer(MinecraftSurvival.getINSTANCE(), player1));
        }*/
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        updateDiscordStatus();
        event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + player.getName());
    }


    @EventHandler
    public void onAsyncPreLogin(@NotNull AsyncPlayerPreLoginEvent event) throws IOException, ParseException {
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
    public void onServerListPing(@NotNull PaperServerListPingEvent event) {
        boolean day = (Objects.requireNonNull(Bukkit.getWorld("world")).getTime() > 23850 || Objects.requireNonNull(Bukkit.getWorld("world")).getTime() < 12300);
        String time = "Es ist: " + (day ? "§aTag" : "§1Nacht") + (Bukkit.getOnlinePlayers().size() != 0 ? "§r - " : "");
        event.setMotd(time + Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.joining(", ")));
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        event.setMaxPlayers(Math.max(dayOfMonth, event.getNumPlayers() + 1));
    }

    private void updateDiscordStatus() {
        Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> {
            int playerSize = Bukkit.getOnlinePlayers().size();
            if(playerSize == 0) {
                MinecraftSurvival.getINSTANCE().getBotStatusMessageThread().startThread();
                return;
            }
            MinecraftSurvival.getINSTANCE().getBotStatusMessageThread().stopThread();
            MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("mit " + playerSize + (playerSize == 1 ? " Spieler " : " Spielern")), true);
        },20 * 2);
    }
}
