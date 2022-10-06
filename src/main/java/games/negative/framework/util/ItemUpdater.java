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

package games.negative.framework.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public interface ItemUpdater {

    static ItemUpdater update(ItemStack item) {
        return new ItemUpdater() {

            private final ItemStack is = item;

            @Override
            public ItemUpdater setName(String displayName) {
                ItemMeta meta = is.getItemMeta();
                meta.setDisplayName(Utils.color(displayName));
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater setLore(String... lore) {
                ItemMeta meta = is.getItemMeta();
                meta.setLore(Utils.color(Arrays.asList(lore)));
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater setLore(List<String> lore) {
                ItemMeta meta = is.getItemMeta();
                meta.setLore(Utils.color(lore));
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater addEnchantment(Enchantment enchantment, int level) {
                ItemMeta meta = is.getItemMeta();
                meta.addEnchant(enchantment, level, true);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater removeEnchantment(Enchantment enchantment) {
                ItemMeta meta = is.getItemMeta();
                meta.removeEnchant(enchantment);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater setAmount(int amount) {
                is.setAmount(amount);
                return this;
            }

            @Override
            public ItemUpdater setData(short durability) {
                is.setDurability(durability);
                return this;
            }

            @Override
            public ItemUpdater addItemFlags(ItemFlag... flags) {
                ItemMeta meta = is.getItemMeta();
                meta.addItemFlags(flags);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater removeItemFlags(ItemFlag... flags) {
                ItemMeta meta = is.getItemMeta();
                meta.removeItemFlags(flags);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater addLoreLine(String line) {
                ItemMeta meta = is.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore == null)
                    lore = new ArrayList<>();

                lore.add(Utils.color(line));
                meta.setLore(lore);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater removeLoreLine(int index) {
                ItemMeta meta = is.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore == null)
                    return this;

                lore.remove(index);
                meta.setLore(lore);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater setLoreLine(String line, int index) {
                ItemMeta meta = is.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore == null)
                    return this;

                lore.set(index, Utils.color(line));
                meta.setLore(lore);
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater replaceLore(Consumer<List<String>> function) {
                ItemMeta meta = is.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore == null)
                    return this;

                function.accept(lore);
                meta.setLore(Utils.color(lore));
                is.setItemMeta(meta);
                return this;
            }

            @Override
            public ItemUpdater clone() {
                return ItemUpdater.update(is.clone());
            }
        };
    }

    ItemUpdater setName(String displayName);

    ItemUpdater setLore(String... lore);

    ItemUpdater setLore(List<String> lore);

    ItemUpdater addEnchantment(Enchantment enchantment, int level);

    ItemUpdater removeEnchantment(Enchantment enchantment);

    ItemUpdater setAmount(int amount);

    ItemUpdater setData(short durability);

    ItemUpdater addItemFlags(ItemFlag... flags);

    ItemUpdater removeItemFlags(ItemFlag... flags);

    ItemUpdater addLoreLine(String line);

    ItemUpdater removeLoreLine(int index);

    ItemUpdater setLoreLine(String line, int index);

    ItemUpdater replaceLore(Consumer<List<String>> function);

    ItemUpdater clone();
}
