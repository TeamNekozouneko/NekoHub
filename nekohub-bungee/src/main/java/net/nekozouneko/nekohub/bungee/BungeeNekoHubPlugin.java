package net.nekozouneko.nekohub.bungee;

import net.nekozouneko.nekohub.NekoHubPlugin;
import net.nekozouneko.nekohub.ProxyData;
import net.nekozouneko.nekohub.bungee.command.DonateCommand;
import net.nekozouneko.nekohub.bungee.command.VoteCommand;
import net.nekozouneko.nekohub.bungee.listener.ServerKickListener;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public final class BungeeNekoHubPlugin extends Plugin implements NekoHubPlugin {

    private static BungeeNekoHubPlugin plugin;
    private static Map<String, ProxyData.Server> sd;

    private BungeeNekoHubConfig config;
    private final Map<Depends, Boolean> depends = new TreeMap<>();

    public static NekoHubPlugin getInstance() {
        return plugin;
    }

    public BungeeNekoHubConfig getConfiguration() {
        return config;
    }

    @Override
    public void onEnable() {
        plugin = this;

        setUpConfig();
        setUpDepends();

        getProxy().registerChannel("nekohub:proxy");
        getProxy().getPluginManager().registerListener(this, new ServerKickListener());

        getProxy().getPluginManager().registerCommand(this, new DonateCommand());
        getProxy().getPluginManager().registerCommand(this, new VoteCommand());
    }

    @Override
    public File getPluginDataFolder() {
        return super.getDataFolder();
    }

    @Override
    public File getLanguageFolder() {
        return null;
    }

    @Override
    public boolean isDependEnabled(Depends depend) {
        return Optional.ofNullable(depends.get(depend)).orElse(false);
    }

    private void setUpConfig() {
        final File d = new File(getPluginDataFolder(), "bungeeconfig.yml");
        Configuration yc = null;

        if (!d.exists() || !d.isFile()) {
            try (
                InputStream is = getResourceAsStream("bungeeconfig.yml");
            ) {
                Files.copy(is, d.toPath(), StandardCopyOption.REPLACE_EXISTING);
                yc = YamlConfiguration.getProvider(YamlConfiguration.class).load(is);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (yc != null) {
            try {
                yc = YamlConfiguration.getProvider(YamlConfiguration.class).load(d);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpDepends() {
        final Plugin LuckPerm = getProxy().getPluginManager().getPlugin("LuckPerms");

        /* LuckPerms */
        if (LuckPerm != null) {
            LuckPerms lp = LuckPermsProvider.get();
            if (lp != null) depends.put(Depends.LUCKPERMS, true);
        }
    }
}
