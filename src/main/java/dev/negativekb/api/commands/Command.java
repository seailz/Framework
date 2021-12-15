/*
 *       DeltaAPI is a Minecraft Java API.
 *       Copyright (C) 2021 DeltaDevelopment
 *
 *       This program is free software; you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation; either version 2 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 */

package dev.negativekb.api.commands;

import dev.negativekb.api.commands.annotation.CommandInfo;
import dev.negativekb.api.commands.shortcommands.ShortCommands;
import dev.negativekb.api.message.Message;
import dev.negativekb.api.nms.DeltaReflection;
import dev.negativekb.api.util.UtilPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class Command extends org.bukkit.command.Command {
    @Getter
    private final List<SubCommand> subCommands = new ArrayList<>();
    private final Message cannotUseThis;
    private final Message commandDisabled;
    private final Message noPerm;
    @Getter
    @Setter
    public boolean consoleOnly = false;
    @Getter
    @Setter
    public boolean playerOnly = false;
    @Getter
    @Setter
    public boolean disabled = false;
    @Getter
    @Setter
    public String permissionNode = "";
    private String[] params;
    private TabCompleter completer;

    public Command() {
        this("1");
    }

    public Command(@NotNull String name) {
        this(name, "", Collections.emptyList());
    }

    public Command(@NotNull String name, @NotNull String description) {
        this(name, description, Collections.emptyList());
    }

    public Command(@NotNull String name, @NotNull Collection<String> aliases) {
        this(name, "", aliases);
    }

    public Command(@NotNull String name, @NotNull String description, @NotNull Collection<String> aliases) {
        super(name, description, "/" + name, new ArrayList<>(aliases));

        cannotUseThis = new Message("&cYou cannot use this!");
        commandDisabled = new Message("&cThis command is currently disabled");
        noPerm = new Message("&cYou do not have permission to use this!");

        boolean hasInfo = getClass().isAnnotationPresent(CommandInfo.class);
        if (hasInfo) {
            CommandInfo annotation = getClass().getAnnotation(CommandInfo.class);
            setName(annotation.name());

            if (annotation.consoleOnly())
                setConsoleOnly(true);

            if (annotation.playerOnly())
                setPlayerOnly(true);

            if (annotation.disabled())
                setDisabled(true);

            if (!annotation.description().isEmpty())
                setDescription(annotation.description());

            List<String> a = new ArrayList<>(Arrays.asList(annotation.aliases()));
            // There will always be an empty index even if no arguments are
            // set. So the way you identify if there are actual arguments in the command
            // is you check if the first index is empty.
            if (!a.get(0).isEmpty()) {
                setAliases(a);
            }

            if (!annotation.permission().isEmpty())
                setPermissionNode(annotation.permission());

            List<String> shortCmds = new ArrayList<>(Arrays.asList(annotation.shortCommands()));
            if (!shortCmds.get(0).isEmpty()) {
                ShortCommands.getInstance().addShortCommand(this, annotation.shortCommands());
            }

            List<String> paramList = new ArrayList<>(Arrays.asList(annotation.args()));
            if (!paramList.get(0).isEmpty()) {
                this.params = annotation.args();
            }
        }

    }

    public abstract void onCommand(CommandSender sender, String[] args);

    public void onCommand(CommandSender sender, String label, String[] args) {
        onCommand(sender, args);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        // If the Command is disabled, send this message
        if (isDisabled()) {
            commandDisabled.send(sender);
            return true;
        }

        if (isPlayerOnly() && !(sender instanceof Player)) {
            cannotUseThis.send(sender);
            return true;
        }

        if (isConsoleOnly() && sender instanceof Player) {
            cannotUseThis.send(sender);
            return true;
        }

        // If the permission node is not null and not empty
        // but, if the user doesn't have permission for the command
        // send this message
        String permNode = getPermissionNode();
        if (!permNode.isEmpty() && !sender.hasPermission(permNode)) {
            noPerm.send(sender);
            return true;
        }


        // If there are no SubCommands for this Command
        // execute the regular command
        List<SubCommand> subCommands = getSubCommands();
        if (args.length == 0 || subCommands.isEmpty()) {
            if (params != null && (args.length < params.length)) {
                StringBuilder builder = new StringBuilder();
                for (String param : params) {
                    builder.append("<").append(param).append(">").append(" ");
                }
                sender.sendMessage(ChatColor.RED + "Usage: /" + this.getName() + " " + builder);
                return true;
            }
            onCommand(sender, label, args);
            return true;
        }

        // Gets argument 0
        String arg = args[0];
        // Removes argument 0
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        Optional<SubCommand> command = subCommands.stream().filter(subCmd -> {
            if (subCmd.getArgument().equalsIgnoreCase(arg))
                return true;

            List<String> aliases = subCmd.getAliases();
            if (aliases == null || aliases.isEmpty())
                return false;

            return aliases.contains(arg.toLowerCase());
        }).findFirst();

        if (command.isPresent())
            runSubCommand(command.get(), sender, newArgs);
        else {
            if (params != null && (args.length < params.length)) {
                StringBuilder builder = new StringBuilder();
                for (String param : params) {
                    builder.append("<").append(param).append(">").append(" ");
                }
                sender.sendMessage(ChatColor.RED + "Usage: /" + this.getName() + " " + builder);
                return true;
            }
            onCommand(sender, label, args);
        }
        return true;
    }

    /**
     * Run SubCommand Function
     *
     * @param subCommand SubCommand
     * @param sender     Player/Sender
     * @param args       Arguments
     */
    private void runSubCommand(SubCommand subCommand, CommandSender sender, String[] args) {
        subCommand.execute(sender, args);
    }

    /**
     * @param sender - Sender of the command. Usually a player
     * @param perm   - Permission node
     * @return - Returns whether the player has the permission provided in the "perm" String
     * @deprecated Never used and kind of useless.
     */
    @Deprecated
    public boolean hasPermission(CommandSender sender, String perm) {
        Server server = Bukkit.getServer();
        Player p = server.getPlayer(sender.getName());
        if (p == null) {
            return server.getConsoleSender().hasPermission(perm);
        } else {
            return p.hasPermission(perm);
        }
    }

    public void ifHasPermission(@NotNull CommandSender sender, @NotNull String perm, @NotNull Consumer<CommandSender> consumer) {
        if (sender.hasPermission(perm))
            consumer.accept(sender);
    }

    public void ifNotHasPermission(@NotNull CommandSender sender, @NotNull String perm, @NotNull Consumer<CommandSender> consumer) {
        if (!sender.hasPermission(perm))
            consumer.accept(sender);
    }

    public void ifPlayer(@NotNull CommandSender sender, @NotNull Consumer<Player> consumer) {
        if (sender instanceof Player)
            consumer.accept((Player) sender);
    }

    public void ifConsole(@NotNull CommandSender sender, @NotNull Consumer<ConsoleCommandSender> consumer) {
        if (sender instanceof ConsoleCommandSender)
            consumer.accept((ConsoleCommandSender) sender);
    }

    /**
     * Checks if a player is online
     *
     * @param name Player Name
     * @return Optional Player
     */
    public Optional<Player> getPlayer(@NotNull String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    /**
     * Get the {@link UUID} of the {@link String} name
     * @param name Name of the {@link OfflinePlayer} that you want to retrieve
     * @return {@link UUID} of the {@link OfflinePlayer} that you want to retrieve
     */
    public UUID getOfflineUUID(@NotNull String name) {
        return UtilPlayer.getUUIDByName(name);
    }

    /**
     * Get the {@link OfflinePlayer} of the {@link String} name
     * @param name Name of the {@link OfflinePlayer} you want to retrieve
     * @return {@link OfflinePlayer} of the {@link String} name you input
     */
    public OfflinePlayer getOfflinePlayerByName(@NotNull String name) {
        return Bukkit.getOfflinePlayer(getOfflineUUID(name));
    }

    /**
     * Add one or more SubCommands to
     * a command
     *
     * @param subCommands SubCommand(s)
     */
    public void addSubCommands(SubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    public void setTabComplete(BiFunction<CommandSender, String[], List<String>> function) {
        this.completer = (sender, command, alias, args) -> {
            if (alias.equalsIgnoreCase(getName()) || getAliases().contains(alias.toLowerCase())) {
                return function.apply(sender, args);
            }
            return null;
        };
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (completer == null || this.completer.onTabComplete(sender, this, alias, args) == null) {
            String lastWord = args[args.length - 1];
            Player senderPlayer = sender instanceof Player ? (Player) sender : null;
            ArrayList<String> matchedPlayers = new ArrayList<>();
            sender.getServer().getOnlinePlayers().stream()
                    .filter(player -> senderPlayer == null || senderPlayer.canSee(player) && StringUtil.startsWithIgnoreCase(player.getName(), lastWord))
                    .forEach(player -> matchedPlayers.add(player.getName()));

            matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
            return matchedPlayers;
        }
        return this.completer.onTabComplete(sender, this, alias, args);
    }
}
