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

package games.negative.framework.gui.holder;

import games.negative.framework.gui.GUI;
import games.negative.framework.gui.base.MenuHolder;
import games.negative.framework.gui.internal.MenuItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Getter
@Setter
public class GUIHolder implements MenuHolder<GUI> {

    private final GUI gui;
    private Inventory inventory;

    @Override
    public void onOpen(@NotNull Player player, @NotNull InventoryOpenEvent event) {
        BiConsumer<Player, InventoryOpenEvent> onOpen = gui.getOnOpen();
        if (onOpen != null) onOpen.accept(player, event);
    }

    @Override
    public void onClose(@NotNull Player player, @NotNull InventoryCloseEvent event) {
        BiConsumer<Player, InventoryCloseEvent> onClose = gui.getOnClose();
        if (onClose != null) onClose.accept(player, event);

        gui.getActiveInventories().remove(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (!gui.isAllowTakeItems())
            event.setCancelled(true);

        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
            BiConsumer<Player, InventoryClickEvent> playerClick = gui.getPlayerInventoryClickEvent();
            if (playerClick != null)
                playerClick.accept((Player) event.getWhoClicked(), event);
            return;
        }

        int slot = event.getSlot();

        MenuItem item = gui.getItems().stream().filter(menuItem -> menuItem.getSlot() == slot).findFirst().orElse(null);
        if (item == null)
            return;

        BiConsumer<Player, InventoryClickEvent> click = item.getClickEvent();
        if (click == null)
            return;

        click.accept((Player) event.getWhoClicked(), event);
    }

    @Override
    public @NotNull GUI getMenu() {
        return gui;
    }

}
