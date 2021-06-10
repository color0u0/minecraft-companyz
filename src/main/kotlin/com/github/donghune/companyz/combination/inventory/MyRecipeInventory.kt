package com.github.donghune.companyz.combination.inventory

import com.github.donghune.companyz.combination.extension.manufacturing
import com.github.donghune.companyz.combination.extension.recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.companyz.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.koin.java.KoinJavaComponent.inject

class MyRecipeInventory(val player: Player) : GUI(plugin, 54, "내 레시피 창고") {

    private val recipeRepository by inject<RecipeRepository>(RecipeRepository::class.java)

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        player.recipe.recipes
            .mapNotNull { recipeRepository.get(it) }
            .forEachIndexed { index, recipe ->
                setItem(index, recipe.toItemStack()) {
                    it.isCancelled = true
                    recipe.manufacturing(player)
                }
            }
    }
}