package dev.negativekb.api.commands.shortcommands.provider;

import dev.negativekb.api.commands.Command;
import dev.negativekb.api.commands.SubCommand;
import dev.negativekb.api.commands.shortcommands.ShortCommands;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("all")
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

        boolean b = commandShortCommands.containsKey(command);
        if (b)
            commandShortCommands.replace(command, strings);
        else
            commandShortCommands.put(command, strings);
    }

    @Override
    public void addShortSubCommand(@NotNull SubCommand command, String[] commands) {
        ArrayList<String> strings = subCommandShortCommands.getOrDefault(command, new ArrayList<>());
        Arrays.stream(commands).filter(s -> !strings.contains(s)).forEach(strings::add);

        boolean b = subCommandShortCommands.containsKey(command);
        if (b)
            subCommandShortCommands.replace(command, strings);
        else
            subCommandShortCommands.put(command, strings);
    }

    @Override
    public Optional<Command> getCommand(@NotNull String cmd) {
        Optional<Map.Entry<Command, ArrayList<String>>> entry = commandShortCommands.entrySet()
                .stream()
                .filter(commandArrayListEntry -> commandArrayListEntry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .findFirst();
        return entry.map(Map.Entry::getKey);
    }

    @Override
    public Optional<SubCommand> getSubCommand(@NotNull String cmd) {
        Optional<Map.Entry<SubCommand, ArrayList<String>>> entry = subCommandShortCommands.entrySet()
                .stream()
                .filter(subCommandArrayListEntry -> subCommandArrayListEntry.getValue().stream().anyMatch(s -> s.equalsIgnoreCase(cmd)))
                .findFirst();

        return entry.map(Map.Entry::getKey);
    }
}
