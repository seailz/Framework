package games.negative.framework.repository;

import java.util.Collection;
import java.util.function.Function;

public interface Repository<T> {

    void add(T entry);

    void remove(T entry);

    void removeAll(Collection<T> entries);

    void removeAll(Function<T, Boolean> function);

    Collection<T> get(Function<T, Boolean> function);

    Collection<T> getAll();

    boolean contains(T entry);

    boolean contains(Function<T, Boolean> function);

    boolean isEmpty();

    int size();

    boolean isFull();

    void clear();

    void clear(Function<T, Boolean> function);

}
