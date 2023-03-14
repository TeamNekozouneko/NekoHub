package net.nekozouneko.nekohub.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;

public final class DonateCommand extends Command implements TabExecutor {

    public DonateCommand() {
        super("donate", "", "patreon", "amazon-gift", "kifu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new TextComponent("§e+§c§m----------------------------------------§e+"));

        sender.sendMessage(new TextComponent("Nekozouneko Serverの維持にはどうしても、お金が必要となります。もしお金に余裕がありましたら以下のリンクから寄付していただけると幸いです。"));

        BaseComponent patreon = new TextComponent("Patreon: §9§nhttps://patreon.com/teamnekozouneko");
        patreon.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/teamnekozouneko"));
        patreon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("クリックして開きます。(Patreon)")));
        sender.sendMessage(patreon);

        sender.sendMessage(new TextComponent("§e+§c§m----------------------------------------§e+"));

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
