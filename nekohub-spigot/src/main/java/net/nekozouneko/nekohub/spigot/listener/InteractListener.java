package net.nekozouneko.nekohub.spigot.listener;

import net.nekozouneko.nekohub.spigot.SpigotNekoHubPlugin;
import net.nekozouneko.nekohub.spigot.gui.ServerList;
import net.nekozouneko.nekohub.spigot.gui.StickMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final SpigotNekoHubPlugin plugin = ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance());

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.STICK) {
            switch (e.getAction()) {
                case LEFT_CLICK_AIR: {
                    if (
                            plugin.getConfig().getBoolean("server-list.stick-interact", true)
                            && e.getPlayer().hasPermission("nekohub.interact.stickmenu")
                    ) {
                        new ServerList(plugin, e.getPlayer(), null).open();
                        e.getPlayer().playSound(
                                e.getPlayer().getLocation(),
                                Sound.BLOCK_PORTAL_AMBIENT, 1, 2
                        );
                    }
                    break;
                }
                case RIGHT_CLICK_AIR: {
                    if (
                            plugin.getConfig().getBoolean("stickmenu.stick-interact", true)
                            && e.getPlayer().hasPermission("nekohub.interact.serverlist")
                    ) {
                        new StickMenu(plugin, e.getPlayer()).open();
                        e.getPlayer().playSound(
                                e.getPlayer().getLocation(),
                                Sound.BLOCK_CHEST_OPEN, 1, .5f
                        );
                    }
                    break;
                }
            }
        }
    }

}
