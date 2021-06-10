package com.github.donghune.companyz.combination.model

import com.github.donghune.companyz.combination.extension.hasPlayers
import com.github.donghune.namulibrary.model.EntityRepository
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File

class RecipeRepository(
    override val dataType: Class<Recipe>,
    override val file: File
) : EntityRepository<Recipe>() {
    override fun getDefaultData(key: String): Recipe {
        return Recipe(key, "", listOf(), listOf(), ItemStack(Material.AIR), RecipeShopInfo(true, 0))
    }

    fun getAvailableForSale(): List<Recipe> {
        return getList()
            .filter { it.recipeShopInfo.isUnlimitedSales || it.hasPlayers().isEmpty() }
            .sortedBy { it.recipeShopInfo.isUnlimitedSales }
    }
}