package com.github.donghune.companyz.combination.extension

import com.github.donghune.companyz.combination.model.PlayerRecipeRepository
import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.companyz.util.extension.isContentFull
import com.github.donghune.namulibrary.extension.hasItems
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.takeItems
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent
import java.util.*

private val playerRecipeRepository by KoinJavaComponent.inject<PlayerRecipeRepository>(PlayerRecipeRepository::class.java)
private val recipeRepository by KoinJavaComponent.inject<RecipeRepository>(RecipeRepository::class.java)

fun Recipe.save() {
    recipeRepository.save(id)
}

fun Recipe.hasPlayers(): List<UUID> {
    return playerRecipeRepository.getList().filter { it.recipes.contains(id) }.map { it.uuid }
}

fun Recipe.manufacturing(player: Player) {
    if (!player.recipe.recipes.contains(id)) {
        player.sendErrorMessage("보유하고 있지 않는 레시피 입니다.")
        return
    }

    if (!player.inventory.hasItems(materials.toTypedArray())) {
        player.sendErrorMessage("아이템을 만들기 위한 재료가 부족합니다.")
        return
    }

    if (player.inventory.isContentFull()) {
        player.sendErrorMessage("인벤토리를 공간이 부족합니다. ( 최소 1칸 필요 )")
        return
    }

    player.inventory.takeItems(materials.toTypedArray())
    player.inventory.addItem(resultItem)
    player.sendInfoMessage("성공적으로 아이템을 제작 하였습니다.")
}