package net.nekozouneko.nekohub.bungee.listener;

import net.nekozouneko.nekohub.ProxyData;
import net.nekozouneko.nekohub.Util;
import net.nekozouneko.nekohub.bungee.BungeeNekoHubPlugin;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void onEvent(PluginMessageEvent e) {
        if (e.isCancelled() || !e.getTag().startsWith("nekohub:")) return;

        BungeeNekoHubPlugin plugin = ((BungeeNekoHubPlugin) BungeeNekoHubPlugin.getInstance());

        if (e.getTag().equals("nekohub:proxy")) {
            try (
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()))
            ) {
                String sub = in.readUTF();
                switch (sub) {
                    case "request": {
                        try (
                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                DataOutputStream out = new DataOutputStream(bytes)
                        ) {
                            ProxyData pd = new ProxyData("", null);
                            out.writeUTF(Base64Coder.encodeLines(Util.toByteArr(pd)));
                        }
                        catch (IOException e2) {
                            throw new IOException(e2);
                        }

                        break;
                    }
                    case "connect-lobby":
                        if (e.getSender() instanceof ProxiedPlayer) {
                            ProxiedPlayer pp = ((ProxiedPlayer) e.getSender());
                            ServerInfo si = pp.getServer().getInfo();
                            List<String> pri = pp.getPendingConnection().getListener().getServerPriority();

                            if (pri.size() == 0 || si.getName().equals(pri.get(0))) return;

                            ServerInfo hub = plugin.getProxy().getServerInfo(pri.get(0));
                            if (hub == null) return;

                            pp.connect(hub);
                        }
                        break;
                }
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public ProxyData buildData(BungeeNekoHubPlugin plugin) {
        Map<String, ProxyData.Server> map = new HashMap<>();

        plugin.getProxy().getServers().forEach((n, i) -> {

        });
        return null;
    }

}
