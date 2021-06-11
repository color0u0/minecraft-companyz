package com.github.donghune.companyz.util.minecraft

import com.destroystokyo.paper.Namespaced
import com.google.common.collect.Multimap
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack


class ItemStackFactory(
    itemStack: ItemStack = ItemStack(Material.AIR)
) {

    private val itemStack = itemStack.clone()
    private val itemMeta = itemStack.itemMeta

    fun setNameDisplayName(displayName: String): ItemStackFactory {
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

    fun editLore(value: String, index: Int): ItemStackFactory {
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

    fun addAttributeModifier(attribute: Attribute, modifier: AttributeModifier): ItemStackFactory {
        itemMeta.addAttributeModifier(attribute, modifier)
        return this
    }

    fun setAttributeModifiers(attributeModifiers: Multimap<Attribute, AttributeModifier>): ItemStackFactory {
        itemMeta.attributeModifiers = attributeModifiers
        return this
    }

    fun removeAttributeModifier(attribute: Attribute): ItemStackFactory {
        itemMeta.removeAttributeModifier(attribute)
        return this
    }

    fun removeAttributeModifier(slot: EquipmentSlot): ItemStackFactory {
        itemMeta.removeAttributeModifier(slot)
        return this
    }

    fun removeAttributeModifier(attribute: Attribute, modifier: AttributeModifier): ItemStackFactory {
        itemMeta.removeAttributeModifier(attribute, modifier)
        return this
    }

    fun setDestroyableKeys(canDestroy: Collection<Namespaced>): ItemStackFactory {
        itemMeta.setDestroyableKeys(canDestroy)
        return this
    }

    fun setPlaceableKeys(canPlaceOn: Collection<Namespaced>): ItemStackFactory {
        itemMeta.setPlaceableKeys(canPlaceOn)
        return this
    }

    fun build(): ItemStack {
        return itemStack.apply { itemMeta = this.itemMeta }
    }

}