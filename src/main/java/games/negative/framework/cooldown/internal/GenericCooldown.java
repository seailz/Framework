package games.negative.framework.cooldown.internal;

import games.negative.framework.cooldown.Cooldown;
import games.negative.framework.util.Task;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GenericCooldown implements Cooldown<UUID, Long> {

    private final String key;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final BukkitTask task;

    public GenericCooldown(JavaPlugin plugin) {
        this.key = "generic";

        this.task = Task.asyncRepeating(plugin, 0, 20, this::tick);
    }

    @Override
    public boolean isOnCooldown(UUID identifier) {
        return cooldowns.containsKey(identifier);
    }

    @Override
    public Long getCooldown(UUID identifier) {
        return cooldowns.get(identifier);
    }

    @Override
    public void setCooldown(UUID identifier, Long value) {
        if (cooldowns.containsKey(identifier))
            cooldowns.replace(identifier, value);
        else
            cooldowns.put(identifier, value);
    }

    @Override
    public void resetCooldown(UUID identifier) {
        cooldowns.remove(identifier);
    }

    @Override
    public void resetCooldowns() {
        cooldowns.clear();
    }

    @Override
    public void tick() {
        cooldowns.entrySet().removeIf(entry -> System.currentTimeMillis() >= entry.getValue());
    }

    @Override
    public Collection<UUID> getCooldowns() {
        return Collections.unmodifiableCollection(cooldowns.keySet());
    }

    @Override
    public Map<UUID, Long> getCooldownEntries() {
        return Collections.unmodifiableMap(cooldowns);
    }

    @Override
    public void stopCooldowns() {
        task.cancel();
    }

    @Override
    public @NotNull String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String key) {
        throw new UnsupportedOperationException("Cannot change key of a cooldown");
    }
}
