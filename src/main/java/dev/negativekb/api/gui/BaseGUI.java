/*
 *       DeltaAPI is a Minecraft Java API.
 *       Copyright (C) 2021 DeltaDevelopment
 *
 *       This program is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 2 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 */

package dev.negativekb.api.gui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

@RequiredArgsConstructor
public class BaseGUI implements InventoryHolder {

    @Getter
    private final GUI gui;

    @Getter
    @Setter
    private Inventory inventory;

    public void onOpen(Player player, InventoryOpenEvent event) {
        Optional.ofNullable(gui.getOnOpen()).ifPresent(function ->
                function.accept(player, event));
    }

    public void onClose(Player player, InventoryCloseEvent event) {
        Optional.ofNullable(gui.getOnClose()).ifPresent(closeFunction ->
                closeFunction.accept(player, event));

        gui.getActiveInventories().remove(player);
    }

}
