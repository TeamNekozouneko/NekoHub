package com.nekozouneko.nekohub.spigot;

import com.nekozouneko.nekohub.NekoHubPlugin;
import com.nekozouneko.nekohub.spigot.command.RulebookCommand;
import com.nekozouneko.nekohub.spigot.command.ServerListCommand;
import com.nekozouneko.nekohub.spigot.command.StickMenuCommand;
import com.nekozouneko.nekohub.spigot.listener.BungeeMessageListener;
import com.nekozouneko.nekohub.spigot.listener.InteractListener;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SpigotNekoHubPlugin extends JavaPlugin implements NekoHubPlugin {

    private static SpigotNekoHubPlugin plugin;
    private static File langFolder;

    private Map<Depends, Boolean> depends = new TreeMap<>();

    public static NekoHubPlugin getInstance() {
        return plugin;
    }

    @Override
    public void onLoad() {
        langFolder = new File(getDataFolder(), "lang");
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        getCommand("stickmenu").setExecutor(new StickMenuCommand());
        getCommand("serverlist").setExecutor(new ServerListCommand());
        getCommand("rulebook").setExecutor(new RulebookCommand());

        getServer().getPluginManager().registerEvents(new InteractListener(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());

        setUpLuckPerms();
        setUpVault();
    }

    @Override
    public void onDisable() {
        depends.clear();
    }

    @Override
    public File getPluginDataFolder() {
        return super.getDataFolder();
    }

    @Override
    public File getLanguageFolder() {
        return langFolder;
    }

    @Override
    public boolean isDependEnabled(Depends depend) {
        return Optional.ofNullable(depends.get(depend)).orElse(false);
    }

    public void reload() {
        reloadConfig();
    }

    private void setUpLuckPerms() {
        final Plugin LP_PLUGIN = getServer().getPluginManager().getPlugin("LuckPerms");

        try {
            /* LuckPerms */
            if (LP_PLUGIN != null && LP_PLUGIN.isEnabled()) {
                depends.put(Depends.LUCKPERMS, true);
                LuckPermsProvider.get();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpVault() {
        final Plugin VAULT_PLUGIN = getServer().getPluginManager().getPlugin("Vault");

        try {
            if (VAULT_PLUGIN != null) {
                depends.put(Depends.VAULT_CHAT, VaultUtil.isChatAvailable());
                depends.put(Depends.VAULT_ECONOMY, VaultUtil.isPermissionsAvailable());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
