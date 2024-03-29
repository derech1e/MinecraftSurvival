package de.thomas;

import de.thomas.commands.ChunkCommand;
import de.thomas.commands.FlyCommand;
import de.thomas.commands.PingCommand;
import de.thomas.listeners.*;
import de.thomas.utils.Variables;
import de.thomas.utils.chunkloader.ChunkManager;
import de.thomas.utils.config.Configuration;
import de.thomas.utils.crafting.RecipeManager;
import de.thomas.utils.resourcepack.ResourcePack;
import de.thomas.utils.resourcepack.ResourcePackURLData;
import de.thomas.utils.resourcepack.verification.HashingUtil;
import de.thomas.utils.threads.ClockTimeThread;
import de.thomas.utils.threads.RestartThread;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MinecraftSurvival extends JavaPlugin {

    private static MinecraftSurvival INSTANCE;
    public Configuration configuration;
    public ChunkManager chunkManager;

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

        initChunkLoading();

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
        pluginManager.registerEvents(new WorldSaveListener(), this);
        pluginManager.registerEvents(new EntityChangeBlockListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCommand());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(getCommand("chunkloader")).setExecutor(new ChunkCommand());
    }

    private void initChunkLoading() {
        Bukkit.getWorlds().stream().filter(world -> world.getPluginChunkTickets().get(this) != null).forEach(world -> world.getPluginChunkTickets().get(this).forEach(chunk -> chunk.removePluginChunkTicket(this)));
        chunkManager = new ChunkManager();
        chunkManager.initLoading();
    }

    private void registerResourcePack() throws Exception {
        final String url = "https://nuerk-solutions.de/BaguettePack.zip";
        String hash = "C8B9C31CC9E40801E171810A198CFF5F7E911525";

        ResourcePackURLData data = HashingUtil.performPackCheck(url, hash);

        if (!data.match()) {
            this.getLogger().severe("Resource pack Hash does not match");
        }
        Variables.resourcePack = new ResourcePack(url, hash, data.size());
    }
}
