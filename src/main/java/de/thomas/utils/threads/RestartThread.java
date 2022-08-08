package de.thomas.utils.threads;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.message.Message;
import de.thomas.utils.threads.base.IThreadBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RestartThread extends IThreadBase {

    int seconds = 0;

    @Override
    public void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);

            if (calendar.get(Calendar.HOUR_OF_DAY) == 2 && calendar.get(Calendar.MINUTE) >= 45) {
                int minutes = (calendar.get(Calendar.MINUTE) - 45) * 60;
                startCountdown(minutes);
                stopThread();
            }
        }, 0, 20);
        super.startThread();
    }

    private void startCountdown(int timer) {
        seconds = timer;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            switch (seconds) {
                case 1 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch 15 Minuten bis zum Server Neustart", true).getMessage());
                case 300 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch 10 Minuten bis zum Server Neustart", true).getMessage());
                case 600 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch 5 Minuten bis zum Server Neustart", true).getMessage());
                case 840 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch 60 Sekunden bis zum Server Neustart", true).getMessage());
                case 890 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch 10 Sekunden bis zum Server Neustart", true).getMessage());
                case 895, 896, 897, 898 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch " + (900 - seconds) + " Sekunden bis zum Server Neustart", true).getMessage());
                case 899 ->
                        Bukkit.broadcast(new Message(ChatColor.RED + "Noch " + (900 - seconds) + " Sekunde bis zum Server Neustart", true).getMessage());
                case 900 -> {
                    Bukkit.broadcast(new Message(ChatColor.RED + "Der Server startet jetzt neu!", true).getMessage());
                    Bukkit.savePlayers();
                    Bukkit.spigot().restart();
                }
            }
            seconds++;
        }, 0, 20);
    }
}
