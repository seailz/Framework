package dev.negativekb.api.commands.shortcommands;

import dev.negativekb.api.commands.Command;
import dev.negativekb.api.commands.SubCommand;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ShortCommands {

    @Getter @Setter
    private static ShortCommands instance;

    public abstract void addShortCommand(@NotNull Command command, @NotNull String[] commands);

    public abstract void addShortSubCommand(@NotNull SubCommand command, @NotNull String[] commands);

    public abstract Optional<Command> getCommand(@NotNull String cmd);

    public abstract Optional<SubCommand> getSubCommand(@NotNull String cmd);
}
