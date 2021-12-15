package dev.negativekb.api.util.data;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * TriConsumer
 * Primarily meant for {@link BiMap}.
 *
 * @param <T>  Type
 * @param <U>  Value 1
 * @param <U1> Value 2
 */
@FunctionalInterface
public interface TriConsumer<T, U, U1> {

    void accept(@NotNull T t, @NotNull U u, @NotNull U1 u1);

    default TriConsumer<T, U, U1> andThen(@NotNull TriConsumer<? super T, ? super U, ? super U1> after) {
        Objects.requireNonNull(after);

        return (l, r, r1) -> {
            accept(l, r, r1);
            after.accept(l, r, r1);
        };
    }
}
