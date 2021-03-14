package de.thomas.bot;

import de.thomas.bot.commands.HalloCommand;
import de.thomas.bot.commands.OnlinePlayerCommand;
import de.thomas.bot.log.UserChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author derech1e
 * @since 14.03.2021
 **/
public class BotMain {

    public static Logger logger = Logger.getLogger("UserChannelLogger");
    static FileHandler fh;
    private static JDA jda;

    public static void main(String[] args) {
        try {
            jda = JDABuilder.create("NTE1NTU0ODYxMzQ2MDYyMzQ2.XWtxWA.wa6VCGxqT4A4SysUAe41cjX6774",
                    GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS)
                    .setBulkDeleteSplittingEnabled(false).setCompression(Compression.NONE)
                    .addEventListeners(new BotDirectMessageListener())
                    .addEventListeners(new OnlinePlayerCommand())
                    .addEventListeners(new HalloCommand())
                    .addEventListeners(new UserChannelListener())
                    .setAutoReconnect(true)
                    .build();

            try {
                jda.awaitReady();
            } catch (InterruptedException exception) {
                Bukkit.getLogger().log(Level.SEVERE, "Ein Fehler beim Laden des Bots [Await Ready]", exception);
            }

//                jda.getPresence().setPresence(Activity.playing("Server startet neu..."), true);

//                jda.getPresence().setPresence(Activity.playing("Server gestartet"), true);
            System.out.println("startet!");
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Ein Fehler beim Laden des Bots", exception);
        }
        try {
            String timeStamp = new SimpleDateFormat().format(new Date());
            new File("channelLogs").mkdirs();
            fh = new FileHandler("channelLogs/%u.%g_" + timeStamp.replace(":", "_") + ".log", 30000, 4);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
