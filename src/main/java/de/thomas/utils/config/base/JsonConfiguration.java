package de.thomas.utils.config.base;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public interface JsonConfiguration {

    void init();

    void load();

    void save();

    void addDefaults();

    boolean getBool(String key);

    byte getByte(String key);

    short getShort(String key);

    int getInt(String key);

    long getLong(String key);

    String getString(String key);

    List<String> getStringList(String key);

    JsonElement getJsonElement(String key);

    <T extends JsonConfigSerializable> T get(String key, Class<T> type);

    <T extends JsonConfigSerializable> List<T> getList(String key, Class<T> type);

    void setBool(String key, boolean value);

    void setByte(String key, byte value);

    void setShort(String key, short value);

    void setInt(String key, int value);

    void setLong(String key, long value);

    void setString(String key, String value);

    void setStringList(String key, List<String> value);

    void setJsonElement(String key, JsonElement value);

    void set(String key, JsonConfigSerializable value);

    void setList(String key, List<? extends JsonConfigSerializable> value);
}
