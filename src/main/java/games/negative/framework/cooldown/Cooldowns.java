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

package games.negative.framework.cooldown;

import games.negative.framework.cooldown.internal.GenericCooldown;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {

    private static final Map<String, Cooldown<?, ?>> cooldownMap = new HashMap<>();

    public static void startInternalCooldowns(JavaPlugin plugin) {
        GenericCooldown cooldown = new GenericCooldown(plugin);
        cooldownMap.put(cooldown.getKey(), cooldown);
    }

    public static void addCooldown(Cooldown<?, ?> cooldown) {
        cooldownMap.put(cooldown.getKey(), cooldown);
    }

    @Nullable
    public static Cooldown<?, ?> getCooldown(String name) {
        return cooldownMap.getOrDefault(name, null);
    }


}
