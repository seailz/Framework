package games.negative.framework.json.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import games.negative.framework.BasePlugin;
import games.negative.framework.json.JSONConfigManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class JSONConfigManagerProvider implements JSONConfigManager {

    private final BasePlugin plugin;
    private final Gson defaultGson;

    public JSONConfigManagerProvider(BasePlugin plugin) {
        this.plugin = plugin;
        this.defaultGson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    @Override
    public <T> @Nullable T loadOrCreate(@Nullable String path, @NotNull String name, @NotNull Class<T> clazz, @NotNull T newInstance, @Nullable Gson gson) {
        Gson localGson = (gson == null ? defaultGson : gson);

        File configDir = (path == null ? new File(plugin.getDataFolder(), "configs") : new File(path));
        if (!configDir.exists())
            configDir.mkdirs();

        File configFile = new File(configDir, (name.endsWith(".json") ? name : name + ".json"));
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();

                try (Writer writer = new FileWriter(configFile)) {
                    localGson.toJson(newInstance, writer);
                    writer.flush();
                    writer.close();
                    return newInstance;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try (Reader reader = new FileReader(configFile)) {
                return localGson.fromJson(reader, clazz);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public <T> void save(@Nullable String directory, @NotNull String name, @NotNull T config) {
        save(directory, name, config, null);
    }

    @Override
    public <T> void save(@Nullable String directory, @NotNull String name, @NotNull T config, @Nullable Gson gson) {
        Gson localGson = (gson == null ? defaultGson : gson);
        File configDir = (directory == null ? new File(plugin.getDataFolder(), "configs") : new File(directory));
        if (!configDir.exists())
            configDir.mkdirs();

        File configFile = new File(configDir, (name.endsWith(".json") ? name : name + ".json"));
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Writer writer = new FileWriter(configFile)) {
            localGson.toJson(config, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
