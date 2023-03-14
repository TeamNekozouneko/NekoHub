package net.nekozouneko.nekohub.spigot.command;

import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import net.nekozouneko.nekohub.spigot.task.SuicideTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class SuicideCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーとして実行してください。");
            return true;
        }

        new SuicideTask((Player) sender, 5).runTaskTimer(SpigotNekoHubPlugin.getInstance(), 0, 20);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
