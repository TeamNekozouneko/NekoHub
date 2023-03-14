package com.nekozouneko.nekohub.spigot.gui;

import com.nekozouneko.nekohub.inventory.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class TrashChest extends NHSpigotGUI implements Listener {

    private final Plugin plugin;
    private final Inventory inv = Bukkit.createInventory(this, 54, "ゴミ箱");

    public TrashChest(Plugin plugin, Player player, ItemStack[] items) {
        super(player);

        this.plugin = plugin;
        inv.setContents(items);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void update() {}

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;

        HandlerList.unregisterAll(this);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!inv.isEmpty()) {
                new ConfirmScreen(plugin, (Player) e.getPlayer(), null, null, null, null,
                        Player::closeInventory,
                        (p) -> {
                            for (ItemStack is : Inventories.addItems(p.getInventory(), e.getInventory().getContents())) {
                                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), is);
                            }
                        }
                ).open();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
        }, 1);
    }
}
