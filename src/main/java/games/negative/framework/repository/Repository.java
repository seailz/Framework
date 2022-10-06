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

package games.negative.framework.repository;

import java.util.Collection;
import java.util.function.Function;

/**
 * Represents an object that can be stored in a repository/cache.
 * This class provides the basic functionality of a repository.
 *
 * @param <T> The type of the entity
 * @author Negative
 * @since 2.1.0
 */
public interface Repository<T> {

    /**
     * Adds an entry to the repository.
     *
     * @param entry The entry to add
     */
    void add(T entry);

    /**
     * Removes an entry from the repository.
     *
     * @param entry The entry to remove
     */
    void remove(T entry);

    /**
     * Removes all entries from the repository.
     *
     * @param entries The entries to remove
     */
    void removeAll(Collection<T> entries);

    /**
     * Removes all entries from the repository which meet the given condition.
     *
     * @param function The condition to meet
     */
    void removeAll(Function<T, Boolean> function);

    /**
     * Returns a collection of all entries that meet the given condition.
     *
     * @param function The condition to meet
     * @return A collection of all entries that meet the given condition
     */
    Collection<T> get(Function<T, Boolean> function);

    /**
     * Returns a collection of all entries in the repository.
     *
     * @return A collection of all entries in the repository
     */
    Collection<T> getAll();

    /**
     * Validates if the provided entry is in the repository.
     *
     * @param entry The entry to validate
     * @return True if the entry is in the repository, false otherwise
     */
    boolean contains(T entry);

    /**
     * Validates if there is any entry in the repository that meets the given condition.
     *
     * @param function The condition to meet
     * @return True if there is any entry in the repository that meets the given condition, false otherwise
     */
    boolean contains(Function<T, Boolean> function);

    /**
     * Validates if the repository is empty.
     *
     * @return True if the repository is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the size of the repository.
     *
     * @return The size of the repository
     */
    int size();

    /**
     * Clears the repository.
     */
    void clear();

    /**
     * Clears the repository of the entries that meet the given condition.
     *
     * @param function The condition to meet
     */
    void clear(Function<T, Boolean> function);

}
