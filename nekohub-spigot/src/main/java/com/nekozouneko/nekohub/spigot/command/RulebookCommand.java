package com.nekozouneko.nekohub.spigot.command;

import com.nekozouneko.nekohub.Util;
import com.nekozouneko.nekohub.inventory.item.BookBuilder;
import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class RulebookCommand implements CommandExecutor, TabCompleter {

    private final SpigotNekoHubPlugin plugin = ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーとして実行してください。");
            return true;
        }

        Player p = (Player) sender;

        ItemStack is = BookBuilder.of(Material.WRITTEN_BOOK)
                .author("Plugin")
                .title("Rule")
                .generation(BookMeta.Generation.ORIGINAL)
                .page(Util.replaceAltCodes(plugin.getConfig().getStringList("rulebook.content")))
                .build();

        p.openBook(is);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
