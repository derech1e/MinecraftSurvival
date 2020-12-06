package de.thomas.utils.threads;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.message.Message;
import de.thomas.utils.threads.base.IThreadBase;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RestartThread extends IThreadBase {

    int seconds = 0;

    @Override
    protected void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);

            if (calendar.get(Calendar.HOUR_OF_DAY) == 20 && calendar.get(Calendar.MINUTE) >= 45) {
                int minutes = 60 - calendar.get(Calendar.MINUTE);
                startCountdown(minutes);
                stopThread();
            }
        }, 20, 100);
        super.startThread();
    }

    private void startCountdown(int timer) {
        seconds = timer;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            switch (seconds) {
                case 1:
                    Bukkit.broadcastMessage(new Message("Noch 15 Minuten bis zum Server Neustart").getMessage());
                    break;
                case 300:
                    Bukkit.broadcastMessage(new Message("Noch 10 Minuten bis zum Server Neustart").getMessage());
                    break;
                case 600:
                    Bukkit.broadcastMessage(new Message("Noch 5 Minuten bis zum Server Neustart").getMessage());
                    break;
                case 840:
                    Bukkit.broadcastMessage(new Message("Noch 60 Sekunden bis zum Server Neustart").getMessage());
                    break;
                case 890:
                    Bukkit.broadcastMessage(new Message("Noch 10 Sekunden bis zum Server Neustart").getMessage());
                    break;

                case 895:
                case 896:
                case 897:
                case 898:
                    Bukkit.broadcastMessage(new Message("Noch " + (900 - seconds) + " Sekunden bis zum Server Neustart").getMessage());
                    break;
                case 899:
                    Bukkit.broadcastMessage(new Message("Noch " + (900 - seconds) + " Sekunde bis zum Server Neustart").getMessage());
                    break;
                case 900:
                    Bukkit.broadcastMessage(new Message("Der Server startet jetzt neu!").getMessage());
                    Bukkit.savePlayers();
                    Bukkit.shutdown();
                    break;
            }
            seconds++;
        }, 20, 20);
    }
}
