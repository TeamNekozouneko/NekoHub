package com.nekozouneko.nekohub.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;

public final class VoteCommand extends Command implements TabExecutor {

    public VoteCommand() {
        super("vote", "", "touhyou");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new TextComponent("§e+§a§m----------------------------------------§e+"));
        sender.sendMessage(new TextComponent("このサーバーに投票すると特定のサーバーで特典をゲットできます。毎日投票して特典をゲットしまくろう！"));

        BaseComponent mjp = new TextComponent("minecraft.jp: §9§nhttps://minecraft.jp/servers/nekozouneko.com");
        mjp.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecraft.jp/servers/nekozouneko.com"));
        mjp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("クリックして開きます。(minecraft.jp)")));
        sender.sendMessage(mjp);

        BaseComponent mono = new TextComponent("ものくらふと！: §9§nhttps://monocraft.net/servers/80mCREsW84eDdHQZb11u");
        mono.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://monocraft.net/servers/80mCREsW84eDdHQZb11u/vote"));
        mono.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("クリックして開きます。(monocraft.net)")));
        sender.sendMessage(mono);

        sender.sendMessage(new TextComponent("§e+§a§m----------------------------------------§e+"));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
