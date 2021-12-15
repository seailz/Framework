package dev.negativekb.api.util;

import dev.negativekb.api.nms.DeltaReflection;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.Optional;

@UtilityClass
public class MigrationCheck {

    @SneakyThrows
    public boolean check(Player player) {
        GameProfile profile = DeltaReflection.getGameProfile(player);
        Optional<Property> capeProperties = DeltaReflection.getProperty(profile, "CAPE");

        boolean present = capeProperties.isPresent();
        if (!present)
            return false;

        Property capeProperty = capeProperties.get();
        return capeProperty.getSignature().equalsIgnoreCase("http://textures.minecraft.net/texture/2340c0e03dd24a11b15a8b33c2a7e9e32abb2051b2481d0ba7defd635ca7a933");
    }

}
