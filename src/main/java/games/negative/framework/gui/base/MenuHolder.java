package games.negative.framework.gui.base;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a menu holder.
 *
 * @author Negative
 * @version 2.0.0
 */
public interface MenuHolder<T extends MenuBase> extends InventoryHolder {

    void onOpen(@NotNull Player player, @NotNull InventoryOpenEvent event);

    void onClose(@NotNull Player player, @NotNull InventoryCloseEvent event);

    void onClick(InventoryClickEvent event);

    @NotNull
    T getMenu();

}
