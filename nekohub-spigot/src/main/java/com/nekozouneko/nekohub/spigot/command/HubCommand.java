package com.nekozouneko.nekohub.spigot.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class HubCommand implements CommandExecutor, TabCompleter {

    private final SpigotNekoHubPlugin plugin = SpigotNekoHubPlugin.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーとして実行してください。");
            return true;
        }

        if (plugin.getConfig().getBoolean("hub-enabled")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(plugin.getConfig().getString("hub-server"));

            ((Player) sender).sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        }
        else {
            sender.sendMessage("§cこのコマンドは管理者によって実行が禁止されています。");
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
