package com.nekozouneko.nekohub.spigot.listener;

import com.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import com.nekozouneko.nekohub.spigot.gui.NHSpigotGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredListener;

public final class PlayerQuitListener implements Listener  {

    private final SpigotNekoHubPlugin plugin = ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance());

    @EventHandler
    public void onEvent(PlayerQuitEvent e) {
        HandlerList.getHandlerLists().forEach((hl) -> {
            for (RegisteredListener rl : hl.getRegisteredListeners()) {
                if (rl.getListener() instanceof NHSpigotGUI) {
                    if (((NHSpigotGUI) rl.getListener()).getPlayer().equals(e.getPlayer())) {
                        Bukkit.getScheduler().runTaskLater(
                                plugin, () -> HandlerList.unregisterAll(rl.getListener()), 1
                        );
                    }
                }
            }
        });
    }

}
