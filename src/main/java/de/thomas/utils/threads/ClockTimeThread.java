package de.thomas.utils.threads;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.threads.base.IThreadBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockTimeThread extends IThreadBase {

    @Override
    public void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> Bukkit.getOnlinePlayers().forEach(player -> {
            DateFormat dateFormatReadable = new SimpleDateFormat("HH:mm:ss");
            player.sendActionBar(ChatColor.GRAY + dateFormatReadable.format(new Date()));
        }), 20, 20);
    }
}
