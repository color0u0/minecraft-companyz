package com.github.donghune.companyz.combination.inventory

import com.github.donghune.companyz.combination.extension.buyRecipeBook
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.companyz.plugin
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.struct.PagingList
import com.github.shynixn.mccoroutine.launch
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.koin.java.KoinJavaComponent.inject

class RecipeShopInventory : GUI(plugin, 54, "특허청") {

    private val recipeRepository by inject<RecipeRepository>(RecipeRepository::class.java)
    private val recipePageList
        get() = PagingList(45, recipeRepository.getAvailableForSale())
    private var page: Int = 0

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {

    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {

    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {

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

            if (page == recipePageList.lastPageIndex) {
                player.sendErrorMessage("마지막 페이지 입니다.")
                return@setItem
            }

            page++
            plugin.launch { refreshContent() }
        }
        recipePageList.getPage(page).forEachIndexed { index, recipe ->
            setItem(index, recipe.toSellRecipeItemStack()) {
                it.isCancelled = true

                val player = it.whoClicked as Player

                when (it.click) {
                    ClickType.LEFT -> {
                        player.buyRecipeBook(recipe)
                        plugin.launch { refreshContent() }
                    }
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