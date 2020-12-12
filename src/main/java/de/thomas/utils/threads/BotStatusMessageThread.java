package de.thomas.utils.threads;

import de.thomas.minecraftsurvival.MinecraftSurvival;
import de.thomas.utils.threads.base.IThreadBase;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;

public class BotStatusMessageThread extends IThreadBase {

    int counter = 0;

    @Override
    public void startThread() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftSurvival.getINSTANCE(), () -> {
            switch (counter) {
                case 1:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("92.42.46.36:311"), true);
                    break;
                case 2:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("95.156.227.125:311"), true);
                    break;
                case 3:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("!online für Stats"), true);
                    break;
                case 4:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("!mc für Stats"), true);
                    break;
                case 5:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("!help für Hilfe"), true);
                    break;
                case 6:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("mit 0 Spielern"), true);
                    counter = 0;
                    break;
                default:
                    MinecraftSurvival.getINSTANCE().getJda().getPresence().setPresence(Activity.playing("Server started neu..."), true);
                    counter = 0;
                    break;
            }
            counter++;
        }, 20, 20 * 60);
        super.startThread();
    }
}
