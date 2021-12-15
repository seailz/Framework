package dev.negativekb.api.util.version;

import lombok.Getter;
import org.bukkit.Bukkit;

public class VersionChecker {

    @Getter
    private static VersionChecker instance;
    @Getter
    private final ServerVersion serverVersion;

    public VersionChecker() {
        instance = this;
        serverVersion = ServerVersion.fromServerPackageName(Bukkit.getServer().getClass().getName());
    }

    public boolean isLegacy() {
        return serverVersion.isAtMost(ServerVersion.V1_12); // Could also return !isModern()
    }

    public boolean isModern() {
        return serverVersion.isAtLeast(ServerVersion.V1_13);
    }

}
