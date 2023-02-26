package com.nekozouneko.nekohub.spigot.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class SuicideTask extends BukkitRunnable {

    private final Player p;
    private int second;
    private boolean first;

    public SuicideTask(Player player, int second) {
        this.p = player;
        this.second = second;
    }

    @Override
    public void run() {
        if (first) {
            first = false;
        }
        else second--;

        if (second < 0) {
            p.setHealth(0);

        }
    }
}
