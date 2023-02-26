package com.nekozouneko.nekohub.inventory.item;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.print.Book;
import java.util.List;

public class BookBuilder extends AbstractItemStackBuilder<BookBuilder, BookMeta> {

    protected BookBuilder(ItemStack is, BookMeta bm) {
        super(is, bm);
    }

    public static BookBuilder of(Material material) {
        ItemStack is = new ItemStack(material);
        BookMeta bm = ((BookMeta) is.getItemMeta());

        return new BookBuilder(is, bm);
    }

    public static BookBuilder of(ItemStack item) {
        BookMeta bm = ((BookMeta) item.getItemMeta());

        return new BookBuilder(item, bm);
    }

    public static BookBuilder of(ItemStack item, BookMeta meta) {
        item.setItemMeta(meta);

        return new BookBuilder(item, meta);
    }

    public BookBuilder author(String author) {
        meta.setAuthor(author);
        return this;
    }

    public String author() {
        return meta.getAuthor();
    }

    public BookBuilder page(String... contents) {
        meta.addPage(contents);
        return this;
    }

    public BookBuilder page(List<String> contents) {
        meta.addPage(contents.toArray(new String[0]));
        return this;
    }

    public BookBuilder page(int page, String content) {
        meta.setPage(page, content);
        return this;
    }

    public String page(int page) {
        return meta.getPage(page);
    }

    public BookBuilder page(BaseComponent[]... contents) {
        meta.spigot().addPage(contents);
        return this;
    }

    public BookBuilder componentPage(List<BaseComponent[]> contents) {
        meta.spigot().addPage(contents.toArray(new BaseComponent[0][]));
        return this;
    }

    public BookBuilder page(int page, BaseComponent... content) {
        meta.spigot().setPage(page, content);
        return this;
    }

    public BaseComponent[] componentPage(int page) {
        return meta.spigot().getPage(page);
    }

    public List<String> pages() {
        return meta.getPages();
    }

    public List<BaseComponent[]> componentPages() {
        return meta.spigot().getPages();
    }

    public BookBuilder generation(BookMeta.Generation generation) {
        meta.setGeneration(generation);
        return this;
    }

    public BookMeta.Generation generation() {
        return meta.getGeneration();
    }

    public BookBuilder title(String title) {
        meta.setTitle(title);
        return this;
    }

    public String title() {
        return meta.getTitle();
    }
}
