/*
 *     DeltaCore is a Minecraft Java API Provider.
 *     Copyright (C) 2021 DeltaDevelopment
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
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
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();
        base.onClose((Player) event.getPlayer(), event);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();
        base.onOpen((Player) event.getPlayer(), event);
    }
}
