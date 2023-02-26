package com.nekozouneko.nekohub.spigot.gui;

import com.nekozouneko.nekohub.NekoHubPlugin;
import com.nekozouneko.nekohub.Util;
import com.nekozouneko.nekohub.inventory.item.BookBuilder;
import com.nekozouneko.nekohub.inventory.item.ItemStackBuilder;
import com.nekozouneko.nekohub.inventory.item.SkullBuilder;
import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import com.nekozouneko.nekohub.spigot.SpigotUtil;
import com.nekozouneko.nekohub.spigot.VaultUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
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
                .name("§f" + SpigotUtil.toNameAndPrefixSuffix(player, true) + "§7 | " + player.getPing()+ "ms")
                .lore(buildProfile(player))
                .build();

        ItemStack rb = ItemStackBuilder.of(Material.KNOWLEDGE_BOOK)
                .name("§bルール")
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "rule")
                .build();

        ItemStack sl = ItemStackBuilder.of(Material.ENDER_EYE)
                .name("§6サーバー間移動")
                .enchant(Enchantment.DURABILITY, 1, false)
                .itemFlags(ItemFlag.HIDE_ENCHANTS)
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "server")
                .build();

        inv.setItem(0, info);
        inv.setItem(7, rb);
        inv.setItem(8, sl);
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

        Player p = ((Player) e.getWhoClicked());
        ItemStack item = e.getCurrentItem();

        if (item == null || item.getType().isAir()) return;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        Bukkit.broadcastMessage(pdc.getOrDefault(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, ""));

        switch (pdc.getOrDefault(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "")) {
            case "rule": {
                ItemStack is = BookBuilder.of(Material.WRITTEN_BOOK)
                        .author(plugin.getName())
                        .title("Rule")
                        .generation(BookMeta.Generation.ORIGINAL)
                        .page(Util.replaceAltCodes(plugin.getConfig().getStringList("rulebook.content")))
                        .build();

                p.closeInventory();
                p.openBook(is);
                break;
            }
            case "server": {
                p.closeInventory();
                new ServerList(plugin, p, (p1) -> new StickMenu(plugin, p1).open()).open();
                break;
            }
        }

        update();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;

        HandlerList.unregisterAll(this);
    }

    private List<String> buildProfile(Player p) {
        List<String> prof = new ArrayList<>();

        prof.add("§7プレイ時間: §f" + Util.toHrsMinSecFormat("%02d:%02d:%02d", p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20));

        if (
                SpigotNekoHubPlugin.getInstance().isDependEnabled(NekoHubPlugin.Depends.VAULT_ECONOMY)
                && plugin.getConfig().getBoolean("stickmenu.profile.disable-vault", false)
        ) {
            Economy eco = VaultUtil.getEconomy();
            prof.add("§7所持" + eco.currencyNameSingular() + ": §f" + eco.getBalance(p) + (eco.getBalance(p) == 1 ? eco.currencyNameSingular() : eco.currencyNamePlural()));
        }

        return prof;
    }
}
