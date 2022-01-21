package dev.negativekb.api.commands.events;

import dev.negativekb.api.commands.Command;
import dev.negativekb.api.event.PluginEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
@Setter
public class CommandLogEvent extends PluginEvent {

    private final CommandSender sender;
    private final String[] arguments;
    private final Command command;
    private boolean cancelled;

    public Player getPlayer() throws ClassCastException {
        return (Player) sender;
    }

}
