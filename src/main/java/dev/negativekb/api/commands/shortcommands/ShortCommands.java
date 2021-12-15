package dev.negativekb.api.commands.shortcommands;

import dev.negativekb.api.commands.Command;
import dev.negativekb.api.commands.SubCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public abstract class ShortCommands {

    @Getter @Setter
    private static ShortCommands instance;

    public abstract void addShortCommand(Command command, String[] commands);

    public abstract void addShortSubCommand(SubCommand command, String[] commands);

    public abstract Optional<Command> getCommand(String cmd);

    public abstract Optional<SubCommand> getSubCommand(String cmd);
}
