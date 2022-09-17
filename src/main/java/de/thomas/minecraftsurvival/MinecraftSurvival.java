package de.thomas.minecraftsurvival;

import de.thomas.commands.PingCommand;
import de.thomas.listeners.*;
import de.thomas.utils.Variables;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.config.context.BaguetteContext;
import de.thomas.utils.crafting.RecipeManager;
import de.thomas.utils.resourcepack.ResourcePack;
import de.thomas.utils.resourcepack.base.HashingUtil;
import de.thomas.utils.resourcepack.base.verification.ResourcePackURLData;
import de.thomas.utils.threads.ClockTimeThread;
import de.thomas.utils.threads.RestartThread;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MinecraftSurvival extends JavaPlugin {

    private static MinecraftSurvival INSTANCE;
    public Configuration configuration;
    public static MinecraftSurvival getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        configuration = new Configuration(this);
        configuration.init();
        configuration.load();

        registerCommands();
        registerListeners();


        //Load Recipes
        RecipeManager.registerRecipes(getServer());

        //Load Threads
        new RestartThread().startThread();
        new ClockTimeThread().startThread();

        try {
            registerResourcePack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        RecipeManager.unregisterRecipes(getServer());
        configuration.save();
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerBedListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerPortalListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerItemConsumeListener(), this);
        pluginManager.registerEvents(new CraftItemListener(), this);
        pluginManager.registerEvents(new ResourcePackListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCommand());
    }

    private void registerResourcePack() throws Exception {
        final String url = "http://132.145.251.78/BaguettePack.zip";
        String hash = "E8457657FA79204A3DA747B2E0470B7CD13B6F78";

        ResourcePackURLData data = HashingUtil.performPackCheck(url, hash);
        this.getLogger().warning(HashingUtil.getHashFromUrl(url));

        if(!data.match()) {
            this.getLogger().warning(data.getUrlHash());
            this.getLogger().warning("Resourcepack Hash does not match");
        }
        Variables.resourcePack = new ResourcePack(url, hash, data.getSize());
    }
}
