package de.thomas.utils.animation;

import com.destroystokyo.paper.Title;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.animation.particle.HelixParticleAnimationDown;
import de.thomas.utils.animation.particle.HelixParticleAnimationUp;
import de.thomas.utils.animation.particle.WaveParticleAnimation;
import de.thomas.utils.animation.particle.base.ParticleAnimationHandler;
import de.thomas.utils.config.ConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitleAnimation {

    public static final List<Player> playerInAnimation = new ArrayList<>();
    final Player targetPlayer;
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
        WorldBorderAnimation worldBorderAnimation = new WorldBorderAnimation(targetPlayer);
        worldBorderAnimation.reset();
        for (PotionEffect potionEffect : targetPlayer.getActivePotionEffects())
            targetPlayer.removePotionEffect(potionEffect.getType());
        targetPlayer.setBedSpawnLocation(ConfigCache.spawnLocation, true);
        targetPlayer.getWorld().setSpawnLocation(ConfigCache.spawnLocation);
        Location up = ConfigCache.spawnLocation.clone();
        up.setYaw(0);
        up.setPitch(90);
        targetPlayer.teleport(up);
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1240, 255, false, false, false));
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1260, 255, false, false, false));
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1250, 255, false, false, false));
        targetPlayer.teleport(targetPlayer.getLocation().add(0, 4725, 0));
        Title title_1 = new Title("§nMinecraft Survival§r §9#1", "Nach einem Legendärem Minigames-Server startete die §5Expedition ", 50, 60, 50);
        Title title_2 = new Title("§nMinecraft Fette-Mauer§r §a#2", "Auf das 1. Projekt, folgte das 2. mit neuen §dNachbarn §rund einer §dGroßen Mauer", 50, 60, 50);
        Title title_3 = new Title("§nMinecraft des Vergessens§r §b#3", "Es konnten keine genauen angaben in der Datenbank gefunden werden.", 50, 60, 50);
        Title title_4 = new Title("§nMinecraft Riesen-Turm§r §b#4", "Es entstanden nicht nur einige Lieben sondern auch Freundschaften im 4. §c<3", 50, 60, 50);
        Title title_5 = new Title("§nMinecraft Avatar§r §c#5", "Diese Lieben schafften es leider nicht; jeder versuchte im 5. seine §6Kräfte§r neu zu entdecken!", 50, 60, 50);
        Title title_6 = new Title("§nMinecraft WinterBaguette§r §d#6", "Doch als der §bWinter§r kam, hielten sie zusammen und Revolutionierten die Technik.", 50, 60, 50);
        Title title_7 = new Title("§nMinecraft Broken World§r §d#7", "Diese Erfindungen waren so stark, dass sie Teile der Welt irreversibel zerstörten...", 50, 60, 50);
        Title title_8 = new Title("§c§l§k!!!§r§6 Es war soweit §c§l§k!!!", "Eine neue Reise auf unbestimmte Zeit stand den Entdeckern bevor...", 50, 60, 50);
        Title title_9 = new Title("§6Herzlich Willkommen zum", "§1S§2u§3r§4v§5i§6v§7a§8l§9 P§ar§bo§cj§de§ek§ft §o#8", 50, 60, 50);
        Title title_10 = new Title("§6Viel Spaß beim Spielen!", "§c<3", 50, 60, 50);

        ParticleAnimationHandler particleAnimationHandler = new ParticleAnimationHandler();
        particleAnimationHandler.onFinal(() -> {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 0);
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 0);
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 0.5f, 0);
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 0);
            Bukkit.getScheduler().cancelTask(waitingID);
            targetPlayer.sendTitle(title_10);
            worldBorderAnimation.openWorldBorderFullDelayed();
            playerInAnimation.remove(targetPlayer);
            titleNumber = 1;
            Bukkit.getScheduler().cancelTask(taskID);
        });
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
                    targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_ELYTRA_FLYING, 1, 0);
                    Bukkit.getScheduler().runTaskLater(MinecraftSurvival.getINSTANCE(), () -> targetPlayer.playSound(targetPlayer.getLocation(), Sound.ITEM_ELYTRA_FLYING, 1, 0), 20 * 20);
                    worldBorderAnimation.setSize(15);
                    worldBorderAnimation.setTime(5);
                    worldBorderAnimation.setWorldBorderInTime();
                    particleAnimationHandler.addAnimations(new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 8, 0, 0, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 2, 0, 5, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 2, 0, 2, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 6, 0, 3, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 3, 0, 0, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 5, 0, 0, 64),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 2, 0, 5, 32),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 2, 0, 2, 16),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 6, 0, 3, 8),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 3, 0, 0, 16),
                            new HelixParticleAnimationUp(ConfigCache.spawnLocation.clone(), 5, 0, 0, 32),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 10, 0), 0, 4, 2),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 40, 0), 3, 6, 5),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 30, 0), 0, 8, 4),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 20, 0), 0, 8, 3),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 20, 0), 2, 8, 1),
                            new HelixParticleAnimationDown(ConfigCache.spawnLocation.clone().add(0, 20, 0), 5, 8, 2),
                            new WaveParticleAnimation(ConfigCache.spawnLocation.clone()),
                            new WaveParticleAnimation(ConfigCache.spawnLocation.clone().add(0, 15, 0)),
                            new WaveParticleAnimation(ConfigCache.spawnLocation.clone().add(0, 40, 0)));
                    particleAnimationHandler.start();
                    break;
                case 11:
                    Bukkit.getOnlinePlayers().forEach(player -> targetPlayer.showPlayer(MinecraftSurvival.getINSTANCE(), player));
                    Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(MinecraftSurvival.getINSTANCE(), targetPlayer));
                    targetPlayer.setVelocity(new Vector(0.6 * Math.random(), 1.2, 0.6 * Math.random()));

                    titleNumber++;
                    break;
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
