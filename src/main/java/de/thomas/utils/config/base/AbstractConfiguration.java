package de.thomas.utils.config.base;

import com.google.gson.*;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author derech1e
 * @since 10.11.2021
 **/
public abstract class AbstractConfiguration implements JsonConfiguration {

    protected File file;
    protected File folder;
    protected JsonObject root;
    private Gson gson;

    public AbstractConfiguration(File folder, String fileName) {
        this.folder = folder;
        this.file = new File(folder, fileName);
    }

    @Override
    public void init() {
        this.gson = new Gson();
        if (!file.exists()) {
            try {
                Files.createDirectories(folder.toPath());
                Files.createFile(file.toPath());
                addDefaults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void load() {
        this.root = gson.fromJson(FileUtil.read(file), JsonObject.class);
    }

    @Override
    public void save() {
        FileUtil.write(file, gson.toJson(root));
    }

    @Override
    public boolean getBool(String key) {
        return root.get(key).getAsBoolean();
    }

    @Override
    public byte getByte(String key) {
        return root.get(key).getAsByte();
    }

    @Override
    public short getShort(String key) {
        return root.get(key).getAsShort();
    }

    @Override
    public int getInt(String key) {
        return root.get(key).getAsInt();
    }

    @Override
    public long getLong(String key) {
        return root.get(key).getAsLong();
    }

    @Override
    public String getString(String key) {
        return root.get(key).getAsString();
    }

    @Override
    public List<String> getStringList(String key) {
        List<String> result = new ArrayList<>();
        JsonArray array = root.getAsJsonArray(key);
        for (JsonElement element : array)
            if (element instanceof JsonPrimitive && ((JsonPrimitive) element).isString())
                result.add(element.getAsString());
        return result;
    }

    @Override
    public JsonElement getJsonElement(String key) {
        return root.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends JsonConfigSerializable> T get(String key, Class<T> type) {
        JsonObject object = root.getAsJsonObject(key);
        try {
            return (T) type.getDeclaredConstructor().newInstance().deserialize(object);
        } catch (Exception ex) {
            Bukkit.getLogger().severe("Could not deserialize " + key + " to " + type.getSimpleName());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends JsonConfigSerializable> List<T> getList(String key, Class<T> type) {
        List<T> result = new ArrayList<>();
        JsonArray array = root.getAsJsonArray(key);
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            try {
                Method method = type.getMethod("deserialize", JsonObject.class);
                Object rs = method.invoke(null, object);
                if (type.isInstance(rs)) {
                    result.add((T) rs);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void setBool(String key, boolean value) {
        root.addProperty(key, value);
    }

    @Override
    public void setByte(String key, byte value) {
        root.addProperty(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        root.addProperty(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        root.addProperty(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        root.addProperty(key, value);
    }

    @Override
    public void setString(String key, String value) {
        root.addProperty(key, value);
    }

    @Override
    public void setStringList(String key, List<String> value) {
        JsonArray array = new JsonArray();
        for (String string : value) {
            array.add(new JsonPrimitive(string));
        }
        root.add(key, array);
    }

    @Override
    public void setJsonElement(String key, JsonElement value) {
        root.add(key, value);
    }

    @Override
    public void set(String key, JsonConfigSerializable value) {
        JsonObject object = new JsonObject();
        value.serialize(object);
        root.add(key, object);
    }

    @Override
    public void setList(String key, List<? extends JsonConfigSerializable> value) {
        JsonArray array = new JsonArray();
        for (JsonConfigSerializable obj : value) {
            JsonObject object = new JsonObject();
            obj.serialize(object);
            array.add(object);
        }
        root.add(key, array);
    }
}

