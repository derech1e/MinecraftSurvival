package de.thomas.utils.chunkloader;

import de.thomas.MinecraftSurvival;
import de.thomas.utils.config.context.ChunkData;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChunkManager {

    private static final List<Chunk> loadedChunks = new ArrayList<>();
    private BukkitTask tickTask;

    public void initLoading() {
        // Enable tick
        tickTask = new BukkitRunnable() {
            @Override
            public void run() {
                // tickTaskRun
                if(loadedChunks.size() != 0) {
                    loadedChunks.forEach(chunk -> {
                        if(!ChunkTicker.isInsideRange(chunk)) {
                            chunk.addPluginChunkTicket(MinecraftSurvival.getINSTANCE());
                            ChunkTicker.tickChunk(chunk);
                        }
                    });
                }
            }
        }.runTaskTimer(MinecraftSurvival.getINSTANCE(), 0L, 1L);
    }

    public void disableTick() {
        try {
            if (tickTask != null) {
                tickTask.cancel();
            }
        } catch (IllegalStateException exception) {
            exception.printStackTrace();
        }
    }

    public void addChunk(Player player, final Chunk chunk) {
        loadedChunks.add(chunk);
        MinecraftSurvival.getINSTANCE().configuration.addChunk(new ChunkData(player.getUniqueId(), chunk));
        chunk.addPluginChunkTicket(MinecraftSurvival.getINSTANCE());
    }

    public void removeChunk(Player player, final Chunk chunk) {
        loadedChunks.remove(chunk);
        MinecraftSurvival.getINSTANCE().configuration.removeChunk(new ChunkData(player.getUniqueId(), chunk));
    }
    public Boolean hasChunk(final Chunk c) {
        return loadedChunks.contains(c);
    }

    public Boolean hasAllChunks(final Set<Chunk> chunks) {
        for (final Chunk c : chunks) {
            if (!loadedChunks.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public Boolean hasNoneChunks(final Set<Chunk> chunks) {
        for (final Chunk c : chunks) {
            if (loadedChunks.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public List<Chunk> getChunks() {
        return loadedChunks;
    }
}
