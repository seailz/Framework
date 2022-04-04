package games.negative.framework.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import games.negative.framework.nms.SkullReflections;
import games.negative.framework.util.version.VersionChecker;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

@ApiStatus.Experimental
public interface CustomSkull {

    @NotNull
    String link();

    default ItemStack getItemStack() {
        Base64 base64 = new Base64();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        Validate.notNull(propertyMap, "Profile property map cannot be null");

        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", link()).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head;
        if (VersionChecker.getInstance().isModern())
            head = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        else
            head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        ItemMeta headMeta = head.getItemMeta();
        Class<?> headMetaClass = headMeta.getClass();
        SkullReflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        head.setItemMeta(headMeta);
        return head;
    }

    @NotNull
    static CustomSkull of(@NotNull String link) {
        return () -> link;
    }

    @NotNull
    static CustomSkull create(@NotNull String link) {
        return of(link);
    }
}
