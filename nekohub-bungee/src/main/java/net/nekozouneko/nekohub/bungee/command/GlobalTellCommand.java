package net.nekozouneko.nekohub.bungee.command;

import com.google.common.collect.Lists;
import net.nekozouneko.nekohub.Util;
import net.nekozouneko.nekohub.bungee.BungeeNekoHubPlugin;
import net.nekozouneko.nekohub.bungee.BungeeUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.UUID;

public class GlobalTellCommand extends Command implements TabExecutor {

    private final BungeeNekoHubPlugin plugin;

    public GlobalTellCommand(BungeeNekoHubPlugin plugin) {
        super("global-tell", "", "proxy-tell", "gtell");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(args.length > 1)) {
            TextComponent tc = new TextComponent("§c使用方法: /gtell <target> <text>");
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gtell <target> <text>"));
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("クリックしてチャットに入力")));
            sender.sendMessage(tc);
            return;
        }
        else if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("§cプレイヤーとして実行してください。"));
            return;
        }
        else if (sender.getName().equals(args[0])) {
            sender.sendMessage(new TextComponent("§c自分にプライベートメッセージは送信できません。"));
            return;
        }

        ProxiedPlayer to;

        if (Util.isUUID(args[0])) {
            to = plugin.getProxy().getPlayer(UUID.fromString(args[0]));
        }
        else to = plugin.getProxy().getPlayer(args[0]);

        if (to == null) {
            sender.sendMessage(new TextComponent("§c\"" + args[0] + "\" というプレイヤーが見つからないかオフラインです。"));
            return;
        }

        ProxiedPlayer pp = (ProxiedPlayer) sender;
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < args.length; i++) sb.append(args[i]); // メッセージのみにする

        sendTellFromMessage(pp, to, sb.toString());
        sendTellToMessage(pp, to, sb.toString());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Util.ignoreCaseTabComp(BungeeUtil.toPlayerNames(plugin), args[0]);
        }
        return Lists.newArrayList();
    }

    private void sendTellToMessage(ProxiedPlayer fr, ProxiedPlayer to, String mes) {
        BaseComponent c = new TextComponent();

        BaseComponent p = new TextComponent(ChatColor.GRAY+"["+ChatColor.WHITE+BungeeUtil.getFullUserName(plugin, fr)+ChatColor.GRAY+" ->]");
        p.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gtell " + fr.getName() + " "));
        p.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity("minecraft:player", fr.getUniqueId().toString(), new TextComponent(fr.getName()))));

        c.addExtra(p);
        c.addExtra(ChatColor.RESET + " ");
        c.addExtra(mes);

        to.sendMessage(c);
    }

    private void sendTellFromMessage(ProxiedPlayer fr, ProxiedPlayer to, String mes) {
        BaseComponent c = new TextComponent();

        BaseComponent p = new TextComponent(ChatColor.GRAY+"["+ChatColor.WHITE+BungeeUtil.getFullUserName(plugin, to)+ChatColor.GRAY+" <-]");
        p.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gtell " + to.getName() + " "));
        p.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity("minecraft:player", to.getUniqueId().toString(), new TextComponent(to.getName()))));

        c.addExtra(p);
        c.addExtra(ChatColor.RESET + " ");
        c.addExtra(mes);

        fr.sendMessage(c);
    }
}
