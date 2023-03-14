package net.nekozouneko.nekohub.spigot.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import net.nekozouneko.nekohub.spigot.SpigotUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ServerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("使用方法: " + command.getUsage().replace("<command>", label));
            return true;
        }

        Set<Player> players;
        if (args.length > 1) {
            List<String> prePlayers = new ArrayList<>(Arrays.asList(args));
            prePlayers.remove(0);
            players = new HashSet<>();
            prePlayers.forEach((pp) -> {
                Player p = Bukkit.getPlayer(pp);
                if (p != null) players.add(p);
                else {
                    sender.sendMessage("§cE: " + pp + " というプレイヤーは見つかりませんでした。");
                }
            });
        }
        else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("プレイヤーとして実行するか転送するプレイヤーを指定してください。");
                return true;
            }
            players = Collections.singleton((Player) sender);
        }

        int c = 0;
        for (Player p : players) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(args[0]);

            p.sendPluginMessage(
                    SpigotNekoHubPlugin.getInstance(),
                    "BungeeCord",
                    out.toByteArray()
            );
            c++;
        }

        sender.sendMessage(c + "人を"+args[0]+"に転送に成功しました。"
                + ((players.size() - c) == 0 ? "" :
                "ただし、" + (players.size() - c) + "人の転送に失敗しました"));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return new ArrayList<>();
        List<String> tab = new ArrayList<>();

        for (String pn : SpigotUtil.toPlayerNames()) {
            if (pn.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                tab.add(pn);
            }
        }

        return tab;
    }
}
