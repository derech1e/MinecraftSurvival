package de.thomas.utils.config.context;

import java.util.UUID;

public record ChunkData(UUID uuid, org.bukkit.Chunk chunk) {
    public ChunkData() {
        this(UUID.randomUUID(), null);
    }
}
