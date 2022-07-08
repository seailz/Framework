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

import games.negative.framework.command.shortcommand.ShortCommands;
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
