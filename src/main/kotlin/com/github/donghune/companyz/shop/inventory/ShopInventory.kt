package com.github.donghune.companyz.shop.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.shop.extension.buy
import com.github.donghune.companyz.shop.extension.sell
import com.github.donghune.companyz.shop.model.Shop
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.struct.PagingList
import com.github.shynixn.mccoroutine.launch
import kotlinx.coroutines.coroutineScope
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class ShopInventory(val shop: Shop) : GUI(plugin, 54, "상점") {

    private val stuffPageList = PagingList(45, shop.stuffList)
    private var page: Int = 0

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {

    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {

    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        event.isCancelled = true
        val shopStuff = shop.stuffList.find { it.itemStack.isSimilar(event.currentItem) } ?: return
        shopStuff.sell(player, event.currentItem ?: return)
    }

    override suspend fun setContent() {
        setItem(48, PREVIOUS_BUTTON) {
            it.isCancelled = true

            val player = it.whoClicked as Player

            if (page <= 0) {
                player.sendErrorMessage("처음 페이지 입니다.")
                return@setItem
            }

            page--
            plugin.launch { refreshContent() }
        }
        setItem(50, NEXT_BUTTON) {
            it.isCancelled = true

            val player = it.whoClicked as Player

            println("if ($page == ${stuffPageList.lastPageIndex}) {")
            if (page == stuffPageList.lastPageIndex) {
                player.sendErrorMessage("마지막 페이지 입니다.")
                return@setItem
            }

            page++
            plugin.launch { refreshContent() }
        }
        stuffPageList.getPage(page).forEachIndexed { index, shopStuff ->
            setItem(index, shopStuff.toStuffItemStack()) {
                it.isCancelled = true

                val player = it.whoClicked as Player

                when (it.click) {
                    ClickType.LEFT -> shopStuff.buy(player, 1)
                    else -> return@setItem
                }
            }
        }
    }

    companion object {
        val NEXT_BUTTON = ItemBuilder().setMaterial(Material.OAK_SIGN).setDisplay("&6다음 페이지").build()
        val PREVIOUS_BUTTON = ItemBuilder().setMaterial(Material.OAK_SIGN).setDisplay("&6다음 페이지").build()
    }
}