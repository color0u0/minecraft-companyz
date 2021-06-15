package com.github.donghune.companyz.work.model

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class PartTimeJob(
    val index: Int,
    val work: Work,
    var state: WorkState,
    var acceptedPlayer: Player?
) {
    fun toItemStack(): ItemStack {
        return ItemStackFactory()
            .setType(Material.PAPER)
            .setDisplayName("&f${work.display}")
            .setLore(
                listOf(
                    "&f${work.description}",
                    "&f",
                    "&f보수금 : &6${work.reward.money.toMoneyFormat()}",
                    "&f명성치 : &6${work.reward.money.toMoneyFormat()}",
                    "",
                    "§f${state.message}",
                )
            )
            .build()
    }
}
