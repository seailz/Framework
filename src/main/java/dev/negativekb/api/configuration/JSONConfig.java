package dev.negativekb.api.configuration;

import dev.negativekb.api.util.ForceSavable;
import dev.negativekb.api.util.Reloadable;
import dev.negativekb.api.util.cache.ObjectHashCache;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.LinkedHashMap;

public abstract class JSONConfig extends ObjectHashCache<String, Object> implements Reloadable, ForceSavable {

    private LinkedHashMap<String, Object> map;
    @SneakyThrows
    public JSONConfig(String path) {
        super(path);

        map = load();

        generateDefaults();
    }

    public abstract void addDefaults();

    @SneakyThrows
    private void generateDefaults() {
        if (!map.isEmpty())
            return;

        addDefaults();
        save(map);
    }

    public <T> void add(String key, T value) {
        map.putIfAbsent(key, value);
    }

    public <T> void replace(String key, T value) {
        map.replace(key, value);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        Object object = getOrDefault(key, defaultValue);
        return (String) object;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        Object object = getOrDefault(key, defaultValue);
        return (int) object;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        Object object = getOrDefault(key, defaultValue);
        return (double) object;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        Object object = getOrDefault(key, defaultValue);
        return (long) object;
    }

    public <T> T getType(String key) {
        return getType(key, null);
    }

    public <T> T getType(String key, T defaultValue) {
        return (T) getOrDefault(key, defaultValue);
    }

    public Object get(String key) {
        return getOrDefault(key, null);
    }

    public Object getOrDefault(String key, Object defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public Collection<String> keys() {
        return map.keySet();
    }

    public LinkedHashMap<String, Object> entries() {
        return map;
    }

    @SneakyThrows
    @Override
    public void reload() {
        map = load();
    }

    @SneakyThrows
    @Override
    public void forceSave() {
        save(map);
    }
}
