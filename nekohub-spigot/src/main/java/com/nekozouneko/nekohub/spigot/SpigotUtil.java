package com.nekozouneko.nekohub.spigot;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nekozouneko.nekohub.NekoHubPlugin;
import com.nekozouneko.nekohub.Util;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class SpigotUtil {

    private SpigotUtil() {}

    public static List<String> toPlayerNames() {
        List<String> res = Lists.newArrayList();

        Bukkit.getOnlinePlayers().forEach((p) -> res.add(p.getName()));
        Collections.sort(res);

        return res;
    }

    public static String toNameAndPrefixSuffix(Player p, boolean enableVanilla) {
        final SpigotNekoHubPlugin plugin = ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance());
        StringBuilder sb = new StringBuilder();

        if (plugin.isDependEnabled(NekoHubPlugin.Depends.VAULT_CHAT)) {
            sb.append(Strings.nullToEmpty(VaultUtil.getChat().getPlayerPrefix(p)));
            sb.append(p.getName());
            sb.append(Strings.nullToEmpty(VaultUtil.getChat().getPlayerSuffix(p)));
        }
        else if (plugin.isDependEnabled(NekoHubPlugin.Depends.LUCKPERMS)) {
            final User u = LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId());
            Preconditions.checkState(u != null);

            sb.append(Strings.nullToEmpty(u.getCachedData().getMetaData().getPrefix()));
            sb.append(p.getName());
            sb.append(Strings.nullToEmpty(u.getCachedData().getMetaData().getSuffix()));
        }
        else sb.append(p.getName());

        if (enableVanilla) {
            Team t = p.getScoreboard().getEntryTeam(p.getName());
            if (t != null) {
                sb.insert(0, Strings.nullToEmpty(t.getPrefix()));
                sb.append(Strings.nullToEmpty(t.getSuffix()));
            }
        }

        return sb.toString();
    }

    @SuppressWarnings("deprecation")
    public static OfflinePlayer getOfflinePlayer(String s) {
        if (Util.isUUID(s)) {
            return Bukkit.getOfflinePlayer(UUID.fromString(s));
        }
        else return Bukkit.getOfflinePlayer(s);
    }

}
