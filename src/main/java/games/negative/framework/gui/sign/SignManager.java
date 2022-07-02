package games.negative.framework.gui.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import games.negative.framework.BasePlugin;
import games.negative.framework.gui.sign.packets.SignEditorPacket;
import games.negative.framework.util.Utils;
import games.negative.framework.util.version.VersionChecker;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
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

    @Getter
    @Setter
    private static BlockPosition location;

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
        // SignEditorPacket signEditorPacket = new SignEditorPacket();
        PacketContainer signDataPacket = protocol.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);

        setLocation(new BlockPosition((int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ()));

        if (!versionChecker.isModern())
            player.sendBlockChange(new Location(player.getWorld(), 0, 0, 0), Material.SIGN, (byte) 0);
        else if (!versionChecker.isLegacy())
            player.sendBlockChange(new Location(player.getWorld(), 0, 0, 0), Material.valueOf("OAK_SIGN"), (byte) 0);

        // signEditorPacket.setLocation(location);

        ArrayList<String> lines = new ArrayList<>();

        signGUI.getLines().forEach(line -> {
            if (line.getText() != null)
                lines.add(line.getText().apply(player));
            else
                lines.add("");
        });



        // String itemName = versionChecker.isModern() ? "minecraft:sign" : "minecraft:oak_sign";

        signDataPacket.getBlockPositionModifier().write(0, location);

        PacketContainer update = protocol.createPacket(PacketType.Play.Server.UPDATE_SIGN);

        PacketContainer block = protocol.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
        block.getBlockPositionModifier().write(0, location);
        block.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
        update.getBlockPositionModifier().write(0, location);
        update.getChatComponentArrays().write(0, wrap(lines));

        sendPacket(player, signDataPacket);
        sendPacket(player, update);
        // sendPacket(player, signEditorPacket.getHandle());

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

    private static WrappedChatComponent[] wrap(ArrayList<String> lines) {
        WrappedChatComponent[] wrappedLines = new WrappedChatComponent[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            wrappedLines[i] = WrappedChatComponent.fromText(lines.get(i));
        }
        return wrappedLines;
    }
}
