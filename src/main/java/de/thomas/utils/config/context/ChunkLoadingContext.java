package de.thomas.utils.config.context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.thomas.utils.Variables;
import de.thomas.utils.config.base.JsonConfigSerializable;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ChunkLoadingContext(List<ChunkData> chunkDataList) implements JsonConfigSerializable {

    public ChunkLoadingContext() {
        this(new ArrayList<>());
    }

    @Override
    public ChunkLoadingContext deserialize(JsonObject object) {
        object.get("loadedChunks").getAsJsonArray().forEach(chunk -> {
            JsonObject chunkObject = chunk.getAsJsonObject();
            System.out.println(chunkObject);
            String uuid = chunkObject.get("playerId").getAsString();
            Chunk actualChunk = Variables.stringToChunk(chunkObject.get("chunkData").getAsString());
            chunkDataList.add(new ChunkData(UUID.fromString(uuid), actualChunk));
        });
        return new ChunkLoadingContext(chunkDataList);
    }

    @Override
    public void serialize(JsonObject object) {
        JsonArray chunks = new JsonArray();
        this.chunkDataList.forEach(chunkData -> {
            JsonObject chunkObject = new JsonObject();
            chunkObject.addProperty("playerId", chunkData.uuid().toString());
            chunkObject.addProperty("chunkData", Variables.chunkToString(chunkData.chunk()));
            chunks.add(chunkObject);
        });
        object.add("loadedChunks", chunks);
    }
}