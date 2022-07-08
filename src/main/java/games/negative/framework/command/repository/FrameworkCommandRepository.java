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

package games.negative.framework.command.repository;

import org.bukkit.command.Command;

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
