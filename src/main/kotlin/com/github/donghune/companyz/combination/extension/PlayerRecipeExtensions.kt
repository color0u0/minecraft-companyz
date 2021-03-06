package com.github.donghune.companyz.combination.extension

import com.github.donghune.companyz.combination.model.PlayerRecipe
import com.github.donghune.companyz.combination.model.PlayerRecipeRepository
import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

private val recipeRepository by inject<RecipeRepository>(RecipeRepository::class.java)
private val playerRecipeRepository by inject<PlayerRecipeRepository>(PlayerRecipeRepository::class.java)

val Player.recipe: PlayerRecipe
    get() = playerRecipeRepository.getSafety(uniqueId.toString())

fun Player.buyRecipeBook(recipe: Recipe) {

    if (recipeRepository.getAvailableForSale().find { it.id == recipe.id } == null) {
        sendErrorMessage("이미 누군가가 구매한 레시피 입니다.")
        return
    }

    if (this.recipe.recipes.contains(recipe.id)) {
        sendErrorMessage("이미 보유하고 있는 레시피 입니다.")
        return
    }

    this.recipe.recipes.add(recipe.id)
    playerRecipeRepository.save(uniqueId.toString())
    sendInfoMessage("레시피를 습득하였습니다.")
}