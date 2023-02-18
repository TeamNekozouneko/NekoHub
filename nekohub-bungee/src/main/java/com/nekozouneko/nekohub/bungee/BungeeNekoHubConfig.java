package com.nekozouneko.nekohub.bungee;

import java.util.Set;

public final class BungeeNekoHubConfig {

    private final Set<String> lobbies;

    public BungeeNekoHubConfig(Set<String> lobbies) {
        this.lobbies = lobbies;
    }

    public Set<String> getLobbyServers() {
        return lobbies;
    }

}
