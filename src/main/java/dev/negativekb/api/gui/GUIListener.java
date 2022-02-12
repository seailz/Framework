/*
 * MIT License
 *
 * Copyright (c) 2022 Negative
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

package dev.negativekb.api.gui;

import dev.negativekb.api.gui.internal.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

public class GUIListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // Chest GUI
        if (holder instanceof BaseGUI) {
            BaseGUI base = (BaseGUI) holder;

            if (!base.getGui().isAllowTakeItems())
                event.setCancelled(true);

            if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                GUI gui = base.getGui();
                Optional.ofNullable(gui.getPlayerInventoryClickEvent()).ifPresent(function ->
                        function.accept((Player) event.getWhoClicked(), event));
                return;
            }

            int slot = event.getSlot();
            Optional<MenuItem> first = base.getGui().getItems().stream()
                    .filter(menuItem -> menuItem.getSlot() == slot)
                    .findFirst();

            first.flatMap(menuItem -> Optional.ofNullable(menuItem.getClickEvent()))
                    .ifPresent(function -> function.accept((Player) event.getWhoClicked(), event));
        }

        // Hopper GUI
        if (holder instanceof HopperGUIHolder) {
            HopperGUIHolder base = (HopperGUIHolder) holder;
            if (!base.getGui().isAllowTakeItems())
                event.setCancelled(true);

            if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                HopperGUI gui = base.getGui();
                Optional.ofNullable(gui.getPlayerInventoryClickEvent()).ifPresent(function ->
                        function.accept((Player) event.getWhoClicked(), event));
                return;
            }

            int slot = event.getSlot();
            Optional<MenuItem> first = base.getGui().getItems().stream()
                    .filter(menuItem -> menuItem.getSlot() == slot)
                    .findFirst();

            first.flatMap(menuItem -> Optional.ofNullable(menuItem.getClickEvent()))
                    .ifPresent(function -> function.accept((Player) event.getWhoClicked(), event));
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof BaseGUI) {
            BaseGUI base = (BaseGUI) holder;
            base.onClose((Player) event.getPlayer(), event);
        }

        if (holder instanceof HopperGUIHolder) {
            HopperGUIHolder base = (HopperGUIHolder) holder;
            base.onClose((Player) event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof BaseGUI) {
            BaseGUI base = (BaseGUI) holder;
            base.onOpen((Player) event.getPlayer(), event);
        }

        if (holder instanceof HopperGUIHolder) {
            HopperGUIHolder base = (HopperGUIHolder) holder;
            base.onOpen((Player) event.getPlayer(), event);
        }
    }
}
