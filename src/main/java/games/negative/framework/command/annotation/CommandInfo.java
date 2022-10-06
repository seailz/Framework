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

package games.negative.framework.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CommandInfo
 * <p>
 * The ability to initialize your command without multiple
 * annotations or using the super methods
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * Name of the command
     *
     * @return name of the command
     */
    String name();

    /**
     * Aliases of the command
     *
     * @return aliases of the command
     */
    String[] aliases() default "";

    /**
     * The permission required to execute the command
     *
     * @return permission required to execute the command
     */
    String permission() default "";

    /**
     * The description of the command
     *
     * @return description of the command
     */
    String description() default "";

    /**
     * If the command is only for the console
     *
     * @return if the command is only for the console
     */
    boolean consoleOnly() default false;

    /**
     * If the command is only for players
     *
     * @return if the command is only for players
     */
    boolean playerOnly() default false;

    /**
     * If the command is disabled
     *
     * @return if the command is disabled
     */
    boolean disabled() default false;

    /**
     * The shortcommand alias of the command
     * <p>
     * This is intended to be a shortcut for the command
     * For example if you have a command called "gamemode creative" and you want to use "gmc" as a shortcut
     * You can do this by having a short command called "gmc"
     *
     * @return shortcommand alias of the command
     */
    String[] shortCommands() default "";

    /**
     * Arguments of the command
     * <p>
     * This is intended to remove the redundancy of checking the arguments of the command for a certain length.
     *
     * @return arguments of the command
     */
    String[] args() default "";

}
