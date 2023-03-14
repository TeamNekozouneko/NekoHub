package net.nekozouneko.nekohub.spigot.gui;

import com.google.common.base.Preconditions;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.nekozouneko.nekohub.Util;
import net.nekozouneko.nekohub.common.spigot.inventory.item.AbstractItemStackBuilder;
import net.nekozouneko.nekohub.common.spigot.inventory.item.ItemStackBuilder;
import net.nekozouneko.nekohub.common.spigot.inventory.item.SkullBuilder;
import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import net.nekozouneko.nekohub.spigot.SpigotUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class ServerList extends NHSpigotGUI implements Listener {

    private final Plugin plugin;
    private final Inventory inv;
    private final Consumer<Player> onClose;

    public ServerList(Plugin plugin, Player p, Consumer<Player> onClose) {
        super(p);
        this.plugin = plugin;
        this.inv = Bukkit.createInventory(this, plugin.getConfig().getInt("server-list.size", 54), "サーバーを選択");
        this.onClose = onClose;

        if (!plugin.getConfig().getBoolean("server-list.enabled")) {
            throw new RuntimeException("Server list is disabled.");
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update() {
        inv.clear();
        List<Map<String, Object>> sections = ((List) plugin.getConfig().getList("server-list.servers"));

        Preconditions.checkNotNull(sections);
        sections.forEach(this::buildItem);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;

        Player p = ((Player) e.getWhoClicked());
        ItemStack item = e.getCurrentItem();

        e.setCancelled(true);

        if (item == null || item.getType().isAir()) return;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "server");

        if (pdc.has(key, PersistentDataType.STRING)) {
            String val = pdc.get(key, PersistentDataType.STRING);
            if (val == null || val.isEmpty()) return;

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(val);

            p.sendPluginMessage(
                    ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance()),
                    "BungeeCord", out.toByteArray()
            );
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;

        HandlerList.unregisterAll(this);
        if (onClose != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                onClose.accept(player);
            }, 1);
        }
    }

    @SuppressWarnings("unchecked")
    private void buildItem(Map<String, Object> map) {
         if (!Util.containsKeys(map, "position", "material")) return;

        Material m = Material.valueOf(String.valueOf(map.get("material")));
        int pos = Integer.parseInt(String.valueOf(map.get("position")));

        String name = String.valueOf(map.get("name"));
        List<String> lore = Util.replaceAltCodes((List<String>) map.getOrDefault("lore", new ArrayList<>()));
        boolean enchant = Boolean.parseBoolean(String.valueOf(map.get("enchant")));

        AbstractItemStackBuilder builder;
        if (m == Material.PLAYER_HEAD && map.containsKey("owner")) {
            builder = SkullBuilder.of(m);
            String ow = String.valueOf(map.get("owner"));

            ((SkullBuilder) builder).owner(SpigotUtil.getOfflinePlayer(ow));
        }
        else builder = ItemStackBuilder.of(m);

        builder.itemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE
        );

        builder.name(name != null ? Util.replaceAltCodes(name) : null);
        builder.lore(lore);
        if (enchant) {
            builder.enchant(Enchantment.DURABILITY, 1, false);
        }
        if (map.containsKey("custom-model-data") && map.get("custom-model-data") instanceof Integer) {
            builder.customModelData(Integer.parseInt(String.valueOf(map.get("custom-model-data"))));
        }
        if (map.containsKey("goto") && map.get("goto") instanceof String) {
            builder.persistentData(
                    new NamespacedKey(plugin, "server"),
                    PersistentDataType.STRING,
                    map.get("goto")
            );
        }

        inv.setItem(pos, builder.build());
    }

}
