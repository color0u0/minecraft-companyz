package com.github.donghune.companyz.util.extension

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.isContentFull(): Boolean {
    return contents.count { itemStack -> itemStack != null && itemStack.type == Material.AIR } == size
}

fun Inventory.isContentEmpty(): Boolean {
    return !isContentFull()
}

fun Inventory.hasItems2(requireItems: Array<ItemStack>): Boolean {
    val requireMap = requireItems
        .filter { itemStack: ItemStack -> itemStack != null && itemStack.type != Material.AIR }
        .groupBy { itemStack: ItemStack -> itemStack.i18NDisplayName ?: itemStack.type }
        .mapValues { data -> data.value.sumBy { itemStack: ItemStack -> itemStack.amount } }
        .toMap()

    val playerMap = storageContents
        .filter { itemStack: ItemStack -> itemStack != null && itemStack.type != Material.AIR }
        .groupBy { itemStack: ItemStack -> itemStack.i18NDisplayName ?: itemStack.type }
        .mapValues { data -> data.value.sumBy { itemStack: ItemStack -> itemStack.amount } }
        .toMap()

    return requireMap
        .map { entry: Map.Entry<Any, Int> -> entry.value <= (playerMap[entry.key] ?: 0) }
        .find { isHas: Boolean -> !isHas }
        .let { isFind: Boolean? -> isFind == null }
}