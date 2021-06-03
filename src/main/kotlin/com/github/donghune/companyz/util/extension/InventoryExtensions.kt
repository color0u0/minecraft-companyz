package com.github.donghune.companyz.util.extension

import org.bukkit.Material
import org.bukkit.inventory.Inventory

fun Inventory.isContentFull(): Boolean {
    return contents.count { itemStack -> itemStack != null && itemStack.type == Material.AIR } == 0
}

fun Inventory.isContentEmpty(): Boolean {
    return contents.count { itemStack -> itemStack == null } == size
}