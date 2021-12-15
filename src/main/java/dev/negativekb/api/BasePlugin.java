package dev.negativekb.api;

import dev.negativekb.api.commands.shortcommands.provider.ShortCommandsListener;
import dev.negativekb.api.gui.GUIListener;
import dev.negativekb.api.util.FileLoader;
import dev.negativekb.api.util.version.VersionChecker;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public abstract class BasePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new VersionChecker();

        registerListeners(
                new GUIListener(),
                new ShortCommandsListener()
        );

    }

    /**
     * Load custom files with pre-written data.
     *
     * @param plugin Plugin instance
     * @param names  File names
     */
    public void loadFiles(@NonNull JavaPlugin plugin, @NonNull String... names) {
        File dataFolder = plugin.getDataFolder();
        Arrays.stream(names).forEach(name -> {
            File file = new File(dataFolder, name);
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);

            if (!file.exists())
                FileLoader.loadFile(plugin, name);

            try {
                fileConfig.load(file);
            } catch (Exception e3) {
                e3.printStackTrace();
            }

            fileConfig.getKeys(false).forEach(priceString ->
                    fileConfig.set(priceString, fileConfig.getString(priceString)));
        });

    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void registerCommands(@NonNull Command... commands) {
        VersionChecker versionChecker = VersionChecker.getInstance();

        Server server = Bukkit.getServer();
        Field field = server.getClass().getDeclaredField("commandMap");
        field.setAccessible(true);

        Arrays.stream(commands).forEach(iCommand -> {
            try {
                CommandMap commandMap = (CommandMap) field.get(server);

                String name = iCommand.getName();

                org.bukkit.command.Command command = commandMap.getCommand(name);
                if (command != null) {
                    Map<String, Command> map;
                    if (versionChecker.isLegacy()) {
                        Field commandField = commandMap.getClass().getDeclaredField("knownCommands");
                        commandField.setAccessible(true);
                        map = (Map<String, Command>) commandField.get(commandMap);
                    } else {
                        map = (Map<String, Command>) commandMap.getClass().getDeclaredMethod("getKnownCommands").invoke(commandMap);
                    }
                    command.unregister(commandMap);
                    map.remove(name);
                    iCommand.getAliases().forEach(map::remove);
                }

                commandMap.register(name, iCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Registers Listener classes
     *
     * @param listeners Listeners
     */
    public void registerListeners(@NonNull Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

}
