package de.thomas.utils.chunkloader;

import de.thomas.MinecraftSurvival;
import de.thomas.utils.config.context.ChunkData;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    private List<Chunk> loadedChunks = new ArrayList<>();
    private BukkitTask tickTask;

    public void initLoading() {
        loadedChunks = MinecraftSurvival.getINSTANCE().configuration.getLoadedChunks().stream().map(ChunkData::chunk).toList();;

        loadedChunks.forEach(chunk -> {
            chunk.addPluginChunkTicket(MinecraftSurvival.getINSTANCE());
            chunk.setForceLoaded(true);
        });
        // Enable tick
        tickTask = new BukkitRunnable() {
            @Override
            public void run() {
                // tickTaskRun
                if(loadedChunks.size() != 0) {
                    loadedChunks.forEach(chunk -> {
                        if(!ChunkTicker.isInsideRange(chunk)) {
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
        chunk.setForceLoaded(true);
        chunk.addPluginChunkTicket(MinecraftSurvival.getINSTANCE());
    }

    public void removeChunk(Player player, final Chunk chunk) {
        loadedChunks.remove(chunk);
        MinecraftSurvival.getINSTANCE().configuration.removeChunk(new ChunkData(player.getUniqueId(), chunk));
        chunk.setForceLoaded(false);
        chunk.removePluginChunkTicket(MinecraftSurvival.getINSTANCE());
    }

    public List<Chunk> getChunks() {
        return loadedChunks;
    }
}
