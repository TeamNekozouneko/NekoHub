package com.nekozouneko.nekohub.spigot;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultUtil {

    private VaultUtil() { throw new ExceptionInInitializerError(); }

    /**
     * @return Vault chat api
     * @throws IllegalStateException Service is now not available.
     */
    public static Chat getChat() throws IllegalStateException {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) throw new IllegalStateException("Chat service is now not available");

        return rsp.getProvider();
    }

    /**
     * @return Vault economy api
     * @throws IllegalStateException Service is now not available.
     */
    public static Economy getEconomy() throws IllegalStateException {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) throw new IllegalStateException("Economy service is now not available");

        return rsp.getProvider();
    }

    /**
     * @return Vault chat api
     * @throws IllegalStateException Service is now not available.
     */
    public static Permission getPermissions() throws IllegalStateException {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) throw new IllegalStateException("Permission service is now not available");

        return rsp.getProvider();
    }

    public static boolean isChatAvailable() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;

        try {
            return getChat() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEconomyAvailable() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;

        try {
            return getEconomy() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPermissionsAvailable() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;

        try {
            return getPermissions() != null;
        } catch (Exception e) {
            return false;
        }
    }

}
