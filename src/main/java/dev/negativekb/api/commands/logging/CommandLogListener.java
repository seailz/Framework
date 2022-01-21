package dev.negativekb.api.commands.logging;

import dev.negativekb.api.commands.events.CommandLogEvent;
import dev.negativekb.api.commands.events.SubCommandLogEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CommandLogListener implements Listener {

    @EventHandler
    public void onCommandLog(CommandLogEvent event) {
        event.getCommand().getLogEvent().accept(event);
    }

    @EventHandler
    public void onSubCommandLog(SubCommandLogEvent event) {
        event.getCommand().getSubCommandLogEvent().accept(event);
    }
}
