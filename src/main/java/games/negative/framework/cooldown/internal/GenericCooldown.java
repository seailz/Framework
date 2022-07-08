/*
 *  MIT License
 *
 * Copyright (C) 2022 Negative Games & Developers
 * Copyright (C) 2022 NegativeDev (NegativeKB, Eric)
 * Copyright (C) 2022 Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
