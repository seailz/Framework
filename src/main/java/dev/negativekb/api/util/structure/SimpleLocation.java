package dev.negativekb.api.util.structure;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

@Data
public class SimpleLocation {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public Block getBlock() {
        return toLocation().getBlock();
    }

}
