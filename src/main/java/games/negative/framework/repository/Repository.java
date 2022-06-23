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
