package de.thomas.utils.config.base;

import com.google.gson.JsonObject;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public interface JsonConfigSerializable {

    void serialize(JsonObject object);

    Object deserialize(JsonObject object);
}
