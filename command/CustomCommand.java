package ua.imfoxter.mc.foxanarchy.command;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ua.imfoxter.mc.foxanarchy.language.Messages;
import ua.imfoxter.mc.foxanarchy.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomCommand<T> extends Command {
    /**
     * Конструктор команди з параметрами.
     *
     * @param command     Команда
     * @param aliases     Псевдоніми команди
     * @param permission  Дозвіл на виконання команди
     * @param description Опис команди
     */
    public CustomCommand(
            @NotNull String command,
            @NotNull String aliases,
            @NotNull String permission,
            @NotNull String description
    ) {
        super(command);
        super.setAliases(ParseUtils.parseStringToList(aliases));
        super.setPermission(permission);
        super.setDescription(description);
    }

    /**
     * Конструктор команди з параметрами без псевдонімів.
     *
     * @param command     Команда
     * @param permission  Дозвіл на виконання команди
     * @param description Опис команди
     */
    public CustomCommand(
            @NotNull String command,
            @NotNull String permission,
            @NotNull String description
    ) {
        super(command);
        super.setPermission(permission);
        super.setDescription(description);
    }

    /**
     * Метод для виконання команди.
     *
     * @param sender Відправник команди
     * @param args   Аргументи команди
     */
    protected abstract void execute(
            @NotNull T sender,
            String @NotNull [] args
    );

    /**
     * Метод для автодоповнення команди.
     *
     * @param sender Відправник команди
     * @param args   Аргументи команди
     * @return Список можливих автодоповнень
     */
    protected abstract @NotNull List<String> complete(
            @NotNull CommandSender sender,
            String @NotNull [] args
    );

    @Override
    public boolean execute(
            @NotNull CommandSender sender,
            @NotNull String commandLabel,
            @NotNull String[] args
    ) {
        try {this.execute((T) sender, args);}
        catch (ClassCastException e) {Messages.DISABLED_IN_CONSOLE.sendMessage(sender);}
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(
            @NotNull CommandSender sender,
            @NotNull String alias,
            @NotNull String[] args
    ) throws IllegalArgumentException {
        return this.filter(this.complete(sender, args), args);
    }

    /**
     * Фільтрує список автодоповнень на основі введених аргументів.
     *
     * @param list Список автодоповнень
     * @param args Аргументи команди
     * @return Відфільтрований список автодоповнень
     */
    private @NotNull List<String> filter(
            @Nullable List<String> list,
            String @NotNull [] args
    ) {
        if (list == null) return Lists.newArrayList();
        final String last = args[args.length - 1];
        final List<String> result = new ArrayList<>();

        for (String arg : list) {
            if (arg.toLowerCase().startsWith(
                    last.toLowerCase())
            ) {
                result.add(arg);
            }
        }
        return result;
    }
}