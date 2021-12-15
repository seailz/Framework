package dev.negativekb.api.gui.internal;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Data
public class MenuItem {

    private final int slot;
    private final Function<Player, ItemStack> item;
    @Nullable
    private final BiConsumer<Player, InventoryClickEvent> clickEvent;

}
