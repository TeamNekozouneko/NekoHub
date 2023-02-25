package com.nekozouneko.nekohub.spigot.gui;

import com.nekozouneko.nekohub.inventory.item.ItemStackBuilder;
import com.nekozouneko.nekohub.inventory.item.SkullBuilder;
import com.nekozouneko.nekohub.spigot.SpigotUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StickMenu extends NHSpigotGUI implements Listener {

    private final Inventory inv = Bukkit.createInventory(this, 27, "棒メニュー");
    private final Plugin plugin;

    public StickMenu(Plugin plugin, Player p) {
        super(p);
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void update() {
        inv.clear();

        ItemStack info = SkullBuilder.of(Material.PLAYER_HEAD)
                .owner(player)
                .name(SpigotUtil.toNameAndPrefixSuffix(player, true))
                .lore(buildProfile(player))
                .build();

        inv.setItem(0, info);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Bukkit.broadcastMessage("Event called");
        if (e.getInventory().getHolder() != this) return;



        e.setCancelled(true);

        update();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;

        HandlerList.unregisterAll(this);
    }

    private List<String> buildProfile(Player p) {
        return new ArrayList<>();
    }
}
