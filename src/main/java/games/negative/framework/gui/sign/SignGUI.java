package games.negative.framework.gui.sign;

import games.negative.framework.gui.sign.exception.ProtocolLibNotInstalledException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Custom SignGUI inputs
 * @author Seailz
 */
@Getter
@Setter
public class SignGUI {

    private ArrayList<SignLine> lines;
    private BiConsumer<Player, String[]> onClose;

    public SignGUI() {
        lines = new ArrayList<>();
    }

    /**
     * Sets a line of the sign
     * @param index The line number (0-3)
     * @param line The line text
     */
    public void setLine(int index, Function<Player, String> line) {
        if (index <= 3)
            lines.set(index, new SignLine(line));
    }

    /**
     * Opens the sign GUI
     * @param player The player to open the GUI for
     * @throws ProtocolLibNotInstalledException If ProtocolLib is not installed
     */
    public void open(Player player) throws ProtocolLibNotInstalledException {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib"))
            throw new ProtocolLibNotInstalledException();
        SignManager.open(player, this);
    }

    public void onClose(BiConsumer<Player, String[]> function) {
        setOnClose(function);
    }




}
