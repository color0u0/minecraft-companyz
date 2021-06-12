package com.github.donghune.companyz.util.minecraft

import com.github.donghune.namulibrary.extension.replaceChatColorCode
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

fun ItemStack.edit(block: ItemStackFactory.() -> ItemStackFactory): ItemStack {
    return block.invoke(ItemStackFactory(this)).build()
}

class ItemStackFactory(
    itemStack: ItemStack = ItemStack(Material.AIR),
    isWithClone: Boolean = false,
) {

    private val itemStack = if (isWithClone) itemStack.clone() else itemStack
    private var itemMeta = itemStack.itemMeta

    fun setDisplayName(displayName: String): ItemStackFactory {
        itemMeta.displayName(Component.text(displayName))
        return this
    }

    fun setType(material: Material): ItemStackFactory {
        itemStack.type = material
        itemMeta = Bukkit.getItemFactory().getItemMeta(material)
        return this
    }

    fun setAmount(value: Int): ItemStackFactory {
        itemStack.amount = value
        return this
    }

    fun addAmount(value: Int): ItemStackFactory {
        itemStack.amount += value
        return this
    }

    fun addLore(value: String): ItemStackFactory {
        (itemMeta.lore() ?: mutableListOf())
            .apply { add(Component.text("&f$value".replaceChatColorCode())) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun addLore(vararg value: String): ItemStackFactory {
        (itemMeta.lore() ?: mutableListOf())
            .apply { value.forEach { add(Component.text("&f$it".replaceChatColorCode())) } }
            .also { itemMeta.lore(it) }
        return this
    }

    fun removeLore(index: Int): ItemStackFactory {
        (itemMeta.lore() ?: mutableListOf())
            .apply { removeAt(index) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun editLore(index: Int, value: String): ItemStackFactory {
        (itemMeta.lore() ?: mutableListOf())
            .apply { set(index, Component.text(value.replaceChatColorCode())) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun setLore(lore: List<String>): ItemStackFactory {
        itemStack.itemMeta.lore(lore.map { Component.text(it) })
        return this
    }

    fun addUnsafeEnchantment(enchantment: Enchantment, level: Int): ItemStackFactory {
        itemStack.addUnsafeEnchantment(enchantment, level)
        return this
    }

    fun removeEnchantment(enchantment: Enchantment): ItemStackFactory {
        itemStack.removeEnchantment(enchantment)
        return this
    }

    fun addItemFlags(vararg itemFlags: ItemFlag): ItemStackFactory {
        itemMeta.addItemFlags(*itemFlags)
        return this
    }

    fun removeItemFlags(vararg itemFlags: ItemFlag): ItemStackFactory {
        itemMeta.removeItemFlags(*itemFlags)
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): ItemStackFactory {
        itemMeta.isUnbreakable = unbreakable
        return this
    }

    fun BannerMeta(block: BannerMeta.() -> Unit): ItemStackFactory {
        (itemMeta as BannerMeta).apply(block)
        return this
    }

    fun EnchantmentStorageMeta(block: EnchantmentStorageMeta.() -> Unit): ItemStackFactory {
        (itemMeta as EnchantmentStorageMeta).apply(block)
        return this
    }

    fun LeatherArmorMeta(block: LeatherArmorMeta.() -> Unit): ItemStackFactory {
        (itemMeta as LeatherArmorMeta).apply(block)
        return this
    }

    fun PotionMeta(block: PotionMeta.() -> Unit): ItemStackFactory {
        (itemMeta as PotionMeta).apply(block)
        return this
    }

    fun SkullMeta(block: SkullMeta.() -> Unit): ItemStackFactory {
        (itemMeta as SkullMeta).apply(block)
        return this
    }

    fun SuspiciousStewMeta(block: SuspiciousStewMeta.() -> Unit): ItemStackFactory {
        (itemMeta as SuspiciousStewMeta).apply(block)
        return this
    }

    fun TropicalFishBucketMeta(block: TropicalFishBucketMeta.() -> Unit): ItemStackFactory {
        (itemMeta as TropicalFishBucketMeta).apply(block)
        return this
    }

    fun build(): ItemStack {
        return itemStack.apply { itemMeta = this@ItemStackFactory.itemMeta }
    }

}