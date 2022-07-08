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

package games.negative.framework.util.proxy;

import games.negative.framework.BasePlugin;
import games.negative.framework.util.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Seailz
 * @description Use this class to manage Bungeecord proxies throughout your spigot plugin
 */
public class BungeeManager {

    private final BasePlugin main;

    public BungeeManager(BasePlugin plugin) {
        this.main = plugin;
    }

    /**
     * Connect a player to a server!
     *
     * @param player The player that you would like to connect.
     * @param server The server that you would like the Player to be connected to.
     * @throws IOException if {@code server} is {@code null}
     */
    public void connect(@NotNull Player player, @NotNull String server) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(main, "BungeeCord", b.toByteArray());
    }

    /**
     * Send a message to a player on the proxy
     *
     * @param player  The player whom you want to send a message to
     * @param message The message you want to send
     * @throws IOException if {@code player} is offline
     */
    public void sendMessage(@NotNull Player player, @NotNull String message) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        out.writeUTF("Message");
        out.writeUTF(player.getName());
        out.writeUTF(Utils.color(message));
        player.sendPluginMessage(main, "BungeeCord", b.toByteArray());
    }

}

