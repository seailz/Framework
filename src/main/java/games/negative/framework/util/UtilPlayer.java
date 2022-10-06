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

package games.negative.framework.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.UUID;

@UtilityClass
public class UtilPlayer {

    /**
     * Resets the player to default stats
     *
     * @param player Player
     */
    public void reset(@NotNull Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
        player.setWalkSpeed(0.2F);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getOpenInventory().close();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setExp(0);
        player.setLevel(0);
        player.setFireTicks(0);
        player.setGameMode(GameMode.SURVIVAL);
        if (player.getVehicle() != null)
            player.leaveVehicle();
        if (player.getPassenger() != null)
            player.getPassenger().leaveVehicle();

    }

    /**
     * Checks if a location is in-between 2 locations
     *
     * @param loc Location 1
     * @param l1  Location 2
     * @param l2  Location 3
     * @return true or false
     */
    public boolean isInside(@NonNull Location loc, @NonNull Location l1, @NonNull Location l2) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());

        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    /**
     * Checks if a provided player's inventory is full
     *
     * @param player Player
     * @return Returns true if the player's inventory is full, returns false if it is not.
     */
    public boolean isInventoryFull(@NonNull Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    /**
     * Get a UUID by a Name from Mojang's Servers
     *
     * @param name Name
     * @return UUID of the Player
     * @apiNote Requires org.json to work!
     */
    @Nullable
    public UUID getUUIDByName(String name) {
        JSONObject jsonObject = UtilHTTP.getJSONObjectFromMojang("https://api.mojang.com/users/profiles/minecraft/" + name);
        if (jsonObject == null)
            return null;

        return UUID.fromString(jsonObject.getString("id"));
    }

    /**
     * Get an IGN through a UUID from Mojang's servers
     *
     * @param uuid UUID
     * @return IGN of the UUID
     * @apiNote Requires org.json to work!
     */
    @Nullable
    public String getNameByUUID(UUID uuid) {
        JSONObject jsonObject = UtilHTTP.getJSONObjectFromMojang("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString());
        if (jsonObject == null)
            return null;

        return jsonObject.getString("name");
    }


}
