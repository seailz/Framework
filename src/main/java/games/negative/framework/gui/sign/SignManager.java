package games.negative.framework.gui.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import games.negative.framework.BasePlugin;
import games.negative.framework.gui.sign.packets.SignEditorPacket;
import games.negative.framework.util.Utils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the back-end logic for the sign input util
 */
public class SignManager {

    @Getter
    private static final HashMap<Player, SignGUI> input = new HashMap<>();

    @Getter
    private static final ProtocolManager protocol;

    static {
        protocol = ProtocolLibrary.getProtocolManager();
    }

    /**
     * Opens the sign GUI for the given player.
     *
     * @param player  The player to open the GUI for.
     * @param signGUI THe gui to open
     * @author FrostedSnowman & Seailz
     */
    public static void open(Player player, SignGUI signGUI) {
        SignEditorPacket signEditorPacket = new SignEditorPacket();
        PacketContainer signDataPacket = protocol.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        signEditorPacket.setLocation(new BlockPosition(0, 0, 0));

        player.sendBlockChange(new Location(player.getWorld(), 0, 0, 0), Material.SIGN, (byte) 0);
        ;

        NbtCompound nbt = (NbtCompound) signDataPacket.getNbtModifier().read(0);
        ArrayList<String> lines = new ArrayList<>();

        signGUI.getLines().forEach(line -> {
            lines.add(line.getText().apply(player));
        });

        for (String line : lines) {
            nbt.put("Text" + (line + 1), lines.size() > lines.indexOf(line) ? String.format("{\"text\":\"%s\"}", Utils.color(line)) : "");
        }

        nbt.put("id", "minecraft:sign");

        signDataPacket.getBlockPositionModifier().write(0, new BlockPosition(0, 0, 0));
        signDataPacket.getIntegers().write(0, 9);
        signDataPacket.getNbtModifier().write(0, nbt);

        sendPacket(player, signDataPacket);
        sendPacket(player, signEditorPacket.getHandle());

        input.put(player, signGUI);
    }

    /**
     * Creates the packet listener
     */
    public void listen() {
        protocol.addPacketListener(new PacketAdapter(BasePlugin.getInst(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player p = event.getPlayer();
                if (input.containsKey(p)) {
                    SignGUI signGUI = input.get(p);
                    String[] lines = event.getPacket().getStringArrays().read(0);
                    signGUI.getOnClose().accept(p, lines);
                    input.remove(p);
                }
            }
        });
    }

    /**
     * Sends a packet with ProtocolLib
     *
     * @param player The player to send the packet to.
     * @param packet The packet to send.
     */
    private static void sendPacket(Player player, PacketContainer packet) {
        try {
            protocol.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
