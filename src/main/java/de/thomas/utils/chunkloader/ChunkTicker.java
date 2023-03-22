package de.thomas.utils.chunkloader;

import org.bukkit.Chunk;
import org.bukkit.GameRule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChunkTicker {

    protected static void tickChunk(Chunk chunk) {
        try {
            Object chunkObject = chunk.getClass().getMethod("getHandle").invoke(chunk);
            Object worldServer = chunkObject.getClass().getField("q").get(chunkObject);
            Integer randomTickSpeed = chunk.getWorld().getGameRuleValue(GameRule.RANDOM_TICK_SPEED);
            if (randomTickSpeed == null) {
                throw new IllegalAccessException("Cannot read RANDOM_TICK_SPEED gamerule");
            }

            worldServer.getClass().getMethod("a", chunkObject.getClass(), Integer.TYPE).invoke(worldServer, chunkObject, randomTickSpeed);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }

    protected static boolean isInsideRange(Chunk chunk) {
        try {
            Object chunkObject = chunk.getClass().getMethod("getHandle").invoke(chunk);
            Object worldServer = chunkObject.getClass().getField("q").get(chunkObject);
            Object chunkProvider = worldServer.getClass().getMethod("k").invoke(worldServer);
            Object playerChunkMap = chunkProvider.getClass().getField("a").get(chunkProvider);
            Object chunkCoordsPair = chunkObject.getClass().getMethod("f").invoke(chunkObject);
            Method method = playerChunkMap.getClass().getDeclaredMethod("d", chunkCoordsPair.getClass());
            method.setAccessible(true);
            String isInsideRange = method.invoke(playerChunkMap, chunkCoordsPair).toString();
            method.setAccessible(false);

            return Boolean.parseBoolean(isInsideRange);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
