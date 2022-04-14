package games.negative.framework.gui.base;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Represents the base functions of a menu.
 *
 * @author Negative
 * @since 2.0.0
 */
public interface MenuBase {

    void setItem(int index, @NotNull Function<Player, ItemStack> function);

    void setItemClickEvent(int index, @NotNull Function<Player, ItemStack> function, @Nullable BiConsumer<Player, InventoryClickEvent> clickFunction);

    void addItem(@NotNull Function<Player, ItemStack> function);

    void addItemClickEvent(@NotNull Function<Player, ItemStack> function, @Nullable BiConsumer<Player, InventoryClickEvent> clickFunction);

    void clearSlot(int index);

    void refresh(@NotNull Player player);

    void open(@NotNull Player player);

    void onOpen(BiConsumer<Player, InventoryOpenEvent> function);

    void onClose(BiConsumer<Player, InventoryCloseEvent> function);

    void onInventoryClick(BiConsumer<Player, InventoryClickEvent> function);
}
