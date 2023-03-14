package net.nekozouneko.nekohub.spigot.gui;

import net.nekozouneko.nekohub.common.spigot.inventory.Inventories;
import net.nekozouneko.nekohub.common.spigot.inventory.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public final class ConfirmScreen extends NHSpigotGUI implements Listener {

    private final Plugin plugin;
    private final Consumer<Player> onYes;
    private final Consumer<Player> onNo;
    private final String yes;
    private final String no;
    private final String comment;
    private final Inventory inv;

    private boolean confirmed = false;

    public ConfirmScreen(Plugin plugin, Player player, String title, String yes, String no, String comment, Consumer<Player> onYes, Consumer<Player> onNo) {
        super(player);

        this.plugin = plugin;
        this.onYes = onYes;
        this.onNo = onNo;
        this.yes = yes;
        this.no = no;
        this.comment = comment;

        this.inv = Bukkit.createInventory(this, 9, title == null || title.isEmpty() ? "確認" : title);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void update() {
        inv.clear();

        ItemStack y = ItemStackBuilder.of(Material.LIME_STAINED_GLASS_PANE)
                .name(yes == null || yes.isEmpty() ? "§rはい" : yes)
                .persistentData(new NamespacedKey(plugin, "value"), PersistentDataType.INTEGER, 1)
                .build();

        ItemStack n = ItemStackBuilder.of(Material.RED_STAINED_GLASS_PANE)
                .name(no == null || no.isEmpty() ? "§rいいえ" : no)
                .persistentData(new NamespacedKey(plugin, "value"), PersistentDataType.INTEGER, 0)
                .build();

        ItemStack c = ItemStackBuilder.of(Material.GRAY_STAINED_GLASS_PANE)
                .name(comment == null || comment.isEmpty() ? " " : "§f" + comment)
                .build();

        inv.setItem(4, c);
        Inventories.setItems(inv, 0, 3, y);
        Inventories.setItems(inv, 5, 8, n);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        PersistentDataContainer pdc = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
        if (pdc.has(new NamespacedKey(plugin, "value"), PersistentDataType.INTEGER)) {
            if (pdc.get(new NamespacedKey(plugin, "value"), PersistentDataType.INTEGER) == 1) {
                onYes.accept(((Player) e.getWhoClicked()));
            }
            else onNo.accept(((Player) e.getWhoClicked()));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            confirmed = true;

            Bukkit.getScheduler().runTaskLater(plugin, e.getWhoClicked()::closeInventory, 1);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;

        if (confirmed) {
            HandlerList.unregisterAll(this);
        }
        else Bukkit.getScheduler().runTaskLater(plugin, this::open, 1);
    }
}
