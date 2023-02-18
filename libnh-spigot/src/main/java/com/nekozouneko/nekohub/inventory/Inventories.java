package com.nekozouneko.nekohub.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class Inventories {

    private Inventories() { throw new ExceptionInInitializerError(); }

    public static void insert(Inventory inv, int i, ItemStack... items) {
        Preconditions.checkArgument(items.length != 0);

        for (int j = 0; j < items.length; j++) {
            inv.setItem((i-1) + j, items[i]);
        }


    }
}
