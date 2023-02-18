package com.nekozouneko.nekohub.spigot.gui;

import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public final class ServerList extends NHSpigotGUI implements Listener {

    private final Inventory inv = Bukkit.createInventory(this, 54, "サーバーを選択");

    public ServerList(SpigotNekoHubPlugin plugin, Player p) {
        super(p);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

    }
}
