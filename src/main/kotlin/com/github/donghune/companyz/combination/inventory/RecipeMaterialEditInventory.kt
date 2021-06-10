package com.github.donghune.companyz.combination.inventory

import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.companyz.plugin
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.koin.java.KoinJavaComponent

class RecipeMaterialEditInventory(
    val recipe: Recipe
) : GUI(plugin, 9, "조합 재료를 등록해주세요") {

    private val recipeRepository by KoinJavaComponent.inject<RecipeRepository>(RecipeRepository::class.java)

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        event.inventory.storageContents
            .filter { itemStack: ItemStack? -> itemStack != null && itemStack.type != Material.AIR }
            .also { recipe.materials = it }
        recipeRepository.save(recipe.id)
        (event.player as Player).sendInfoMessage("레시피 정보를 수정하였습니다.")
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {

    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {

    }

    override suspend fun setContent() {
        recipe.materials.forEach {
            inventory.addItem(it)
        }
    }
}