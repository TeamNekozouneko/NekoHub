package com.nekozouneko.nekohub.bungee.listener;

import com.nekozouneko.nekohub.bungee.BungeeNekoHubPlugin;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ServerKickListener implements Listener {

    @EventHandler
    public void onEvent(ServerKickEvent e) {
        BungeeNekoHubPlugin plugin = ((BungeeNekoHubPlugin) BungeeNekoHubPlugin.getInstance());

        ListenerInfo li = e.getPlayer().getPendingConnection().getListener();
        List<String> ls = li.getServerPriority();

        plugin.getLogger().info("Lobbies: " + ls.toString());
        plugin.getLogger().info("Current: " + (e.getPlayer().getServer() != null ? e.getPlayer().getServer().getInfo().getName() : ""));
        plugin.getLogger().info("Kicked: " + e.getKickedFrom().getName());
        plugin.getLogger().info("Connection state: " + e.getState().name());
        plugin.getLogger().info("Player: " + e.getPlayer().getName());

        if (ls.size() == 0) {
            plugin.getLogger().info("1");
            BaseComponent[] bc = new BaseComponent[3];
            bc[0] = new TextComponent("\n再接続ができませんでした。\n§c(再接続先のサーバーが設定されていません)\n");
            bc[1] = new TextComponent("§7§m----------------------------------------§r\n");
            bc[2] = new TextComponent(e.getKickReasonComponent());
            e.setKickReasonComponent(bc);
            e.setCancelled(false);
        }
        else if (e.getState() == ServerKickEvent.State.CONNECTED) {
            plugin.getLogger().info("2");
            e.setCancelServer(plugin.getProxy().getServerInfo(ls.get(0)));
            e.setCancelled(true);
            e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
            e.getPlayer().sendMessage(new TextComponent("§c"+e.getKickedFrom().getName()+"から切断されました。"));
            e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
            e.getPlayer().sendMessage(new TextComponent(e.getKickReasonComponent()));
            e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
        }
        else {
            if (e.getKickedFrom().getName().equalsIgnoreCase(ls.get(0)) || e.getPlayer().getServer() == null) {
                plugin.getLogger().info("3");

                BaseComponent[] bc = new BaseComponent[3];
                bc[0] = new TextComponent("\n"+e.getKickedFrom().getName()+"から切断/追放されました。\n");
                bc[1] = new TextComponent("§7§m----------------------------------------\n");
                bc[2] = new TextComponent(e.getKickReasonComponent());
                e.setKickReasonComponent(bc);
                e.setCancelled(false);
            }
            else {
                plugin.getLogger().info("4");

                e.setCancelServer(plugin.getProxy().getServerInfo(ls.get(0)));
                e.setCancelled(true);

                e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
                e.getPlayer().sendMessage(new TextComponent("§c"+e.getKickedFrom().getName()+"から切断されました。"));
                e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
                e.getPlayer().sendMessage(new TextComponent(e.getKickReasonComponent()));
                e.getPlayer().sendMessage(new TextComponent("§7§m----------------------------------------"));
            }
        }
    }

}
