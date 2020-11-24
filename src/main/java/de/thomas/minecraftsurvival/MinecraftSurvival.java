package de.thomas.minecraftsurvival;

import de.thomas.bot.BotDirectMessageListener;
import de.thomas.bot.commands.HalloCommand;
import de.thomas.bot.commands.OnlinePlayerCommand;
import de.thomas.commands.*;
import de.thomas.listeners.*;
import de.thomas.utils.RestartThread;
import de.thomas.utils.config.ConfigLoader;
import de.thomas.utils.message.Message;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        LOGGER.info("Started Restart Thread");

        //Register Bot
        try {
            registerBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
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

    private void registerBot() throws LoginException {
        LOGGER.info("Try to init bot");
        jda = JDABuilder.create("Nzc2ODQxOTA4ODg5OTc2ODYy.X66waA.D1nzOfVglK7iDHuY08n5kSAo4wg", GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES).build();
        jda.getPresence().setPresence(Activity.playing(getServer().getOnlinePlayers().size() + " Spieler auf dem Server"), true);
        jda.addEventListener(new BotDirectMessageListener());
        jda.addEventListener(new OnlinePlayerCommand());
        jda.addEventListener(new HalloCommand());
        jda.setAutoReconnect(true);
        LOGGER.info("Bot started!");
    }
}
