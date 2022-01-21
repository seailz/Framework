package dev.negativekb.api.commands;

import dev.negativekb.api.commands.annotation.CommandInfo;
import dev.negativekb.api.commands.events.SubCommandLogEvent;
import dev.negativekb.api.commands.shortcommands.ShortCommands;
import dev.negativekb.api.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

/**
 * SubCommand
 *
 * @author Negative
 * @apiNote Must be added to a {@link Command} class in order to work!
 */
@Getter @Setter
public abstract class SubCommand {

    // subcommands of subcommands lol
    @Getter
    private final List<SubCommand> subCommands = new ArrayList<>();

    protected final Message cannotUseThis;
    protected final Message commandDisabled;
    protected final Message noPerm;

    private String argument;
    private List<String> aliases;
    private String permission = "";
    private boolean consoleOnly = false;
    private boolean playerOnly = false;
    private boolean disabled;
    private String[] params;
    private Consumer<SubCommandLogEvent> subCommandLogEventConsumer;


    public SubCommand() {
        this(null, null);
    }

    /**
     * SubCommand Constructor
     *
     * @param argument Argument of the SubCommand
     * @apiNote SubCommand argument and aliases are equalsIgnoreCase!
     * @apiNote There are no aliases for this constructor!
     */
    public SubCommand(String argument) {
        this(argument, Collections.emptyList());
    }

    /**
     * SubCommand Constructor
     *
     * @param argument Argument of the SubCommand
     * @param aliases  Aliases of the SubCommand
     * @apiNote SubCommand argument and aliases are equalsIgnoreCase!
     */
    public SubCommand(String argument, List<String> aliases) {
        this.argument = argument;
        this.aliases = aliases;
        
        cannotUseThis = new Message("&cYou cannot use this!");
        commandDisabled = new Message("&cThis command is currently disabled.");
        noPerm = new Message("&cYou do not have permission to use this.");

        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            CommandInfo annotation = this.getClass().getAnnotation(CommandInfo.class);
            setArgument(annotation.name());

            List<String> a = new ArrayList<>(Arrays.asList(annotation.aliases()));
            // There will always be an empty index even if no arguments are
            // set. So the way you identify if there are actual arguments in the command
            // is you check if the first index is empty.
            if (!a.get(0).isEmpty()) {
                setAliases(a);
            }

            if (!annotation.permission().isEmpty())
                setPermission(annotation.permission());

            List<String> shortCmds = new ArrayList<>(Arrays.asList(annotation.shortCommands()));
            if (!shortCmds.get(0).isEmpty()) {
                ShortCommands.getInstance().addShortSubCommand(this, annotation.shortCommands());
            }

            List<String> paramList = new ArrayList<>(Arrays.asList(annotation.args()));
            if (!paramList.get(0).isEmpty()) {
                this.params = annotation.args();
            }
        }
    }

    public void execute(CommandSender sender, String[] args) {
        // If the Command is disabled, send this message

        if (isDisabled()) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            commandDisabled.send(sender);
            return;
        }

        if (isPlayerOnly() && !(sender instanceof Player)) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            cannotUseThis.send(sender);
            return;
        }

        if (isConsoleOnly() && sender instanceof Player) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            cannotUseThis.send(sender);
            return;
        }

        // If the permission node is not null and not empty
        // but, if the user doesn't have permission for the command
        // send this message
        if (!getPermission().isEmpty()) {
            if (!sender.hasPermission(getPermission())) {
                boolean cancelled = runLogEvent(this, sender, args);
                if (cancelled)
                    return;

                noPerm.send(sender);
                return;
            }
        }

        // Checks if the SubCommand SubCommands are empty (subcommand seption)
        // if so, execute regular command
        List<SubCommand> subCommands = getSubCommands();
        if (args.length == 0 || subCommands.isEmpty()) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            if (params != null && (args.length < params.length)) {
                StringBuilder builder = new StringBuilder();
                for (String param : params) {
                    builder.append("<").append(param).append(">").append(" ");
                }
                sender.sendMessage(ChatColor.RED + "Usage: /" + this.getArgument() + " " + builder);
                return;
            }
            runCommand(sender, args);
            return;
        }

        // Gets args 0
        String arg = args[0];
        // Removes args 0
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
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            if (params != null && (args.length < params.length)) {
                StringBuilder builder = new StringBuilder();
                for (String param : params) {
                    builder.append("<").append(param).append(">").append(" ");
                }
                sender.sendMessage(ChatColor.RED + "Usage: /" + this.getArgument() + " " + builder);
                return;
            }
            runCommand(sender, args);
        }
    }

    public abstract void runCommand(CommandSender sender, String[] args);

    /**
     * Adds SubCommand to the SubCommand's subcommands
     * SubCommand seption
     *
     * @param subCommands SubCommand(s)
     */
    public void addSubCommands(SubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    /**
     * Runs a SubCommand
     *
     * @param subCommand SubCommand
     * @param sender     Sender
     * @param args       Arguments
     */
    private void runSubCommand(SubCommand subCommand, CommandSender sender, String[] args) {
        subCommand.execute(sender, args);
    }

    /**
     * Checks to see if a Player is online
     *
     * @param name Name
     * @return Optional
     */
    public Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    private boolean runLogEvent(SubCommand command, CommandSender sender, String[] args) {
        if (subCommandLogEventConsumer == null)
            return false;

        SubCommandLogEvent event = new SubCommandLogEvent(sender, args, command);
        Bukkit.getPluginManager().callEvent(event);

        return event.isCancelled();
    }

    public Consumer<SubCommandLogEvent> getSubCommandLogEvent() {
        return subCommandLogEventConsumer;
    }
}
