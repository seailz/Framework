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

@Getter
@Setter
@RequiredArgsConstructor
public class HopperGUIHolder implements InventoryHolder {

    private final HopperGUI gui;
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
