package com.nekozouneko.nekohub.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public final class Inventories {

    private Inventories() { throw new ExceptionInInitializerError(); }

    public static List<ItemStack> addItems(Player p, ItemStack... items) {
        return addItems(p.getInventory(), items);
    }

    public static List<ItemStack> addItems(Inventory inv, ItemStack... items) {
        List<ItemStack> over = new ArrayList<>();
        for (ItemStack item : items) {
            if (item != null && !item.getType().isAir()) {
                over.addAll(inv.addItem(item).values());
            }
        }

        return over;
    }

    public static void addItemsOrDrop(Player p, ItemStack... items) {
        addItemsOrDrop(p.getInventory(), p.getLocation(), items);
    }

    public static void addItemsOrDrop(Inventory inv, Location loc, ItemStack... items) {
        for (ItemStack is : addItems(inv, items)) {
            loc.getWorld().dropItemNaturally(loc, is);
        }
    }

    public static void setItems(Inventory inv, int r1, int r2, ItemStack item) {
        IntStream.range(r1, r2+1).forEach((a) -> inv.setItem(a, item));
    }
}
