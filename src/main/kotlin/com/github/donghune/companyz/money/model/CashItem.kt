package com.github.donghune.companyz.money.model

import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CashItem(val value: Int) {
    fun toItemStack(): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.PAPER)
            .setDisplay("&f수표")
            .setLore(
                listOf(
                    "&f수표 금액 : &6${value.toMoneyFormat()}"
                )
            )
            .build()
            .addNBTTagCompound(this)
    }
}