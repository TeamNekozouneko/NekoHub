package net.nekozouneko.nekohub.spigot.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class BedSpawnCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーとして実行してください。");
            return true;
        }

        Player p = (Player) sender;

        if (p.getBedSpawnLocation() != null) {
            p.teleport(p.getBedSpawnLocation());
            p.playSound(p.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 2);
        }
        else {
            p.sendMessage("§cベッドまたはリスポーンアンカーが破壊, 埋もれているか、スポーン地点が設定されていません。");
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
