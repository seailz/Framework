package dev.negativekb.api.commands.shortcommands.provider;

import dev.negativekb.api.commands.shortcommands.ShortCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class ShortCommandsListener implements Listener {

    private final ShortCommands shortCommands;

    public ShortCommandsListener() {
        new ShortCommandsProvider();

        shortCommands = ShortCommands.getInstance();
    }

    @EventHandler
    public void onCommandInput(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        message = message.replaceFirst("/", "");
        String[] args = message.split(" ");
        String cmd = args[0];
        shortCommands.getCommand(cmd).ifPresent(command -> {
            if (event.isCancelled())
                return;

            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            command.execute(event.getPlayer(), null, newArgs);

            event.setCancelled(true);
        });

        shortCommands.getSubCommand(cmd).ifPresent(subCommand -> {
            if (event.isCancelled())
                return;

            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            subCommand.execute(event.getPlayer(), newArgs);

            event.setCancelled(true);
        });
    }

}
