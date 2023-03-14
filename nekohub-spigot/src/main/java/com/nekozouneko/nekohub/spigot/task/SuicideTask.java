package com.nekozouneko.nekohub.spigot.task;

import com.nekozouneko.nekohub.spigot.SpigotUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class SuicideTask extends BukkitRunnable {

    private final Player p;
    private final Location loc;
    private int second;
    private boolean first = true;

    public SuicideTask(Player player, int second) {
        this.p = player;
        this.second = second;
        this.loc = p.getLocation().clone();
    }

    @Override
    public void run() {
        if (first) {
            first = false;
            p.sendMessage("§c§n自殺を" + second + "秒後実行します。キャンセルするには動いてください。");
        }
        else second--;

        if (!loc.equals(p.getLocation())) {
            p.sendMessage("自殺をキャンセルしました。");
            cancel();
            return;
        }

        if (second < 1) {
            p.sendMessage("自殺を実行しました: " + SpigotUtil.locationFormat("%.03f / %.03f / %.03f (%s)", p.getLocation()));
            p.setHealth(0);
            cancel();
        }
        else {
            p.sendMessage(second + "秒後自殺を実行します。");
        }
    }
}
