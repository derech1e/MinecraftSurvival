package de.thomas.minecraftsurvival;

import de.thomas.bot.BotDirectMessageListener;
import de.thomas.bot.commands.HalloCommand;
import de.thomas.bot.commands.OnlinePlayerCommand;
import de.thomas.commands.*;
import de.thomas.listeners.*;
import de.thomas.utils.config.ConfigLoader;
import de.thomas.utils.message.Message;
import de.thomas.utils.threads.BotStatusMessageThread;
import de.thomas.utils.threads.RestartThread;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public class MinecraftSurvival extends JavaPlugin {

    private static MinecraftSurvival INSTANCE;
    public final Logger LOGGER = getSLF4JLogger();
    private JDA jda;

    public static MinecraftSurvival getINSTANCE() {
        return INSTANCE;
    }

    public JDA getJda() {
        return jda;
    }

    @Override
    public void onEnable() {
        LOGGER.info("Enabled Plugin " + Message.PREFIX);
        INSTANCE = this;
        LOGGER.info("Start to Init registries.");

        //Register some Stuff
        registerCommands();
        registerListeners();

        //Load Config
        LOGGER.info("Load Config File...");
        ConfigLoader.loadConfig();
        LOGGER.info("Loaded Config File.");

        //Load Threads
        new RestartThread();
        new BotStatusMessageThread();
        LOGGER.info("Started Restart Thread");

        //Register Bot
        LOGGER.info("Try to register Bot");
        try {
            registerBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        LOGGER.info("Shutdown Bot...");
        unregisterBot();
        LOGGER.info("Successfully shutdown Bot.");
        LOGGER.info("Saving Config File...");
        ConfigLoader.saveConfig();
        LOGGER.info("Saved Config File.");
        LOGGER.info("Disabled Plugin " + Message.PREFIX);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerBedListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);

        LOGGER.info("All Events registered!");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("glidearea")).setExecutor(new GlideAreaCommand());
        Objects.requireNonNull(getCommand("glideboost")).setExecutor(new GlideBoostCommand());
        Objects.requireNonNull(getCommand("spawnlocation")).setExecutor(new SpawnLocationCommand());
        Objects.requireNonNull(getCommand("start")).setExecutor(new StartCommand());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCommand());
        LOGGER.info("All Commands registered!");
    }

    private void unregisterBot() {
        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.cancelRequests();
        jda.setAutoReconnect(false);
        jda.shutdown();
        jda = null;
    }

    private void registerBot() throws LoginException {
        LOGGER.info("Try to init bot");
        jda = JDABuilder.create("NTE1NTU0ODYxMzQ2MDYyMzQ2.XWtxWA.wa6VCGxqT4A4SysUAe41cjX6774", GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS).build();
        jda.getPresence().setPresence(Activity.playing("Server started neu..."), true);
        jda.addEventListener(new BotDirectMessageListener());
        jda.addEventListener(new OnlinePlayerCommand());
        jda.addEventListener(new HalloCommand());
        jda.setAutoReconnect(true);
        LOGGER.info("Bot started!");
    }
}
