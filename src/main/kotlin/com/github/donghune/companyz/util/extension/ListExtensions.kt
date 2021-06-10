package com.github.donghune.companyz.util.extension

import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull

fun List<ItemStack>.toCloneTypeArray(): Array<@NotNull ItemStack> {
    return map { it.clone() }.toTypedArray()
}