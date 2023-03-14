package net.nekozouneko.nekohub.common.spigot.inventory.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder extends AbstractItemStackBuilder<ItemStackBuilder, ItemMeta> {

    protected ItemStackBuilder(ItemStack is, ItemMeta im) {
        super(is, im);
    }

    public static ItemStackBuilder of(Material material) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();

        return new ItemStackBuilder(is, im);
    }

    public static ItemStackBuilder of(ItemStack item) {
        ItemMeta im = item.getItemMeta();

        return new ItemStackBuilder(item, im);
    }

    public static ItemStackBuilder of(ItemStack item, ItemMeta meta) {
        item.setItemMeta(meta);

        return new ItemStackBuilder(item, meta);
    }
}
