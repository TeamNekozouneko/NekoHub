package net.nekozouneko.nekohub.common.spigot.inventory.item;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

public final class SkullBuilder extends AbstractItemStackBuilder<SkullBuilder, SkullMeta> {

    protected SkullBuilder(ItemStack item, SkullMeta meta) {
        super(item, meta);
    }

    public static SkullBuilder of(Material material) {
        ItemStack item = new ItemStack(material);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        return new SkullBuilder(item, meta);
    }

    public static SkullBuilder of(ItemStack item) {
        Preconditions.checkArgument(
                item.getType() == Material.PLAYER_HEAD || item.getType() == Material.PLAYER_WALL_HEAD
        );

        return new SkullBuilder(item, (SkullMeta) item.getItemMeta());
    }

    public static SkullBuilder of(ItemStack item, SkullMeta meta) {
        Preconditions.checkArgument(
                item.getType() == Material.PLAYER_HEAD || item.getType() == Material.PLAYER_WALL_HEAD
        );

        item.setItemMeta(meta);

        return new SkullBuilder(item, meta);
    }

    public SkullBuilder owner(OfflinePlayer player) {
        meta.setOwningPlayer(player);
        return this;
    }

    public OfflinePlayer owner() {
        return meta.getOwningPlayer();
    }

    public SkullBuilder profile(PlayerProfile profile) {
        meta.setOwnerProfile(profile);
        return this;
    }

    public PlayerProfile profile() {
        return meta.getOwnerProfile();
    }

    public SkullBuilder noteBlockSound(NamespacedKey sound) {
        meta.setNoteBlockSound(sound);
        return this;
    }

    public NamespacedKey noteBlockSound() {
        return meta.getNoteBlockSound();
    }

}
