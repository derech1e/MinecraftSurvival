package de.thomas.utils.threads;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.Variables;
import de.thomas.utils.threads.base.IThreadBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockTimeThread extends IThreadBase {

    @Override
    public void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> Bukkit.getOnlinePlayers().forEach(player -> {
            if (MinecraftSurvival.getINSTANCE().configuration.getClockStateByPlayer(player)) {
                DateFormat dateFormatReadable = new SimpleDateFormat("HH:mm:ss");
                player.sendActionBar(Component.text(dateFormatReadable.format(new Date()), NamedTextColor.GRAY));
            }
        }), 0, 20);
    }
}
