package com.nekozouneko.nekohub.spigot.command;

import com.nekozouneko.nekohub.inventory.Inventories;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class StickCommand implements CommandExecutor, TabCompleter {

    private final Map<UUID, Long> limit = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        checkLimits();

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cE: プレイヤーとして実行してください。");
            return true;
        }

        Player p = (Player) sender;

        if (limit.containsKey(p.getUniqueId()) && (limit.get(p.getUniqueId()) + 10000) > System.currentTimeMillis()) {
            double cool = limit.get(p.getUniqueId()) + 10000;
            p.sendMessage("§cクールダウン中です。あと" + String.format("%.01f", (cool - System.currentTimeMillis()) / 1000) + "秒待ってください。");
            return true;
        }

        if (!p.getInventory().contains(Material.STICK)) {
            limit.put(p.getUniqueId(), System.currentTimeMillis());

            Inventories.addItemsOrDrop(p, new ItemStack(Material.STICK));
        }
        else p.sendMessage("§cあなたのインベントリにすでに棒があるため与えられませんでした。");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }

    public void checkLimits() {
        for (UUID uid : limit.keySet()) {
            if ((limit.get(uid) + 10000) <= System.currentTimeMillis()) {
                limit.remove(uid);
            }
        }
    }
}
