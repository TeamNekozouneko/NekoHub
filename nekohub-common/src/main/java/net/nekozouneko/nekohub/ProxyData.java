package net.nekozouneko.nekohub;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class ProxyData {

    public static class Server {

        private final String name;

        private int players = 0;
        private boolean online = false;
        private String motd = "";

        public Server(String name) {
            this.name = name;
        }

        public Server(String name, String motd) {
            this.name = name;
            this.motd = motd;
        }

        public Server(String name, String motd, boolean online) {
            this.name = name;
            this.motd = motd;
            this.online = online;
        }

        public Server(String name, String motd, boolean online, int players) {
            this.name = name;
            this.motd = motd;
            this.online = online;
            this.players = players;
        }

        public void setPlayers(int i) {
            this.players = i;
        }

        public void setOnline(boolean b) {
            this.online = b;
        }

        public void setMotd(String s) {
            Preconditions.checkArgument(s != null, "Motd cannot be null.");
            this.motd = s;
        }

        public int getPlayers() {
            return players;
        }

        public boolean isOnline() {
            return online;
        }

        private String getMotd() {
            return motd;
        }
    }

    private final String lobby;
    private final Set<Server> servers;

    public ProxyData(String lobby, Set<Server> servers) {
        this.lobby = lobby;

        this.servers = servers;
    }

    public String getLobby() {
        return lobby;
    }

    public Set<Server> getServers() {
        return Collections.unmodifiableSet(servers);
    }

}
