package com.github.donghune.companyz.util.minecraft

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*


class ItemStackFactory(
    itemStack: ItemStack = ItemStack(Material.AIR)
) {

    private val itemStack = itemStack.clone()
    private val itemMeta = itemStack.itemMeta

    fun setDisplayName(displayName: String): ItemStackFactory {
        itemMeta.displayName(Component.text(displayName))
        return this
    }

    fun setType(material: Material): ItemStackFactory {
        itemStack.type = material
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
        itemStack.itemMeta.lore()
            .apply { (itemMeta.lore() ?: mutableListOf()).add(Component.text(value)) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun removeLore(index: Int): ItemStackFactory {
        itemStack.itemMeta.lore()
            .apply { (itemMeta.lore() ?: mutableListOf()).removeAt(index) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun editLore(index: Int, value: String): ItemStackFactory {
        itemStack.itemMeta.lore()
            .apply { (itemMeta.lore() ?: mutableListOf())[index] = Component.text(value) }
            .also { itemMeta.lore(it) }
        return this
    }

    fun setLore(lore: List<String>): ItemStackFactory {
        itemStack.itemMeta.lore(lore.map { Component.text(it) })
        return this
    }

    fun addEnchantments(enchantments: Map<Enchantment?, Int?>): ItemStackFactory {
        itemStack.addEnchantments(enchantments)
        return this
    }

    fun addEnchantment(enchantment: Enchantment, level: Int): ItemStackFactory {
        itemStack.addEnchantment(enchantment, level)
        return this
    }

    fun addUnsafeEnchantments(enchantments: Map<Enchantment?, Int?>): ItemStackFactory {
        itemStack.addUnsafeEnchantments(enchantments)
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

    fun toBannerMeta(block : (BannerMeta) -> Unit) : ItemStackFactory {
        (itemMeta as BannerMeta).apply(block)
        return this
    }

    fun toBlockDataMeta(block : (BlockDataMeta) -> Unit) : ItemStackFactory {
        (itemMeta as BlockDataMeta).apply(block)
        return this
    }

    fun toBlockStateMeta(block : (BlockStateMeta) -> Unit) : ItemStackFactory {
        (itemMeta as BlockStateMeta).apply(block)
        return this
    }

    fun toBookMeta(block : (BookMeta) -> Unit) : ItemStackFactory {
        (itemMeta as BookMeta).apply(block)
        return this
    }

    fun toCompassMeta(block : (CompassMeta) -> Unit) : ItemStackFactory {
        (itemMeta as CompassMeta).apply(block)
        return this
    }

    fun toCrossbowMeta(block : (CrossbowMeta) -> Unit) : ItemStackFactory {
        (itemMeta as CrossbowMeta).apply(block)
        return this
    }

    fun toEnchantmentStorageMeta(block : (EnchantmentStorageMeta) -> Unit) : ItemStackFactory {
        (itemMeta as EnchantmentStorageMeta).apply(block)
        return this
    }

    fun toFireworkEffectMeta(block : (FireworkEffectMeta) -> Unit) : ItemStackFactory {
        (itemMeta as FireworkEffectMeta).apply(block)
        return this
    }

    fun toFireworkMeta(block : (FireworkMeta) -> Unit) : ItemStackFactory {
        (itemMeta as FireworkMeta).apply(block)
        return this
    }

    fun toKnowledgeBookMeta(block : (KnowledgeBookMeta) -> Unit) : ItemStackFactory {
        (itemMeta as KnowledgeBookMeta).apply(block)
        return this
    }

    fun toLeatherArmorMeta(block : (LeatherArmorMeta) -> Unit) : ItemStackFactory {
        (itemMeta as LeatherArmorMeta).apply(block)
        return this
    }

    fun toMapMeta(block : (MapMeta) -> Unit) : ItemStackFactory {
        (itemMeta as MapMeta).apply(block)
        return this
    }

    fun toPotionMeta(block : (PotionMeta) -> Unit) : ItemStackFactory {
        (itemMeta as PotionMeta).apply(block)
        return this
    }

    fun toSkullMeta(block : (SkullMeta) -> Unit) : ItemStackFactory {
        (itemMeta as SkullMeta).apply(block)
        return this
    }

    fun toSpawnEggMeta(block : (SpawnEggMeta) -> Unit) : ItemStackFactory {
        (itemMeta as SpawnEggMeta).apply(block)
        return this
    }

    fun toSuspiciousStewMeta(block : (SuspiciousStewMeta) -> Unit) : ItemStackFactory {
        (itemMeta as SuspiciousStewMeta).apply(block)
        return this
    }

    fun toTropicalFishBucketMeta(block : (TropicalFishBucketMeta) -> Unit) : ItemStackFactory {
        (itemMeta as TropicalFishBucketMeta).apply(block)
        return this
    }


    fun build(): ItemStack {
        return itemStack.apply { itemMeta = this.itemMeta }
    }

}