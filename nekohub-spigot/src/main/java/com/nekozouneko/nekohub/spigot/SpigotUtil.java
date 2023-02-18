package com.nekozouneko.nekohub.spigot;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nekozouneko.nekohub.NekoHubPlugin;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;

public final class SpigotUtil {

    private SpigotUtil() {}

    public static List<String> toPlayerNames() {
        List<String> res = Lists.newArrayList();

        Bukkit.getOnlinePlayers().forEach((p) -> res.add(p.getName()));
        Collections.sort(res);

        return res;
    }

    public static String toNameAndPrefixSuffix(Player p, boolean enableVanilla, boolean appendVanilla) {
        final SpigotNekoHubPlugin plugin = ((SpigotNekoHubPlugin) SpigotNekoHubPlugin.getInstance());
        StringBuilder sb = new StringBuilder();

        if (plugin.isDependEnabled(NekoHubPlugin.Depends.VAULT_CHAT)) {
            sb.append(VaultUtil.getChat().getPlayerPrefix(p));
            sb.append(p.getName());
            sb.append(VaultUtil.getChat().getPlayerSuffix(p));

            Team t = p.getScoreboard().getEntryTeam(p.getName());
            if (appendVanilla && t != null) {
                sb.insert(0, Strings.nullToEmpty(t.getPrefix()));
                sb.append(Strings.nullToEmpty(t.getSuffix()));
            }
        }
        else if (plugin.isDependEnabled(NekoHubPlugin.Depends.LUCKPERMS)) {
            final User u = LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId());
            Preconditions.checkState(u != null);

            sb.append(u.getCachedData().getMetaData().getPrefix());
            sb.append(p.getName());
            sb.append(u.getCachedData().getMetaData().getSuffix());

            Team t = p.getScoreboard().getEntryTeam(p.getName());
            if (appendVanilla && t != null) {
                sb.insert(0, Strings.nullToEmpty(t.getPrefix()));
                sb.append(Strings.nullToEmpty(t.getSuffix()));
            }
        }
        else if (enableVanilla) {
            Team t = p.getScoreboard().getEntryTeam(p.getName());
            if (t != null) {
                sb.insert(0, Strings.nullToEmpty(t.getPrefix()));
                sb.append(Strings.nullToEmpty(t.getSuffix()));
            }
        }
        else sb.append(p.getName());

        return sb.toString();
    }

}
