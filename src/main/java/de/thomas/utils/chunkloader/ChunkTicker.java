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
            Method m = playerChunkMap.getClass().getDeclaredMethod("d", chunkCoordsPair.getClass());
            m.setAccessible(true);
            String isInsideRange = m.invoke(playerChunkMap, chunkCoordsPair).toString();
            m.setAccessible(false);
            boolean b = false;
            if (Boolean.parseBoolean(isInsideRange)) {
                b = Boolean.parseBoolean(isInsideRange);
            }

            return !b;
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
