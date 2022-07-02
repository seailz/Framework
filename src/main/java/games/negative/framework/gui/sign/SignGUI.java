package games.negative.framework.gui.sign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    private BiConsumer<Player, String> onClose;

    public SignGUI() {
        lines = new ArrayList<>();
    }

    public void setLine(int index, Function<Player, String> line) {
        lines.set(index, new SignLine(line));
    }

    public void open(Player player) {

    }

    public void onClose(BiConsumer<Player, String> function) {
        setOnClose(function);
    }




}
