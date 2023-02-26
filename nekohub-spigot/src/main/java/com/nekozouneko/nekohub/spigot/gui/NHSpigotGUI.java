package com.nekozouneko.nekohub.spigot.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public abstract class NHSpigotGUI implements InventoryHolder {

    protected final Player player;

    public NHSpigotGUI(Player p) {
        this.player = p;
    }

    public void open() {
        update();
        player.openInventory(getInventory());
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void update();

}
