package com.nekozouneko.nekohub.spigot.command;

import com.google.common.collect.Lists;
import com.nekozouneko.nekohub.Util;
import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import com.nekozouneko.nekohub.spigot.SpigotUtil;
import com.nekozouneko.nekohub.spigot.gui.StickMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class StickMenuCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cプレイヤーとして実行するか、プレイヤーを引数に含めてください。");
                return true;
            }

            Player p = (Player) sender;
            new StickMenu((Plugin) SpigotNekoHubPlugin.getInstance(), p).open();
        }
        else {
            if (!sender.hasPermission("nekohub.command.stickmenu.other")) {
                sender.sendMessage("§c権限が不足しています。");
                return true;
            }

            List<Player> ps = new ArrayList<>();

            for (String arg : args) {
                Player p = Bukkit.getPlayer(arg);

                if (p == null) {
                    sender.sendMessage("§c" + arg + " というプレイヤーは見つかりませんでした。");
                    continue;
                }

                ps.add(p);
            }


            ps.forEach((player -> new StickMenu((Plugin) SpigotNekoHubPlugin.getInstance(), player).open()));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return Lists.newArrayList();
        else return Util.ignoreCaseTabComp(SpigotUtil.toPlayerNames(), args[args.length-1]);
    }
}
