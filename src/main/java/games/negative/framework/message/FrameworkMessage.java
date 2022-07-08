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

package games.negative.framework.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum FrameworkMessage {

    COMMAND_NO_PERMISSION(Collections.singletonList(
            "&cYou do not have permission to use this command."
    )),

    COMMAND_DISABLED(Collections.singletonList(
            "&cThis command is currently disabled."
    )),

    COMMAND_CANNOT_USE_THIS_AS_PLAYER(Collections.singletonList(
            "&cYou cannot use this command as a player."
    )),

    COMMAND_CANNOT_USE_THIS_AS_CONSOLE(Collections.singletonList(
            "&cYou cannot use this command as a console."
    )),

    COMMAND_USAGE(Collections.singletonList(
            "&cUsage: &7/%command% %usage%"
    )),

    ;
    private final List<String> defaultMessage;
    private Message message;

    public static void init() {
        Arrays.stream(values()).forEach(message -> message.message = new Message(message.defaultMessage));
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void send(Iterable<CommandSender> players) {
        message.send(players);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(String o1, String o2) {
        return message.replace(o1, o2);
    }
}
