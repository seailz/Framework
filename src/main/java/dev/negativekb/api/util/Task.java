package dev.negativekb.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class Task {

    /**
     * Run a task asynchronously
     * @param plugin {@link JavaPlugin} instance
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask async(@NotNull JavaPlugin plugin, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Run a delayed task asynchronously
     * @param plugin {@link JavaPlugin} instance
     * @param delay Delay in ticks
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask asyncDelayed(@NotNull JavaPlugin plugin, final long delay, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    /**
     * Run a repeating task asynchronously
     * @param plugin {@link JavaPlugin} instance
     * @param delay Delay in ticks
     * @param interval Interval the task gets repeated in ticks
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask asyncRepeating(@NotNull JavaPlugin plugin, final long delay, final long interval, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, interval);
    }

    /**
     * Run a task
     * @param plugin {@link JavaPlugin} instance
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask task(@NotNull JavaPlugin plugin, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTask(plugin, runnable);
    }

    /**
     * Run a delayed task
     * @param plugin {@link JavaPlugin} instance
     * @param delay Delay in ticks
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask taskDelayed(@NotNull JavaPlugin plugin, final long delay, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Run a repeating task
     * @param plugin {@link JavaPlugin} instance
     * @param delay Delay in ticks
     * @param interval Interval the task gets repeated in ticks
     * @param runnable {@link Runnable} instance
     * @return {@link BukkitTask} instance
     */
    public BukkitTask taskRepeating(@NotNull JavaPlugin plugin, final long delay, final long interval, @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, interval);
    }

}
