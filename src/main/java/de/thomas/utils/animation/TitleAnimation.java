package de.thomas.utils.animation;

import com.destroystokyo.paper.Title;
import de.thomas.minecraftsurvival.MinecraftSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TitleAnimation {

    Player targetPlayer;
    private int titleNumber = 1;
    private int taskID;

    public TitleAnimation(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public void startFirstJoinAnimation() {
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 970, 255, false, false, false));
        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 970, 255, false, false, false));
        targetPlayer.teleport(targetPlayer.getLocation().add(0, 3625, 0));
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 10, 1);
        Title title_1 = new Title("§lMinecraft Survival §9#1", "Nach einem Legendärem Minigames-Server startete die §5Expedition ", 50, 60, 50);
        Title title_2 = new Title("§lMinecraft Survival §a#2", "Nach einem Legendärem 1. Projekt, folgte das 2. mit neuen §dNachbarn §rund einer §dGroßen Mauer", 50, 60, 50);
        Title title_3 = new Title("§lMinecraft RiesenTurm §b#3", "Es entstanden nicht nur einge Lieben sondern auf Freundschaften im 3. §c<3", 50, 60, 50);
        Title title_4 = new Title("§lMinecraft Avatar §c#4", "Diese Lieben schafften es leider nicht; jeder versuchte im 4. seine §6Kräfte§r neu zu entdecken!", 50, 60, 50);
        Title title_5 = new Title("§lMinecraft WinterBaguette §d#5", "Doch als der §bWinter§r kam, hielten Sie zusammen und Revolutionierten die Technick.", 50, 60, 50);
        Title title_6 = new Title("§c§l§k!!!§r§6 Es war soweit §c§l§k!!!", "Eine neue Reise auf unbestimmte Zeit stand den entdeckern bevor...", 50, 60, 50);
        Title title_7 = new Title("§6Herzlich Wilkommen zum", "Survival Projekt #6", 20, 60, 50);


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
                    titleNumber = 1;
                    Bukkit.getScheduler().cancelTask(taskID);
                    break;
            }
        }, 20, 150);
    }

    public void startProjektAnimation() {
        WorldBorder border = targetPlayer.getWorld().getWorldBorder();
        border.setCenter(targetPlayer.getLocation());
        border.setSize(999999,20);
    }

}
