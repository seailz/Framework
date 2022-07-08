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

package games.negative.framework.command.shortcommand.provider;

import games.negative.framework.command.Command;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.shortcommand.ShortCommands;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShortCommandsProvider extends ShortCommands {

    private final HashMap<Command, ArrayList<String>> commandShortCommands = new HashMap<>();
    private final HashMap<SubCommand, ArrayList<String>> subCommandShortCommands = new HashMap<>();

    public ShortCommandsProvider() {
        setInstance(this);
    }

    @Override
    public void addShortCommand(@NotNull Command command, String[] commands) {
        ArrayList<String> strings = commandShortCommands.getOrDefault(command, new ArrayList<>());
        Arrays.stream(commands).filter(s -> !strings.contains(s)).forEach(strings::add);

        if (commandShortCommands.containsKey(command))
            commandShortCommands.replace(command, strings);
        else
            commandShortCommands.put(command, strings);
    }

    @Override
    public void addShortSubCommand(@NotNull SubCommand command, String[] commands) {
        ArrayList<String> strings = subCommandShortCommands.getOrDefault(command, new ArrayList<>());
        Arrays.stream(commands).filter(s -> !strings.contains(s)).forEach(strings::add);

        if (subCommandShortCommands.containsKey(command))
            subCommandShortCommands.replace(command, strings);
        else
            subCommandShortCommands.put(command, strings);
    }

    @Override
    public Optional<Command> getCommand(@NotNull String cmd) {
        return commandShortCommands.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .map(Map.Entry::getKey).findFirst();
    }

    @Override
    public Optional<SubCommand> getSubCommand(@NotNull String cmd) {
        return subCommandShortCommands.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .map(Map.Entry::getKey).findFirst();
    }
}
