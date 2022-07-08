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

package games.negative.framework.util.structure;

import games.negative.framework.util.UtilPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class CuboidRegion {

    @NotNull
    private String name;
    private int priority;
    @NotNull
    private SimpleLocation position1;
    @NotNull
    private SimpleLocation position2;

    @NotNull
    public Collection<Player> getPlayers() {
        Collection<Player> collection = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> UtilPlayer.isInside(player.getLocation(), position1.toLocation(), position2.toLocation()))
                .forEach(collection::add);

        return collection;
    }

    @NotNull
    public Collection<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();

        Location loc1 = position1.toLocation();
        Location loc2 = position2.toLocation();
        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return new ArrayList<>(blocks);
    }


    @Override
    public String toString() {
        return "CuboidRegion{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", position1=" + position1 +
                ", position2=" + position2 +
                '}';
    }
}
