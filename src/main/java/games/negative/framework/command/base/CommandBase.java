package games.negative.framework.command.base;

import games.negative.framework.BasePlugin;
import games.negative.framework.command.Command;
import games.negative.framework.command.SubCommand;
import games.negative.framework.message.FrameworkMessage;
import games.negative.framework.util.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The base class for all commands.
 */
public interface CommandBase {

    /**
     * Gets the command
     *
     * @return The command
     */
    Command getCommand();

    /**
     * Executes the command.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    void onCommand(CommandSender sender, String[] args);

    /**
     * Runs a subcommand
     *
     * @param subCommand The subcommand to run.
     * @param sender     The sender of the command.
     * @param args       The arguments of the command.
     */
    void runSubCommand(SubCommand subCommand, CommandSender sender, String[] args);

    /**
     * If the sender has the permission to run the command, execute some code
     *
     * @param sender   The sender of the command.
     * @param perm     The permission to check.
     * @param consumer The code to execute if the sender has the permission.
     */
    void ifHasPermission(@NotNull CommandSender sender, @NotNull String perm, @NotNull Consumer<CommandSender> consumer);

    /**
     * If the sender does not have permission to run the command, execute some code
     *
     * @param sender   The sender of the command.
     * @param perm     The permission to check.
     * @param consumer The code to execute if the sender does not have the permission.
     */
    void ifNotHasPermission(@NotNull CommandSender sender, @NotNull String perm, @NotNull Consumer<CommandSender> consumer);

    /**
     * If the sender is a player, run some code.
     *
     * @param sender   The sender of the command.
     * @param consumer The code to execute if the sender is a player.
     */
    void ifPlayer(@NotNull CommandSender sender, @NotNull Consumer<Player> consumer);

    /**
     * If the sender is the console, run some code.
     *
     * @param sender   The sender of the command.
     * @param consumer The code to execute if the sender is the console.
     */
    void ifConsole(@NotNull CommandSender sender, @NotNull Consumer<ConsoleCommandSender> consumer);

    /**
     * Gets a player by a name.
     *
     * @param name The name of the player.
     * @return The player.
     * @throws NullPointerException If the player is not found.
     */
    @Nullable
    default Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    /**
     * Add subcommands to the command.
     *
     * @param subCommands The subcommands to add.
     */
    void addSubCommands(SubCommand... subCommands);

    /**
     * Get the parent of the command.
     *
     * @return The parent of the command.
     */
    @Nullable
    CommandBase getParent();

    /**
     * Set the parent of the command.
     *
     * @param parent The parent of the command.
     */
    void setParent(@NotNull CommandBase parent);

    /**
     * Get the name of the command.
     *
     * @return The name of the command.
     */
    @NotNull
    String getName();

    /**
     * Validates if the command is disabled.
     *
     * @return If the command is disabled.
     */
    boolean isDisabled();

    /**
     * Validates if the command is player-only.
     *
     * @return If the command is player-only.
     */
    boolean isPlayerOnly();

    /**
     * Validates if the command is console-only.
     *
     * @return If the command is console-only.
     */
    boolean isConsoleOnly();

    /**
     * The command cooldown
     *
     * The Integer value is the amount of ticks the command is on cooldown for.
     * @return The command cooldown.
     */
    Function<CommandSender, Integer> getCooldown();

    /**
     * Get the permission of the command.
     *
     * @return The permission of the command.
     * @throws NullPointerException If the permission is not set.
     */
    @Nullable
    String getPermission();

    /**
     * Get the parameters of the command.
     *
     * @return The parameters of the command.
     */
    String[] getParams();

    /**
     * Get the subcommands of the command.
     *
     * @return The subcommands of the command.
     */
    @NotNull
    List<SubCommand> getSubCommands();

