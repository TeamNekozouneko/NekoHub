package net.nekozouneko.nekohub.spigot.gui;

import net.nekozouneko.nekohub.NekoHubPlugin;
import net.nekozouneko.nekohub.Util;
import net.nekozouneko.nekohub.common.spigot.inventory.item.BookBuilder;
import net.nekozouneko.nekohub.common.spigot.inventory.item.ItemStackBuilder;
import net.nekozouneko.nekohub.common.spigot.inventory.item.SkullBuilder;
import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import net.nekozouneko.nekohub.spigot.SpigotUtil;
import net.nekozouneko.nekohub.spigot.VaultUtil;
import net.nekozouneko.nekohub.spigot.task.LoopTask;
import net.nekozouneko.nekohub.spigot.task.SuicideTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.MemorySection;
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

import java.util.ArrayList;
import java.util.List;

public final class StickMenu extends NHSpigotGUI implements Listener {

    private final Inventory inv = Bukkit.createInventory(this, 27, "棒メニュー");
    private final SpigotNekoHubPlugin plugin;

    public StickMenu(SpigotNekoHubPlugin plugin, Player p) {
        super(p);
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void update() {
        inv.clear();
        MemorySection c = plugin.getConfig();

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

        ItemStack suicide = ItemStackBuilder.of(Material.REDSTONE)
                .name("§c自殺を行う")
                .lore(
                        "§7§nバグや詰んだ場合実行することを推奨します。", "",
                        "§7アイテムの保持はゲームルールから継承されます。"
                )
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "suicide")
                .build();

        ItemStack ender = ItemStackBuilder.of(Material.ENDER_CHEST)
                .name("§5エンダーチェストを開く")
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "enderchest")
                .build();

        ItemStack trash = ItemStackBuilder.of(Material.LAVA_BUCKET)
                .name("§3ゴミ箱")
                .lore("§7※ゴミ箱に入れ捨てたアイテムは一切復元いたしません。")
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "trash")
                .build();

        ItemStack bed = ItemStackBuilder.of(Material.RED_BED)
                .name("§9設定したスポーン地点に戻る")
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "bed-spawn")
                .build();

        ItemStack spawn = ItemStackBuilder.of(Material.ENDER_PEARL)
                .name("§9サーバーのスポーン地点に戻る")
                .persistentData(new NamespacedKey(plugin, "action"), PersistentDataType.STRING, "server-spawn")
                .build();

        inv.setItem(c.getInt("stickmenu.buttons.profile.position"), info);
        if (c.getBoolean("stickmenu.buttons.rulebook.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.rulebook.position"), rb);
        }
        if (c.getBoolean("stickmenu.buttons.server-list.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.server-list.position"), sl);
        }

        if (c.getBoolean("stickmenu.buttons.trash.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.trash.position"), trash);
        }
        if (c.getBoolean("stickmenu.buttons.suicide.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.suicide.position"), suicide);
        }
        if (c.getBoolean("stickmenu.buttons.enderchest.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.enderchest.position"), ender);
        }
        if (c.getBoolean("stickmenu.buttons.bed-spawn.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.bed-spawn.position"), bed);
        }
        if (c.getBoolean("stickmenu.buttons.server-spawn.enabled")) {
            inv.setItem(c.getInt("stickmenu.buttons.server-spawn.position"), spawn);
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;

        e.setCancelled(true);

        Player p = ((Player) e.getWhoClicked());
        ItemStack item = e.getCurrentItem();

        if (item == null || item.getType().isAir()) return;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        MemorySection c = plugin.getConfig();

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
                p.playSound(p.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 1, 2);
                break;
            }
            case "suicide": {
                p.closeInventory();
                new SuicideTask(p, 5).runTaskTimer(plugin, 0, 20);
                new LoopTask(20, (v) -> p.playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 1, 1)).runTaskTimer(plugin, 0, 1);
                break;
            }
            case "enderchest": {
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                p.openInventory(p.getEnderChest());
                break;
            }
            case "trash": {
                p.closeInventory();
                new TrashChest(plugin, p, new ItemStack[0]).open();
                break;
            }
            case "server-spawn": {
                if (plugin.getSpawn() != null) {
                    p.closeInventory();
                    p.teleport(plugin.getSpawn());
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
                }
                else {
                    p.sendMessage("§c設定に不具合があるためテレポートできませんでした。");
                }
                break;
            }
            case "bed-spawn": {
                if (p.getBedSpawnLocation() != null) {
                    p.closeInventory();
                    p.teleport(p.getBedSpawnLocation());
                    p.playSound(p.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 2);
                }
                else {
                    p.sendMessage("§cベッドまたはリスポーンアンカーが破壊, 埋もれているか、スポーン地点が設定されていません。");
                }
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
                && !plugin.getConfig().getBoolean("stickmenu.profile.disable-vault", true)
        ) {
            Economy eco = VaultUtil.getEconomy();
            prof.add("§7所持" + eco.currencyNameSingular() + ": §f" + String.format("%,.1f", eco.getBalance(p)) + (eco.getBalance(p) <= 1 ? eco.currencyNameSingular() : eco.currencyNamePlural()));
        }

        return prof;
    }
}
