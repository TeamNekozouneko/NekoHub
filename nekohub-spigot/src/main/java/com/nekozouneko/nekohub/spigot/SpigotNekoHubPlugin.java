package com.nekozouneko.nekohub.spigot;

import com.nekozouneko.nekohub.NekoHubPlugin;
import com.nekozouneko.nekohub.spigot.command.*;
import com.nekozouneko.nekohub.spigot.listener.BungeeMessageListener;
import com.nekozouneko.nekohub.spigot.listener.InteractListener;
import com.nekozouneko.nekohub.spigot.listener.PlayerQuitListener;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeMap;

public class SpigotNekoHubPlugin extends JavaPlugin implements NekoHubPlugin {

    private static SpigotNekoHubPlugin plugin;
    private static File langFolder;

    private Map<Depends, Boolean> depends = new TreeMap<>();
    private Location spawn;

    public static SpigotNekoHubPlugin getInstance() {
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
        getCommand("stick").setExecutor(new StickCommand());
        getCommand("nekohub").setExecutor(new NekoHubCommand());
        getCommand("hub").setExecutor(new HubCommand());
        getCommand("server").setExecutor(new ServerCommand());

        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        /*getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);*/
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());

        reload();
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

    public Location getSpawn() {
        if (getConfig().getBoolean("world-spawn.custom-enabled")) {
            return new Location(
                    Bukkit.getWorld(getConfig().getString("world-spawn.world")),
                    getConfig().getDouble("world-spawn.x"),
                    getConfig().getDouble("world-spawn.y"),
                    getConfig().getDouble("world-spawn.z"),
                    getConfig().getObject("world-spawn.yaw", Float.class),
                    getConfig().getObject("world-spawn.pitch", Float.class)
            );
        }
        else {
            Properties prop = new Properties();
            try (InputStream in = Files.newInputStream(Paths.get("server.properties"))) {
                prop.load(in);

                return Bukkit.getWorld((String) prop.get("level-name")).getSpawnLocation();
            }
            catch (IOException e) {
                e.printStackTrace();
                return Bukkit.getWorlds().get(0).getSpawnLocation();
            }
        }
    }

    public void updateSpawn() {
        try (InputStreamReader r = new InputStreamReader(
                new FileInputStream("server.properties"),
                StandardCharsets.ISO_8859_1
        )) {
            Properties p = new Properties();
            p.load(r);
            spawn = SpigotUtil.getSpawn(this, p);
        }
        catch (IOException e) {
            spawn = SpigotUtil.getSpawn(this, new Properties());
        }
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();
        updateSpawn();

        setUpVault();
        setUpLuckPerms();
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
