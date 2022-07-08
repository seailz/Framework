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
