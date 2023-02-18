package com.nekozouneko.nekohub.bungee;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.nekozouneko.nekohub.NekoHubPlugin;
import com.nekozouneko.nekohub.Util;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.List;

/**
 * BungeeCordのユーティリティ
 * @author Taitaitatata
 * @since 3.0
 * @see com.nekozouneko.nekohub.Util
 */
public final class BungeeUtil {

    /**
     * サーバー上のプレイヤーの名前リストを作成する
     * @param p BungeeCorのプラグイン
     * @return プレイヤー名のリスト
     */
    public static List<String> toPlayerNames(Plugin p) {
        return toPlayerNames(p.getProxy().getPlayers());
    }

    /**
     * プレイヤーの名前リストを作成する
     * @param pps プレイヤーのリスト
     * @return プレイヤー名のリスト
     */
    public static List<String> toPlayerNames(Collection<ProxiedPlayer> pps) {
        List<String> r = Lists.newArrayList();

        pps.forEach((pp) -> r.add(pp.getName()));

        return r;
    }

    public static String getFullUserName(BungeeNekoHubPlugin bnp, ProxiedPlayer pp) {
        Preconditions.checkArgument(bnp != null, "Plugin is null.");
        StringBuilder sb = new StringBuilder();

        String prefix = "";
        String suffix = "";

        if (bnp.isDependEnabled(NekoHubPlugin.Depends.LUCKPERMS)) {
            final LuckPerms lp = LuckPermsProvider.get();
            final User u = lp.getUserManager().getUser(pp.getUniqueId());

            if (u != null) {
                CachedMetaData md = u.getCachedData().getMetaData();

                if (md.getPrefix() != null) prefix = md.getPrefix();
                if (md.getSuffix() != null) suffix = md.getSuffix();
            }
        }

        sb.append(prefix);
        sb.append(pp.getDisplayName() != null ? pp.getDisplayName() : pp.getName());
        sb.append(suffix);

        return sb.toString();
    }

}
