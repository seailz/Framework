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

package games.negative.framework.cooldown;

import games.negative.framework.key.Keyd;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A cooldown is a timer that can be used to limit the rate of an action.
 *
 * @param <I> The identifier of the cooldown.
 * @param <V> The value of the cooldown.
 */
public interface Cooldown<I, V extends Comparable<V>> extends Keyd<String> {

    /**
     * Validates if the identifier is on the cooldown.
     *
     * @param identifier The identifier of the cooldown.
     * @return True if the identifier is on the cooldown.
     */
    boolean isOnCooldown(I identifier);

    /**
     * Gets the value of the cooldown.
     *
     * @param identifier The identifier of the cooldown.
     * @return The value of the cooldown.
     */
    V getCooldown(I identifier);

    /**
     * Sets the value of the cooldown.
     *
     * @param identifier The identifier of the cooldown.
     * @param value      The value of the cooldown.
     */
    void setCooldown(I identifier, V value);

    /**
     * Resets the cooldown.
     *
     * @param identifier The identifier of the cooldown.
     */
    void resetCooldown(I identifier);

    /**
     * Resets all cooldowns.
     */
    void resetCooldowns();

    /**
     * Ticks the cooldown.
     */
    void tick();

    /**
     * Gets the identifiers of the cooldowns.
     *
     * @return The identifiers of the cooldowns.
     */
    Collection<I> getCooldowns();

    /**
     * Gets the cooldowns.
     *
     * @return The cooldowns.
     */
    Map<I, V> getCooldownEntries();

    /**
     * Code to be ran when you want to stop the cooldown task.
     */
    void stopCooldowns();

    default boolean ifOnCooldown(I identifier, Consumer<I> consumer) {
        if (isOnCooldown(identifier)) {
            consumer.accept(identifier);
            return true;
        }
        return false;
    }

}
