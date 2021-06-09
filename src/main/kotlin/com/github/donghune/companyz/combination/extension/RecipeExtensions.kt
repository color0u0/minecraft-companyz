package com.github.donghune.companyz.combination.extension

import com.github.donghune.companyz.combination.model.PlayerRecipeRepository
import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.util.extension.isContentFull
import com.github.donghune.namulibrary.extension.hasItems
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.takeItems
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

private val playerRecipeRepository by inject<PlayerRecipeRepository>(PlayerRecipeRepository::class.java)

val Player.recipes: List<String>
    get() = playerRecipeRepository.getSafety(uniqueId.toString()).recipes

fun Player.useRecipeBook() {

}

fun Player.manufacturingUsingRecipe(recipe: Recipe) {
    if (!recipes.contains(recipe.id)) {
        sendErrorMessage("보유하고 있지 않는 레시피 입니다.")
        return
    }

    if (!inventory.hasItems(recipe.materials.toTypedArray())) {
        sendErrorMessage("아이템을 만들기 위한 재료가 부족합니다.")
        return
    }

    if (inventory.isContentFull()) {
        sendErrorMessage("인벤토리를 공간이 부족합니다. ( 최소 1칸 필요 )")
        return
    }

    inventory.takeItems(recipe.materials.toTypedArray())
    inventory.addItem(recipe.resultItem)
    sendInfoMessage("성공적으로 아이템을 제작 하였습니다.")
}