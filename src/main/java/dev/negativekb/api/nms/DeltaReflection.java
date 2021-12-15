package dev.negativekb.api.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.Optional;

@UtilityClass
public class DeltaReflection {

    @SneakyThrows
    public Object getHandle(Player player) {
        return player.getClass().getMethod("getHandle").invoke(player);
    }

    @SneakyThrows
    public GameProfile getGameProfile(Player player) {
        Object handle = getHandle(player);

        return (GameProfile) handle.getClass().getSuperclass().getDeclaredMethod("getProfile").invoke(handle);
    }

    public Optional<Property> getProperty(GameProfile profile, String name) {
        return profile.getProperties().get(name).stream().findFirst();
    }

}
