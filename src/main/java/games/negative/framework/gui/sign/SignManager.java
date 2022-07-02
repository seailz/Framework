package games.negative.framework.gui.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import games.negative.framework.BasePlugin;
import games.negative.framework.gui.sign.packets.SignEditorPacket;
import games.negative.framework.util.Utils;
import games.negative.framework.util.version.VersionChecker;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    public static void open(@NotNull Player player, @NotNull SignGUI signGUI) {
        VersionChecker versionChecker = new VersionChecker();
        SignEditorPacket signEditorPacket = new SignEditorPacket();
        PacketContainer signDataPacket = protocol.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        signEditorPacket.setLocation(new BlockPosition(0, 0, 0));

        if (versionChecker.isModern())
            player.sendBlockChange(new Location(player.getWorld(), 0, 0, 0), Material.SIGN, (byte) 0);
        else if (versionChecker.isLegacy())
            player.sendBlockChange(new Location(player.getWorld(), 0, 0, 0), Material.valueOf("OAK_SIGN"), (byte) 0);

        NbtCompound nbt = (NbtCompound) signDataPacket.getNbtModifier().read(0);
        ArrayList<String> lines = new ArrayList<>();

        signGUI.getLines().forEach(line -> {
            if (line.getText() != null)
                lines.add(line.getText().apply(player));
            else
                lines.add("");
        });

        for (String line : lines) {
            nbt.put("Text" + (line + 1), lines.size() > lines.indexOf(line) ? String.format("{\"text\":\"%s\"}", Utils.color(line)) : "");
        }

        String itemName = versionChecker.isModern() ? "minecraft:sign" : "minecraft:oak_sign";
        nbt.put("id", itemName);

        signDataPacket.getBlockPositionModifier().write(0, new BlockPosition(0, 0, 0));
        signDataPacket.getIntegers().write(0, 9);
        signDataPacket.getNbtModifier().write(0, nbt);

        sendPacket(player, signDataPacket);
        sendPacket(player, signEditorPacket.getHandle());

        input.put(player, signGUI);

        if (input.get(player).getOnClose() != null)
            listen();
    }

    /**
     * Creates the packet listener
     */
    private static void listen() {
        protocol.addPacketListener(new PacketAdapter(BasePlugin.getInst(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player p = event.getPlayer();
                if (input.containsKey(p)) {
                    SignGUI signGUI = input.get(p);
                    StructureModifier<String> values = event.getPacket().getStrings();

                    // String[] lines = new String[]{values.read(1), values.read(2), values.read(3), values.read(4)};
                    WrappedChatComponent[] lines = event.getPacket().getChatComponentArrays().read(0);
                    String[] linesStrings = new String[]{lines[0].getJson(), lines[1].getJson(), lines[2].getJson(), lines[3].getJson()};
                    signGUI.getOnClose().accept(p, linesStrings);
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
    private static void sendPacket(@NotNull Player player, @NotNull PacketContainer packet) {
        try {
            protocol.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
