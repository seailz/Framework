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

