package games.negative.framework.key;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a class which needs to be identified by a key.
 *
 * @param <T> The type of the key.
 */
public interface Keyd<T> {

    /**
     * Gets the key.
     *
     * @return The key.
     */
    @NotNull
    T getKey();

    /**
     * Sets the key.
     *
     * @param key The new key.
     */
    void setKey(@NotNull T key);

}
