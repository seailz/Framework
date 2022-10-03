package games.negative.framework.json;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JSONConfigManager {

    /**
     * Loads the json file and if it does not exist create one with the default instance provided
     * @param directory Path to the file
     * @param name Name of the file
     * @param clazz Class of the object
     * @param newInstance Default instance
     * @param gson Gson instance
     * @return Loaded or new config instance
     * @param <T> Type of the object
     */
    @Nullable
    <T> T loadOrCreate(@Nullable String directory, @NotNull String name, @NotNull Class<T> clazz, @NotNull T newInstance, @Nullable Gson gson);

    /**
     * Save a configuration file
     * @param name The name of the configuration file
     * @param config The configuration instance
     * @param <T> The type of the configuration instance
     */
    <T> void save(@Nullable String directory, @NotNull String name, @NotNull T config);

    /**
     * Save a configuration file
     * @param name The name of the configuration file
     * @param config The configuration instance
     * @param gson The Gson instance to use
     * @param <T> The type of the configuration instance
     */
    <T> void save(@Nullable String directory, @NotNull String name, @NotNull T config, @Nullable Gson gson);


}
