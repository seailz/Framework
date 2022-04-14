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
