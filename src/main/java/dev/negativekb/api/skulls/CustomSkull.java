package dev.negativekb.api.skulls;

import dev.negativekb.api.nms.SkullReflections;
import dev.negativekb.api.util.version.VersionChecker;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class CustomSkull {

    @NotNull
    private final String name;
    @NotNull
    private final String link;
    private final Base64 base64 = new Base64();

    @NotNull
    public ItemStack toItem() {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", link).getBytes());
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
}
