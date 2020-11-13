package de.thomas.utils.animation;

import com.destroystokyo.paper.Title;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitleAnimation {

    private static final List<Player> playerInAnimation = new ArrayList<>();
    Player targetPlayer;
    private int titleNumber = 1;
    private int taskID;
    private int waitingID;

    public TitleAnimation(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public static List<Player> getPlayerInAnimation() {
        return playerInAnimation;
    }

    public void startFirstJoinAnimation() {
        playerInAnimation.add(targetPlayer);
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1240, 255, false, false, false));
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1260, 255, false, false, false));
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1250, 255, false, false, false));
        targetPlayer.teleport(targetPlayer.getLocation().add(0, 4725, 0));
        Title title_1 = new Title("§nMinecraft Survival §9#1", "Nach einem Legendärem Minigames-Server startete die §5Expedition ", 50, 60, 50);
        Title title_2 = new Title("§nMinecraft Fette Mauer §a#2", "Nach einem Legendärem 1. Projekt, folgte das 2. mit neuen §dNachbarn §rund einer §dGroßen Mauer", 50, 60, 50);
        Title title_3 = new Title("§nMinecraft des Vergessens §b#3", "Es konnten keine genauen angebanen in der Datenbank gefunden werden.", 50, 60, 50);
        Title title_4 = new Title("§nMinecraft RiesenTurm §b#4", "Es entstanden nicht nur einge Lieben sondern auf Freundschaften im 3. §c<3", 50, 60, 50);
        Title title_5 = new Title("§nMinecraft Avatar §c#5", "Diese Lieben schafften es leider nicht; jeder versuchte im 4. seine §6Kräfte§r neu zu entdecken!", 50, 60, 50);
        Title title_6 = new Title("§nMinecraft WinterBaguette §d#6", "Doch als der §bWinter§r kam, hielten Sie zusammen und Revolutionierten die Technick.", 50, 60, 50);
        Title title_7 = new Title("§nMinecraft Broken World §d#7", "Es war so stark, dass er Teile der Welt irreversibel zerstörte...", 50, 60, 50);
        Title title_8 = new Title("§c§l§k!!!§r§6 Es war soweit §c§l§k!!!", "Eine neue Reise auf unbestimmte Zeit stand den entdeckern bevor...", 50, 60, 50);
        Title title_9 = new Title("§6Herzlich Wilkommen zum", "§1S§2u§3r§4v§5i§6v§7a§8l§9 P§ar§bo§cj§de§ek§ft §o#8", 50, 60, 50);
        Title title_10 = new Title("§6Viel Spaß beim Spielen!", "", 50, 60, 50);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            switch (titleNumber) {
                case 1:
                    targetPlayer.sendTitle(title_1);
                    titleNumber++;
                    break;
                case 2:
                    targetPlayer.sendTitle(title_2);
                    titleNumber++;
                    break;
                case 3:
                    targetPlayer.sendTitle(title_3);
                    titleNumber++;
                    break;
                case 4:
                    targetPlayer.sendTitle(title_4);
                    titleNumber++;
                    break;
                case 5:
                    targetPlayer.sendTitle(title_5);
                    titleNumber++;
                    break;
                case 6:
                    targetPlayer.sendTitle(title_6);
                    titleNumber++;
                    break;
                case 7:
                    targetPlayer.sendTitle(title_7);
                    titleNumber++;
                    break;
                case 8:
                    targetPlayer.sendTitle(title_8);
                    titleNumber++;
                    break;
                case 9:
                    targetPlayer.sendTitle(title_9);
                    titleNumber++;
                    break;
                case 10:
                    showWaitingAnimation();
                    titleNumber++;
                    new WorldBorderAnimation(targetPlayer, 5, 15).setWorldBorderInTime();
                    new ParticleAnimation(ConfigCache.spawnLocation).start();
                    break;
                case 11:
                    titleNumber++;
                    break;
                case 12:
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10,0);
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10,0);
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 10,0);
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10,0);
                    Bukkit.getScheduler().cancelTask(waitingID);
                    targetPlayer.sendTitle(title_10);
                    new WorldBorderAnimation(targetPlayer).openWorldBorderFullDelayed();
                    playerInAnimation.remove(targetPlayer);
                    titleNumber = 1;
                    Bukkit.getScheduler().cancelTask(taskID);

            }
        }, 20, 150);
    }

    private ChatColor getRandomColor() {
        return ChatColor.values()[new Random().nextInt(15)];
    }

    private void showWaitingAnimation() {
        waitingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> targetPlayer.sendTitle("", getRandomColor() + "...", 5, 10, 5), 20, 20);
    }

}
