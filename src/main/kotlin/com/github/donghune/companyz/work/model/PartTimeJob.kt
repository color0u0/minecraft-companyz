package com.github.donghune.companyz.work.model

import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.md_5.bungee.api.ChatColor
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
        return ItemBuilder()
            .setMaterial(Material.PAPER)
            .setDisplay("&f${work.display}")
            .setLore(
                listOf(
                    "&f${work.description}",
                    "&f",
                    "&f보수금 : &6${work.reward.money.toMoneyFormat()}",
                    "&f명성치 : &6${work.reward.money.toMoneyFormat()}",
                )
            )
            .build()
            .apply {
                if (state != WorkState.PENDING) {
                    itemMeta.displayName(Component.text("§m").append(displayName()))
                    (lore() ?: mutableListOf()).map {
                        Component.text("§m").append(it)
                    }.also { lore(it) }
                }

                (lore() ?: mutableListOf()).apply {
                    add(Component.text(""))
                    add(Component.text("§f${state.message}"))
                }.also { lore(it) }
            }
    }
}
