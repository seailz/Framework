package dev.negativekb.api.util;

import dev.negativekb.api.util.structure.SimpleLocation;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class SimpleLocationParser {

    public SimpleLocation toSimpleLocation(@NotNull Location location) {
        return new SimpleLocation(location.getWorld().getName(), location.getX(),
                location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Location fromSimpleLocation(@NotNull SimpleLocation location) {
        return location.toLocation();
    }
}