    /**
     * Code to validate everything before execution of the command.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    default void execute(CommandSender sender, String[] args) {
        if (isDisabled()) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            FrameworkMessage.COMMAND_DISABLED.send(sender);
            return;
        }

        // Runs cooldown logic
        if (getCooldown().apply(sender) > 0 && getCooldown() != null) {
            Map.Entry<CommandSender, Command> entry = new Map.Entry<CommandSender, Command>() {
                @Override
                public CommandSender getKey() {
                    return sender;
                }

                @Override
                public Command getValue() {
                    return getCommand();
                }

                @Override
                public Command setValue(Command value) {
                    return null;
                }
            };

            int time = getCooldown().apply(sender);
            if (BasePlugin.getInst().getCommandCooldown().get(entry) != null) {
                FrameworkMessage.COMMAND_COOLDOWN.replace("%cooldown%", BasePlugin.getInst().getCommandCooldown().get(entry).toString()).send(sender);
                return;
            }

            BasePlugin.getInst().getCommandCooldown().put(entry, time);

            // Runs the cooldown task, which depletes the saved time by 1 every tick.
            Task.asyncRepeating(BasePlugin.getInst(), 1, 1, new BukkitRunnable() {
                @Override
                public void run() {
                    if (BasePlugin.getInst().getCommandCooldown().get(entry) == 0) {
                        BasePlugin.getInst().getCommandCooldown().remove(entry);
                        this.cancel();
                        return;
                    }

                    int time = BasePlugin.getInst().getCommandCooldown().get(entry);
                    BasePlugin.getInst().getCommandCooldown().remove(entry);
                    BasePlugin.getInst().getCommandCooldown().put(entry, time - 1);
                }
            });
        }

        if (isPlayerOnly() && !(sender instanceof Player)) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            FrameworkMessage.COMMAND_CANNOT_USE_THIS_AS_CONSOLE.send(sender);
            return;
        }

        if (isConsoleOnly() && sender instanceof Player) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            FrameworkMessage.COMMAND_CANNOT_USE_THIS_AS_PLAYER.send(sender);
            return;
        }

        // If the permission node is not null and not empty
        // but, if the user doesn't have permission for the command
        // send this message
        if (getPermission() != null && !getPermission().isEmpty()) {
            if (!sender.hasPermission(getPermission())) {
                boolean cancelled = runLogEvent(this, sender, args);
                if (cancelled)
                    return;

                FrameworkMessage.COMMAND_NO_PERMISSION.send(sender);
                return;
            }
        }

        // Checks if the SubCommand SubCommands are empty (subcommand seption)
        // if so, execute regular command
        List<SubCommand> subCommands = getSubCommands();
        String[] params = getParams();
        if (args.length == 0 || subCommands.isEmpty()) {
            boolean cancelled = runLogEvent(this, sender, args);
            if (cancelled)
                return;

            if (params != null && (args.length < params.length)) {
                sendParamMessage(params, sender);
                return;
            }
            onCommand(sender, args);
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
                sendParamMessage(params, sender);
                return;
            }
            onCommand(sender, args);
        }
    }

    default void sendParamMessage(String[] params, CommandSender sender) {
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append("<").append(param).append(">").append(" ");
        }
        ArrayList<String> parentNames = new ArrayList<>();
        parentNames.add(getName());
        CommandBase search = this;
        while (search.getParent() != null) {
            parentNames.add(search.getParent().getName());
            search = search.getParent();
        }

        Collections.reverse(parentNames);
        StringBuilder parentBuilder = new StringBuilder();
        int iteration = 0;
        for (String parentName : parentNames) {
            if (iteration != 0)
                parentBuilder.append(" ");
            parentBuilder.append(parentName);
            iteration++;
        }
        FrameworkMessage.COMMAND_USAGE.replace("%command%", parentBuilder.toString()).replace("%usage%", builder.toString()).send(sender);

    }

    /**
     * Run log event
     *
     * @param base   The base command
     * @param sender The sender of the command
     * @param args   The arguments of the command
     * @return If the event was cancelled
     */
    boolean runLogEvent(CommandBase base, CommandSender sender, String[] args);


}
