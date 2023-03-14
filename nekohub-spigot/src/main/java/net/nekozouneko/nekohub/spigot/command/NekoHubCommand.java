package net.nekozouneko.nekohub.spigot.command;

import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NekoHubCommand implements CommandExecutor, TabCompleter {

    private final SpigotNekoHubPlugin plugin = SpigotNekoHubPlugin.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {

        }
        else {
            switch (args[0]) {
                case "rl":
                case "reload":
                    plugin.reload();
                    break;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission(command.getPermission())) {
            List<String> tab = new ArrayList<>();

            if (args.length == 1) {
                String[] args1 = new String[] {"reload"};

                for (String arg : args1) {
                    if (arg.startsWith(args[0].toLowerCase())) tab.add(arg);
                }
            }

            return tab;
        }
        return new ArrayList<>();
    }
}
