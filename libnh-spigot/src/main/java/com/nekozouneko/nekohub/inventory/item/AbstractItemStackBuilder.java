package com.nekozouneko.nekohub.inventory.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "unused"})
public class AbstractItemStackBuilder<B extends AbstractItemStackBuilder<B, M>, M extends ItemMeta> {

    protected final ItemStack item;

    protected final M meta;

    protected AbstractItemStackBuilder(ItemStack item, M meta) {
        this.item = item.clone();
        this.meta = meta;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public B name(String name) {
        meta.setDisplayName(name);
        return (B) this;
    }

    public String name() {
        return meta.getDisplayName();
    }

    public B localizedName(String localized) {
        meta.setLocalizedName(localized);
        return (B) this;
    }

    public String localizedName() {
        return meta.getLocalizedName();
    }

    public B lore(List<String> lore) {
        meta.setLore(lore);
        return (B) this;
    }

    public B lore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return (B) this;
    }

    public List<String> lore() {
        return meta.hasLore() ? meta.getLore() : Lists.newArrayList();
    }

    public B amount(int amount) {
        item.setAmount(amount);
        return (B) this;
    }

    public int amount() {
        return item.getAmount();
    }

    public B customModelData(Integer customModelData) {
        meta.setCustomModelData(customModelData);
        return (B) this;
    }

    public Integer customModelData() {
        return meta.getCustomModelData();
    }

    public B unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return (B) this;
    }

    public boolean unbreakable() {
        return meta.isUnbreakable();
    }

    public B itemFlags(ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return (B) this;
    }

    public Set<ItemFlag> itemFlags() {
        return meta.getItemFlags();
    }

    public B attribute(Attribute attribute, AttributeModifier modifier) {
        meta.addAttributeModifier(attribute, modifier);
        return (B) this;
    }

    public Multimap<Attribute, AttributeModifier> attributes() {
        return meta.getAttributeModifiers();
    }

    public Multimap<Attribute, AttributeModifier> attributes(EquipmentSlot slot) {
        return meta.getAttributeModifiers(slot);
    }

    /**
     * Enchant item
     * @param enchant Enchantment
     * @param level Enchant level
     * @param bypass Bypass enchant level restriction
     * @return ItemStack Builder
     */
    public B enchant(Enchantment enchant, int level, boolean bypass) {
        meta.addEnchant(enchant, level, bypass);
        return (B) this;
    }

    public Map<Enchantment, Integer> enchants() {
        return meta.getEnchants();
    }

    public <T, Z> B persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        meta.getPersistentDataContainer().set(key, type, value);
        return (B) this;
    }

    public <T, Z> Z persistentData(NamespacedKey key, PersistentDataType<T, Z> type) {
        return meta.getPersistentDataContainer().get(key, type);
    }

}
