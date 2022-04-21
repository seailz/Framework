package games.negative.framework.command.repository;

import games.negative.framework.command.Command;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FrameworkCommandRepository implements CommandRepository {

    private final Set<Command> repository = new HashSet<>();

    @Override
    public void add(Command entry) {
        repository.add(entry);
    }

    @Override
    public void remove(Command entry) {
        repository.remove(entry);
    }

    @Override
    public void removeAll(Collection<Command> entries) {
        repository.removeAll(entries);
    }

    @Override
    public void removeAll(Function<Command, Boolean> function) {
        repository.removeIf(function::apply);
    }

    @Override
    public Collection<Command> get(Function<Command, Boolean> function) {
        return repository.stream().filter(function::apply).collect(Collectors.toList());
    }

    @Override
    public Collection<Command> getAll() {
        return repository;
    }

    @Override
    public boolean contains(Command entry) {
        return repository.contains(entry);
    }

    @Override
    public boolean contains(Function<Command, Boolean> function) {
        return repository.stream().anyMatch(function::apply);
    }

    @Override
    public boolean isEmpty() {
        return repository.isEmpty();
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public void clear() {
        repository.clear();
    }

    @Override
    public void clear(Function<Command, Boolean> function) {
        repository.removeIf(function::apply);
    }
}
